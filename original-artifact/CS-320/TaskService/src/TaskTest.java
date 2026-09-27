import static org.junit.Assert.*;
import org.junit.Test;

public class TaskTest {

    @Test
    public void testCreateValidTask() {
        Task task = new Task("12345", "Test Name", "Test Description");
        assertEquals("12345", task.getTaskId());
        assertEquals("Test Name", task.getName());
        assertEquals("Test Description", task.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTaskIdCannotBeNull() {
        new Task(null, "Name", "Desc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTaskIdTooLong() {
        new Task("12345678901", "Name", "Desc"); // 11 chars
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameCannotBeNull() {
        new Task("12345", null, "Desc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameTooLong() {
        new Task("12345", "123456789012345678901", "Desc"); // 21 chars
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDescriptionCannotBeNull() {
        new Task("12345", "Name", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDescriptionTooLong() {
        String longDesc = "123456789012345678901234567890123456789012345678901"; // 51 chars
        new Task("12345", "Name", longDesc);
    }

    @Test
    public void testTaskIdNotUpdatable() {
        Task task = new Task("abc", "Name", "Desc");

        // No setter exists (by design), so the ID stays what it was created as.
        assertEquals("abc", task.getTaskId());

        // These *are* allowed:
        task.setName("New Name");
        task.setDescription("New Desc");

        assertEquals("abc", task.getTaskId());
    }

    @Test
    public void testSetNameValidation() {
        Task task = new Task("1", "Name", "Desc");

        try {
            task.setName(null);
            fail("Expected IllegalArgumentException for null name.");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            task.setName("123456789012345678901"); // 21 chars
            fail("Expected IllegalArgumentException for name > 20 chars.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testSetDescriptionValidation() {
        Task task = new Task("1", "Name", "Desc");

        try {
            task.setDescription(null);
            fail("Expected IllegalArgumentException for null description.");
        } catch (IllegalArgumentException e) {
            // expected
        }

        String longDesc = "123456789012345678901234567890123456789012345678901"; // 51 chars
        try {
            task.setDescription(longDesc);
            fail("Expected IllegalArgumentException for description > 50 chars.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

