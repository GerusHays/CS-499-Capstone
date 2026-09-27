package com.gerushays.cs320.service;

import com.gerushays.cs320.model.Task;
import com.gerushays.cs320.repository.InMemoryRepository;
import com.gerushays.cs320.repository.Repository;
import java.util.List;

public class TaskService {
    private final Repository<Task> tasks;
    public TaskService() { this(new InMemoryRepository<>()); }
    public TaskService(Repository<Task> tasks) { this.tasks = tasks; }

    public void addTask(String id, String name, String description) { tasks.add(new Task(id, name, description)); }
    public void deleteTask(String id) { tasks.deleteById(id); }
    public Task getTask(String id) { return tasks.requireById(id); }
    public List<Task> getAllTasks() { return tasks.findAll(); }
    public void updateTaskName(String id, String value) { getTask(id).setName(value); }
    public void updateTaskDescription(String id, String value) { getTask(id).setDescription(value); }
}
