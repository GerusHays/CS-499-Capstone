/**
 * Task.java
 *
 * Simple task object for Project One.
 * Requirements:
 * - taskId: required, unique, not null, <= 10 chars, NOT updatable
 * - name: required, not null, <= 20 chars
 * - description: required, not null, <= 50 chars
 */
public class Task {

    // ID is final so it literally cannot be updated after construction.
    private final String taskId;

    private String name;
    private String description;

    public Task(String taskId, String name, String description) {
        // I?m being strict here because the rubric is strict.
        validateTaskId(taskId);
        validateName(name);
        validateDescription(description);

        this.taskId = taskId;
        this.name = name;
        this.description = description;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Updateable field: name
     */
    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    /**
     * Update field: description
     */
    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    // --- Validation helpers (kept private so consumers can?t bypass the rules) ---

    private void validateTaskId(String taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null.");
        }
        if (taskId.length() > 10) {
            throw new IllegalArgumentException("Task ID cannot be longer than 10 characters.");
        }
        if (taskId.isBlank()) {
            throw new IllegalArgumentException("Task ID cannot be blank.");
        }
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        if (name.length() > 20) {
            throw new IllegalArgumentException("Name cannot be longer than 20 characters.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
    }

    private void validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }
        if (description.length() > 50) {
            throw new IllegalArgumentException("Description cannot be longer than 50 characters.");
        }
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank.");
        }
    }
}
