package com.gerushays.cs320.database;

import com.gerushays.cs320.model.*;
import com.gerushays.cs320.exception.DuplicateIdException;
import java.nio.file.*;
import java.sql.*;
import java.util.Date;

/** File-backed SQLite integration checks; run using TESTING.md instructions. */
public final class SqliteIntegrationTest {
    private static int passed = 0;
    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
        passed++;
        System.out.println("PASS " + label);
    }
    private static void rejectsSql(Connection cn, String sql, String label) throws SQLException {
        try (Statement st = cn.createStatement()) {
            try { st.executeUpdate(sql); throw new AssertionError("Expected SQL constraint: " + label); }
            catch (SQLException expected) { check(true, label); }
        }
    }
    public static void main(String[] args) throws Exception {
        Path file = Files.createTempFile("cs499-integration-", ".db");
        try {
            DatabaseManager first = new DatabaseManager(file.toString());
            var contacts = new SqliteContactRepository(first);
            var tasks = new SqliteTaskRepository(first);
            var appointments = new SqliteAppointmentRepository(first);
            long future = System.currentTimeMillis() + 86_400_000L * 30;
            contacts.add(new Contact("C1", "Ada", "Lovelace", "1234567890", "123 Main St"));
            tasks.add(new Task("T1", "Inspect", "Inspect system"));
            appointments.add(new Appointment("A1", new Date(future), "Review"));
            check(contacts.size()==1 && tasks.size()==1 && appointments.size()==1, "initial CRUD inserts");
            // New manager/repositories reconnect to the same file-backed database.
            DatabaseManager reopened = new DatabaseManager(file.toString());
            contacts = new SqliteContactRepository(reopened);
            tasks = new SqliteTaskRepository(reopened);
            appointments = new SqliteAppointmentRepository(reopened);
            check(contacts.requireById("C1").getLastName().equals("Lovelace"), "contact persisted after reconnect");
            check(tasks.requireById("T1").getName().equals("Inspect"), "task persisted after reconnect");
            check(appointments.requireById("A1").getDescription().equals("Review"), "appointment persisted after reconnect");
            contacts.update(new Contact("C1", "Ada", "Byron", "1234567890", "123 Main St"));
            tasks.update(new Task("T1", "Inspect", "Updated"));
            appointments.update(new Appointment("A1", new Date(future), "Updated"));
            DatabaseManager again = new DatabaseManager(file.toString());
            check(new SqliteContactRepository(again).requireById("C1").getLastName().equals("Byron"), "contact update persisted");
            check(new SqliteTaskRepository(again).requireById("T1").getDescription().equals("Updated"), "task update persisted");
            check(new SqliteAppointmentRepository(again).requireById("A1").getDescription().equals("Updated"), "appointment update persisted");
            try { contacts.add(new Contact("C1", "Ada", "Byron", "1234567890", "123 Main St")); throw new AssertionError("Expected duplicate ID"); }
            catch (DuplicateIdException expected) { check(true, "duplicate ID rejected"); }
            try (Connection cn = again.getConnection()) {
                rejectsSql(cn, "INSERT INTO contacts VALUES('BAD','Ada','Lovelace','123','Address')", "phone length CHECK constraint");
                rejectsSql(cn, "INSERT INTO tasks VALUES('BAD',NULL,'description')", "NOT NULL constraint");
                for (String idx : new String[]{"idx_contacts_name", "idx_tasks_name", "idx_appointments_date"}) {
                    try (PreparedStatement ps = cn.prepareStatement("SELECT COUNT(*) FROM sqlite_master WHERE type='index' AND name=?")) {
                        ps.setString(1, idx);
                        try (ResultSet rs = ps.executeQuery()) { check(rs.next() && rs.getInt(1)==1, idx + " exists"); }
                    }
                }
                try (Statement st = cn.createStatement(); ResultSet rs = st.executeQuery("EXPLAIN QUERY PLAN SELECT * FROM contacts WHERE last_name='Byron'")) {
                    boolean used = false;
                    while (rs.next()) if (rs.getString("detail").contains("idx_contacts_name")) used = true;
                    check(used, "contact-name query uses index");
                }
            }
            contacts.deleteById("C1"); tasks.deleteById("T1"); appointments.deleteById("A1");
            DatabaseManager finalOpen = new DatabaseManager(file.toString());
            check(new SqliteContactRepository(finalOpen).size()==0, "contact deletion persisted");
            check(new SqliteTaskRepository(finalOpen).size()==0, "task deletion persisted");
            check(new SqliteAppointmentRepository(finalOpen).size()==0, "appointment deletion persisted");
            System.out.println("SQLite integration checks passed: " + passed);
        } finally { Files.deleteIfExists(file); }
    }
}
