import java.util.HashMap;
import java.util.Map;

/**
 * ContactService manages Contact objects in-memory (no database).
 *
 * Requirements:
 * - Add contacts with a unique ID
 * - Delete contacts by contact ID
 * - Update contact fields by contact ID (firstName, lastName, phone, address)
 */
public class ContactService {

    // In-memory storage keyed by contactId
    private final Map<String, Contact> contacts = new HashMap<>();

    /**
     * Adds a contact if the contactId is unique.
     * @param contact Contact to add
     */
    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null.");
        }

        String id = contact.getContactId();
        if (contacts.containsKey(id)) {
            throw new IllegalArgumentException("Contact ID already exists: " + id);
        }

        contacts.put(id, contact);
    }

    /**
     * Deletes a contact by ID.
     * @param contactId ID of contact to delete
     */
    public void deleteContact(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null.");
        }

        if (!contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact ID not found: " + contactId);
        }

        contacts.remove(contactId);
    }

    /**
     * Updates firstName for a given contactId.
     */
    public void updateFirstName(String contactId, String firstName) {
        Contact contact = getExistingContact(contactId);
        contact.setFirstName(firstName);
    }

    /**
     * Updates lastName for a given contactId.
     */
    public void updateLastName(String contactId, String lastName) {
        Contact contact = getExistingContact(contactId);
        contact.setLastName(lastName);
    }

    /**
     * Updates phone for a given contactId.
     */
    public void updatePhone(String contactId, String phone) {
        Contact contact = getExistingContact(contactId);
        contact.setPhone(phone);
    }

    /**
     * Updates address for a given contactId.
     */
    public void updateAddress(String contactId, String address) {
        Contact contact = getExistingContact(contactId);
        contact.setAddress(address);
    }

    /**
     * Helper: returns the contact if it exists, otherwise throws.
     */
    private Contact getExistingContact(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null.");
        }

        Contact contact = contacts.get(contactId);
        if (contact == null) {
            throw new IllegalArgumentException("Contact ID not found: " + contactId);
        }

        return contact;
    }

    /**
     * Helper used by tests to verify state.
     * Not required by the prompt, but very useful for unit testing.
     */
    public Contact getContact(String contactId) {
        return contacts.get(contactId);
    }
}
