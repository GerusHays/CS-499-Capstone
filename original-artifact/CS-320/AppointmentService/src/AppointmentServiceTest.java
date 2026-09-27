import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.util.Date;

import org.junit.Test;

public class AppointmentServiceTest {

    @Test
    void testAddAppointmentSuccessfully() {
        AppointmentService service = new AppointmentService();
        Date futureDate = new Date(System.currentTimeMillis() + 60_000);
        Appointment appt = new Appointment("A111", futureDate, "Checkup");

        service.addAppointment(appt);

        assertNotNull(service.getAppointment("A111"));
        assertEquals("Checkup", service.getAppointment("A111").getDescription());
    }

    @Test
    void testAddAppointmentDuplicateIdThrows() {
        AppointmentService service = new AppointmentService();
        Date futureDate = new Date(System.currentTimeMillis() + 60_000);

        Appointment appt1 = new Appointment("A222", futureDate, "First");
        Appointment appt2 = new Appointment("A222", futureDate, "Second");

        service.addAppointment(appt1);
        assertThrows(IllegalArgumentException.class, () -> service.addAppointment(appt2));
    }

    @Test
    void testDeleteAppointmentSuccessfully() {
        AppointmentService service = new AppointmentService();
        Date futureDate = new Date(System.currentTimeMillis() + 60_000);
        Appointment appt = new Appointment("A333", futureDate, "Dentist");

        service.addAppointment(appt);
        service.deleteAppointment("A333");

        assertNull(service.getAppointment("A333"));
    }

    @Test
    void testDeleteAppointmentNotFoundThrows() {
        AppointmentService service = new AppointmentService();
        assertThrows(IllegalArgumentException.class, () -> service.deleteAppointment("NOPE"));
    }
}