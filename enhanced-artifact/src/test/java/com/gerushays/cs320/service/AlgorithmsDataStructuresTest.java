package com.gerushays.cs320.service;

import com.gerushays.cs320.model.Appointment;
import com.gerushays.cs320.model.Contact;
import com.gerushays.cs320.model.Task;

import java.util.Date;
import java.util.List;

/**
 * Small test runner for the algorithms and data structures enhancement.
 * I kept this dependency-free so the tests can run with the JDK by itself.
 */
public class AlgorithmsDataStructuresTest {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testContactSearchAndSort();
        testTaskSearchAndSort();
        testAppointmentSortingAndUpcoming();
        testEmptyCollections();
        testInvalidSearchTerms();

        System.out.println("\nTests passed: " + passed);
        System.out.println("Tests failed: " + failed);

        if (failed > 0) {
            throw new AssertionError("One or more tests failed.");
        }
    }

    private static void testContactSearchAndSort() {
        ContactService service = new ContactService();
        service.addContact(new Contact("C1", "Gus", "Hays", "5551112222", "1 Main St"));
        service.addContact(new Contact("C2", "Alex", "Brown", "5552223333", "2 Main St"));
        service.addContact(new Contact("C3", "Sam", "HAYES", "5553334444", "3 Main St"));

        // Search should ignore case and check both first and last names.
        List<Contact> matches = service.searchByName("ha");
        check(matches.size() == 2, "Contact search finds partial matches");
        check(service.searchByName("GUS").size() == 1, "Contact search is case-insensitive");
        check(service.searchByName("nobody").isEmpty(), "Contact search handles no matches");

        List<Contact> sorted = service.getContactsSortedByName();
        check(sorted.get(0).getLastName().equals("Brown"), "Contacts sort by last name");
        check(sorted.get(1).getLastName().equals("HAYES"), "Contact sort ignores case");
        check(sorted.get(2).getLastName().equals("Hays"), "Contact sort returns predictable order");
    }

    private static void testTaskSearchAndSort() {
        TaskService service = new TaskService();
        service.addTask("T1", "Flight Test", "Review telemetry logs");
        service.addTask("T2", "Code Review", "Review automation changes");
        service.addTask("T3", "Build Report", "Summarize flight results");

        // A task can be found from either its name or its description.
        check(service.searchTasks("code").size() == 1, "Task search checks task names");
        check(service.searchTasks("FLIGHT").size() == 2, "Task search checks descriptions and ignores case");
        check(service.searchTasks("missing").isEmpty(), "Task search handles no matches");

        List<Task> sorted = service.getTasksSortedByName();
        check(sorted.get(0).getName().equals("Build Report"), "Tasks sort alphabetically");
        check(sorted.get(1).getName().equals("Code Review"), "Task sort middle value is correct");
        check(sorted.get(2).getName().equals("Flight Test"), "Task sort final value is correct");
    }

    private static void testAppointmentSortingAndUpcoming() {
        AppointmentService service = new AppointmentService();
        long now = System.currentTimeMillis();

        Appointment later = new Appointment("A2", new Date(now + 3_600_000L * 3), "Later appointment");
        Appointment sooner = new Appointment("A1", new Date(now + 3_600_000L), "Sooner appointment");
        Appointment middle = new Appointment("A3", new Date(now + 3_600_000L * 2), "Middle appointment");

        // Add these out of order on purpose so the sort actually has something to do.
        service.addAppointment(later);
        service.addAppointment(sooner);
        service.addAppointment(middle);

        List<Appointment> sorted = service.getAppointmentsChronologically();
        check(sorted.get(0).getAppointmentId().equals("A1"), "Appointments sort earliest first");
        check(sorted.get(1).getAppointmentId().equals("A3"), "Appointment chronological middle is correct");
        check(sorted.get(2).getAppointmentId().equals("A2"), "Appointments sort latest last");

        List<Appointment> upcoming = service.getUpcomingAppointments();
        check(upcoming.size() == 3, "Upcoming appointment filter keeps future appointments");
        check(upcoming.get(0).getAppointmentId().equals("A1"), "Upcoming appointments are chronological");
    }

    private static void testEmptyCollections() {
        check(new ContactService().getContactsSortedByName().isEmpty(), "Empty contact collection sorts safely");
        check(new TaskService().getTasksSortedByName().isEmpty(), "Empty task collection sorts safely");
        check(new AppointmentService().getAppointmentsChronologically().isEmpty(), "Empty appointment collection sorts safely");
        check(new AppointmentService().getUpcomingAppointments().isEmpty(), "Empty upcoming appointment list is handled");
    }

    private static void testInvalidSearchTerms() {
        expectIllegalArgument(() -> new ContactService().searchByName("   "), "Blank contact search is rejected");
        expectIllegalArgument(() -> new TaskService().searchTasks(null), "Null task search is rejected");
    }

    private static void expectIllegalArgument(Runnable action, String name) {
        try {
            action.run();
            fail(name);
        } catch (IllegalArgumentException expected) {
            pass(name);
        }
    }

    private static void check(boolean condition, String name) {
        if (condition) pass(name); else fail(name);
    }

    private static void pass(String name) {
        passed++;
        System.out.println("PASS: " + name);
    }

    private static void fail(String name) {
        failed++;
        System.out.println("FAIL: " + name);
    }
}
