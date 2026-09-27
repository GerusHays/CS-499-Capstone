import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class AppointmentTest {

    @Test
    void testAppointmentCreatedSuccessfully() {
        Date futureDate = new Date(System.currentTimeMillis() + 60_000); // 1 min in the future
        Appointment appt = new Appointment("A12345", futureDate, "Doctor visit");

        assertEquals("A12345", appt.getAppointmentId());
        assertEquals(futureDate, appt.getAppointmentDate());
        assertEquals("Doctor visit", appt.getDescription());
    }

    @Test
    void testAppointmentIdCannotBeNull() {
        Date futureDate = new Date(System.currentTimeMillis() + 60_000);
        assertThrows(IllegalArgumentException.class, () ->
            new Appointment(null, futureDate, "Valid description")
        );
    }

    @Test
    void testAppointmentIdCannotBeTooLong() {
        Date futureDate = new Date(System.currentTimeMillis() + 60_000);
        assertThrows(IllegalArgumentException.class, () ->
            new Appointment("12345678901", futureDate, "Valid description") // 11 chars
        );
    }

    @Test
    void testAppointmentDateCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Appointment("A12345", null, "Valid description")
        );
    }

    @Test
    void testAppointmentDateCannotBeInPast() {
        Date pastDate = new Date(System.currentTimeMillis() - 60_000); // 1 min ago
        assertThrows(IllegalArgumentException.class, () ->
            new Appointment("A12345", pastDate, "Valid description")
        );
    }

    @Test
    void testDescriptionCannotBeNull() {
        Date futureDate = new Date(System.currentTimeMillis() + 60_000);
        assertThrows(IllegalArgumentException.class, () ->
            new Appointment("A12345", futureDate, null)
        );
    }

    @Test
    void testDescriptionCannotBeTooLong() {
        Date futureDate = new Date(System.currentTimeMillis() + 60_000);
        String longDesc = "123456789012345678901234567890123456789012345678901"; // 51 chars
        assertThrows(IllegalArgumentException.class, () ->
            new Appointment("A12345", futureDate, longDesc)
        );
    }
}