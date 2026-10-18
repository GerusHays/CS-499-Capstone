package com.gerushays.cs320.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Owns the SQLite connection details and creates the database schema.
 * The database file can be changed without changing the repositories.
 */
public final class DatabaseManager {
    private final String url;

    public DatabaseManager(String databaseFile) {
        if (databaseFile == null || databaseFile.isBlank()) {
            throw new IllegalArgumentException("Database file cannot be null or blank.");
        }
        this.url = "jdbc:sqlite:" + databaseFile;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    public void initializeSchema() {
        String[] statements = {
            "CREATE TABLE IF NOT EXISTS contacts (contact_id TEXT PRIMARY KEY NOT NULL, first_name TEXT NOT NULL, last_name TEXT NOT NULL, phone TEXT NOT NULL, address TEXT NOT NULL, CHECK(length(contact_id) <= 10), CHECK(length(first_name) <= 10), CHECK(length(last_name) <= 10), CHECK(length(phone) = 10), CHECK(length(address) <= 30))",
            "CREATE TABLE IF NOT EXISTS tasks (task_id TEXT PRIMARY KEY NOT NULL, name TEXT NOT NULL, description TEXT NOT NULL, CHECK(length(task_id) <= 10), CHECK(length(name) <= 20), CHECK(length(description) <= 50))",
            "CREATE TABLE IF NOT EXISTS appointments (appointment_id TEXT PRIMARY KEY NOT NULL, appointment_date INTEGER NOT NULL, description TEXT NOT NULL, CHECK(length(appointment_id) <= 10), CHECK(length(description) <= 50))",
            "CREATE INDEX IF NOT EXISTS idx_contacts_name ON contacts(last_name, first_name)",
            "CREATE INDEX IF NOT EXISTS idx_tasks_name ON tasks(name)",
            "CREATE INDEX IF NOT EXISTS idx_appointments_date ON appointments(appointment_date)"
        };
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            for (String sql : statements) statement.execute(sql);
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to initialize database schema.", e);
        }
    }
}
