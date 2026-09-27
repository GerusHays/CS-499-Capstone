package com.gerushays.cs320.service;

import com.gerushays.cs320.model.Appointment;
import com.gerushays.cs320.repository.InMemoryRepository;
import com.gerushays.cs320.repository.Repository;
import java.util.List;

public class AppointmentService {
    private final Repository<Appointment> appointments;
    public AppointmentService() { this(new InMemoryRepository<>()); }
    public AppointmentService(Repository<Appointment> appointments) { this.appointments = appointments; }

    public void addAppointment(Appointment appointment) { appointments.add(appointment); }
    public void deleteAppointment(String id) { appointments.deleteById(id); }
    public Appointment getAppointment(String id) { return appointments.requireById(id); }
    public List<Appointment> getAllAppointments() { return appointments.findAll(); }
}
