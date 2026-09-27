package com.gerushays.cs320.model;

import com.gerushays.cs320.validation.Validation;
import java.util.Date;

public class Appointment implements Identifiable {
    private final String appointmentId;
    private Date appointmentDate;
    private String description;

    public Appointment(String appointmentId, Date appointmentDate, String description) {
        this.appointmentId = Validation.requiredText(appointmentId, "Appointment ID", 10);
        setAppointmentDate(appointmentDate);
        setDescription(description);
    }

    @Override public String getId() { return appointmentId; }
    public String getAppointmentId() { return appointmentId; }
    public Date getAppointmentDate() { return new Date(appointmentDate.getTime()); }
    public String getDescription() { return description; }

    public void setAppointmentDate(Date value) {
        if (value == null) throw new IllegalArgumentException("Appointment date cannot be null.");
        if (value.before(new Date())) throw new IllegalArgumentException("Appointment date cannot be in the past.");
        appointmentDate = new Date(value.getTime());
    }

    public void setDescription(String value) {
        description = Validation.requiredText(value, "Description", 50);
    }
}
