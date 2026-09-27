import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for Contact validation rules.
 */
public class ContactTest {

    @Test
    public void testCreateValidContact() {
        Contact c = new Contact("12345", "Gus", "Hays", "1234567890", "123 Main Street");
        assertEquals("12345", c.getContactId());
        assertEquals("Gus", c.getFirstName());
        assertEquals("Hays", c.getLastName());
        assertEquals("1234567890", c.getPhone());
        assertEquals("123 Main Street", c.getAddress());
    }

    @Test
    public void testContactIdCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(null, "Gus", "Hays", "1234567890", "123 Main Street")
        );
    }

    @Test
    public void testContactIdCannotBeTooLong() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345678901", "Gus", "Hays", "1234567890", "123 Main Street")
        );
    }

    @Test
    public void testFirstNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345", null, "Hays", "1234567890", "123 Main Street")
        );
    }

    @Test
    public void testFirstNameCannotBeTooLong() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345", "ABCDEFGHIJK", "Hays", "1234567890", "123 Main Street")
        );
    }

    @Test
    public void testLastNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345", "Gus", null, "1234567890", "123 Main Street")
        );
    }

    @Test
    public void testLastNameCannotBeTooLong() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345", "Gus", "ABCDEFGHIJK", "1234567890", "123 Main Street")
        );
    }

    @Test
    public void testPhoneCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345", "Gus", "Hays", null, "123 Main Street")
        );
    }

    @Test
    public void testPhoneMustBeExactly10Digits() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345", "Gus", "Hays", "12345", "123 Main Street")
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345", "Gus", "Hays", "12345678901", "123 Main Street")
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345", "Gus", "Hays", "12345abcde", "123 Main Street")
        );
    }

    @Test
    public void testAddressCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345", "Gus", "Hays", "1234567890", null)
        );
    }

    @Test
    public void testAddressCannotBeTooLong() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345", "Gus", "Hays", "1234567890",
                    "1234567890123456789012345678901") // 31 chars
        );
    }

    @Test
    public void testUpdateFieldsValid() {
        Contact c = new Contact("12345", "Gus", "Hays", "1234567890", "123 Main Street");

        c.setFirstName("Gerus");
        c.setLastName("H");
        c.setPhone("0987654321");
        c.setAddress("456 Another Street");

        assertEquals("Gerus", c.getFirstName());
        assertEquals("H", c.getLastName());
        assertEquals("0987654321", c.getPhone());
        assertEquals("456 Another Street", c.getAddress());
    }

    @Test
    public void testUpdateFieldsInvalid() {
        Contact c = new Contact("12345", "Gus", "Hays", "1234567890", "123 Main Street");

        assertThrows(IllegalArgumentException.class, () -> c.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> c.setLastName(null));
        assertThrows(IllegalArgumentException.class, () -> c.setPhone("111"));
        assertThrows(IllegalArgumentException.class, () -> c.setAddress(null));
    }
}
