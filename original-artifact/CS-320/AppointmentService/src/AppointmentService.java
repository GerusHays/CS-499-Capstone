import java.util.HashMap;
import java.util.Map;

public class AppointmentService {

    // In-memory data structure (no DB required)
    private final Map<String, Appointment> appointments = new HashMap<>();

    // Add appointment with a unique appointment ID
    public void addAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null.");
        }

        String id = appointment.getAppointmentId();
        if (appointments.containsKey(id)) {
            throw new IllegalArgumentException("Appointment ID already exists: " + id);
        }

        appointments.put(id, appointment);
    }

    // Delete appointment by appointment ID
    public void deleteAppointment(String appointmentId) {
        if (appointmentId == null || appointmentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Appointment ID cannot be null or empty.");
        }

        if (!appointments.containsKey(appointmentId)) {
            throw new IllegalArgumentException("Appointment ID not found: " + appointmentId);
        }

        appointments.remove(appointmentId);
    }

    // Helper for tests
    public Appointment getAppointment(String appointmentId) {
        return appointments.get(appointmentId);
    }
}
