package com.gerushays.cs320.model;

import com.gerushays.cs320.validation.Validation;

public class Task implements Identifiable {
    private final String taskId;
    private String name;
    private String description;

    public Task(String taskId, String name, String description) {
        this.taskId = Validation.requiredText(taskId, "Task ID", 10);
        setName(name);
        setDescription(description);
    }

    @Override public String getId() { return taskId; }
    public String getTaskId() { return taskId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public void setName(String value) { name = Validation.requiredText(value, "Name", 20); }
    public void setDescription(String value) { description = Validation.requiredText(value, "Description", 50); }
}
