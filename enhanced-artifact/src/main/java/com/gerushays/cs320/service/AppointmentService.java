package com.gerushays.cs320.service;

import com.gerushays.cs320.model.Appointment;
import com.gerushays.cs320.repository.InMemoryRepository;
import com.gerushays.cs320.repository.Repository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AppointmentService {
    private final Repository<Appointment> appointments;

    public AppointmentService() { this(new InMemoryRepository<>()); }
    public AppointmentService(Repository<Appointment> appointments) { this.appointments = appointments; }

    public void addAppointment(Appointment appointment) { appointments.add(appointment); }
    public void deleteAppointment(String id) { appointments.deleteById(id); }
    public Appointment getAppointment(String id) { return appointments.requireById(id); }
    public List<Appointment> getAllAppointments() { return appointments.findAll(); }

    // Sort a copy of the collection so the soonest appointment is returned first.
    public List<Appointment> getAppointmentsChronologically() {
        List<Appointment> sorted = new ArrayList<>(appointments.findAll());
        sorted.sort(Comparator.comparing(Appointment::getAppointmentDate)
                .thenComparing(Appointment::getAppointmentId));
        return List.copyOf(sorted);
    }

    // Filter first, then sort the result. This keeps past records out if the data source changes later.
    public List<Appointment> getUpcomingAppointments() {
        Date now = new Date();
        List<Appointment> upcoming = new ArrayList<>();

        for (Appointment appointment : appointments.findAll()) {
            if (!appointment.getAppointmentDate().before(now)) {
                upcoming.add(appointment);
            }
        }

        upcoming.sort(Comparator.comparing(Appointment::getAppointmentDate)
                .thenComparing(Appointment::getAppointmentId));
        return List.copyOf(upcoming);
    }
}
