import java.util.HashMap;
import java.util.Map;

/**
 * TaskService.java
 *
 * In-memory task service (no database).
 * Requirements:
 * - Add tasks with a unique ID
 * - Delete tasks by task ID
 * - Update task fields by task ID (name + description are updateable)
 */
public class TaskService {

    // Using a map gives fast lookups by taskId and keeps it simple.
    private final Map<String, Task> tasks = new HashMap<>();

    /**
     * Adds a task using the given values.
     * Enforces unique taskId.
     */
    public void addTask(String taskId, String name, String description) {
        if (tasks.containsKey(taskId)) {
            throw new IllegalArgumentException("Task ID already exists. Must be unique.");
        }

        Task newTask = new Task(taskId, name, description);
        tasks.put(taskId, newTask);
    }

    /**
     * Deletes a task by ID.
     */
    public void deleteTask(String taskId) {
        if (!tasks.containsKey(taskId)) {
            throw new IllegalArgumentException("Task ID not found. Nothing to delete.");
        }
        tasks.remove(taskId);
    }

    /**
     * Updates the name for the task with the given ID.
     */
    public void updateTaskName(String taskId, String newName) {
        Task task = getExistingTask(taskId);
        task.setName(newName);
    }

    /**
     * Updates the description for the task with the given ID.
     */
    public void updateTaskDescription(String taskId, String newDescription) {
        Task task = getExistingTask(taskId);
        task.setDescription(newDescription);
    }

    /**
     * Helper used internally to enforce "must exist" behavior.
     */
    private Task getExistingTask(String taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task ID not found. Cannot update.");
        }
        return task;
    }

    /**
     * Not required by the prompt, but useful for unit tests to verify results.
     * If your instructor is super strict about ?only required methods,? tell me and I?ll remove it
     * and adjust the tests accordingly.
     */
    public Task getTask(String taskId) {
        return tasks.get(taskId);
    }
}
