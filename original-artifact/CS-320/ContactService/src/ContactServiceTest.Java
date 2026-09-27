import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for ContactService behaviors:
 * - add (unique id)
 * - delete by id
 * - update fields by id
 */
public class ContactServiceTest {

    @Test
    public void testAddContactSuccess() {
        ContactService service = new ContactService();
        Contact c = new Contact("1", "Gus", "Hays", "1234567890", "123 Main Street");

        service.addContact(c);

        assertNotNull(service.getContact("1"));
        assertEquals("Gus", service.getContact("1").getFirstName());
    }

    @Test
    public void testAddContactDuplicateIdFails() {
        ContactService service = new ContactService();
        Contact c1 = new Contact("1", "Gus", "Hays", "1234567890", "123 Main Street");
        Contact c2 = new Contact("1", "Bob", "Smith", "0987654321", "456 Another St");

        service.addContact(c1);
        assertThrows(IllegalArgumentException.class, () -> service.addContact(c2));
    }

    @Test
    public void testDeleteContactSuccess() {
        ContactService service = new ContactService();
        Contact c = new Contact("1", "Gus", "Hays", "1234567890", "123 Main Street");

        service.addContact(c);
        service.deleteContact("1");

        assertNull(service.getContact("1"));
    }

    @Test
    public void testDeleteContactNotFoundFails() {
        ContactService service = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("doesNotExist"));
    }

    @Test
    public void testUpdateContactFieldsSuccess() {
        ContactService service = new ContactService();
        Contact c = new Contact("1", "Gus", "Hays", "1234567890", "123 Main Street");
        service.addContact(c);

        service.updateFirstName("1", "Gerus");
        service.updateLastName("1", "H");
        service.updatePhone("1", "0987654321");
        service.updateAddress("1", "456 Another Street");

        Contact updated = service.getContact("1");
        assertEquals("Gerus", updated.getFirstName());
        assertEquals("H", updated.getLastName());
        assertEquals("0987654321", updated.getPhone());
        assertEquals("456 Another Street", updated.getAddress());
    }

    @Test
    public void testUpdateContactNotFoundFails() {
        ContactService service = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("nope", "Test"));
    }

    @Test
    public void testUpdateContactInvalidFieldFails() {
        ContactService service = new ContactService();
        Contact c = new Contact("1", "Gus", "Hays", "1234567890", "123 Main Street");
        service.addContact(c);

        // Too long firstName
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("1", "ABCDEFGHIJK"));

        // Invalid phone
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("1", "123"));
    }
}
