package com.gerushays.cs320.service;

import com.gerushays.cs320.model.Task;
import com.gerushays.cs320.repository.InMemoryRepository;
import com.gerushays.cs320.repository.Repository;
import java.util.ArrayList;
import java.util.Comparator;
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

    // Search both fields so the user does not have to remember exactly where a word was entered.
    public List<Task> searchTasks(String searchTerm) {
        String term = normalizeSearchTerm(searchTerm);
        List<Task> matches = new ArrayList<>();

        for (Task task : tasks.findAll()) {
            if (task.getName().toLowerCase().contains(term)
                    || task.getDescription().toLowerCase().contains(term)) {
                matches.add(task);
            }
        }
        return List.copyOf(matches);
    }

    // This gives the task collection a predictable order instead of relying on HashMap iteration order.
    public List<Task> getTasksSortedByName() {
        List<Task> sorted = new ArrayList<>(tasks.findAll());
        sorted.sort(Comparator.comparing(Task::getName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Task::getTaskId));
        return List.copyOf(sorted);
    }

    private static String normalizeSearchTerm(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            throw new IllegalArgumentException("Search term cannot be null or blank.");
        }
        return searchTerm.trim().toLowerCase();
    }
}
