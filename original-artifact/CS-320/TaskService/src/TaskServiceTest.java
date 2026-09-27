import static org.junit.Assert.*;
import org.junit.Test;

public class TaskServiceTest {

    @Test
    public void testAddTask() {
        TaskService service = new TaskService();

        service.addTask("1", "Task Name", "Task Description");

        Task task = service.getTask("1");
        assertNotNull(task);
        assertEquals("1", task.getTaskId());
        assertEquals("Task Name", task.getName());
        assertEquals("Task Description", task.getDescription());
    }

    @Test
    public void testAddTaskDuplicateId() {
        TaskService service = new TaskService();

        service.addTask("1", "Task Name", "Task Description");

        try {
            service.addTask("1", "Another Name", "Another Description");
            fail("Expected IllegalArgumentException for duplicate task ID.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testDeleteTask() {
        TaskService service = new TaskService();

        service.addTask("1", "Task Name", "Task Description");
        assertNotNull(service.getTask("1"));

        service.deleteTask("1");
        assertNull(service.getTask("1"));
    }

    @Test
    public void testDeleteMissingTask() {
        TaskService service = new TaskService();

        try {
            service.deleteTask("doesNotExist");
            fail("Expected IllegalArgumentException for deleting missing task.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testUpdateTaskName() {
        TaskService service = new TaskService();

        service.addTask("1", "Old Name", "Task Description");
        service.updateTaskName("1", "New Name");

        Task task = service.getTask("1");
        assertEquals("New Name", task.getName());
        assertEquals("Task Description", task.getDescription()); // confirm description didn't change
    }

    @Test
    public void testUpdateTaskDescription() {
        TaskService service = new TaskService();

        service.addTask("1", "Task Name", "Old Description");
        service.updateTaskDescription("1", "New Description");

        Task task = service.getTask("1");
        assertEquals("Task Name", task.getName()); // confirm name didn't change
        assertEquals("New Description", task.getDescription());
    }

    @Test
    public void testUpdateMissingTask() {
        TaskService service = new TaskService();

        try {
            service.updateTaskName("nope", "Name");
            fail("Expected IllegalArgumentException when updating name for missing task.");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            service.updateTaskDescription("nope", "Desc");
            fail("Expected IllegalArgumentException when updating description for missing task.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
