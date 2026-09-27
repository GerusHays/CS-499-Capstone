/**
 * Contact object that enforces all required field constraints.
 *
 * Requirements:
 * - contactId: required, unique, not null, not updatable, max length 10
 * - firstName: required, not null, max length 10
 * - lastName: required, not null, max length 10
 * - phone: required, not null, exactly 10 digits
 * - address: required, not null, max length 30
 */
public class Contact {

    private final String contactId;     // Not updatable by design (no setter)
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        // Validate and assign in constructor to ensure object is always valid
        validateContactId(contactId);
        validateFirstName(firstName);
        validateLastName(lastName);
        validatePhone(phone);
        validateAddress(address);

        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    // Getters (contactId only has getter to prevent updates)
    public String getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    // Setters for updatable fields only (per requirements)
    public void setFirstName(String firstName) {
        validateFirstName(firstName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        validateLastName(lastName);
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        validatePhone(phone);
        this.phone = phone;
    }

    public void setAddress(String address) {
        validateAddress(address);
        this.address = address;
    }

    // --- Validation Helpers ---

    private static void validateContactId(String contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null.");
        }
        if (contactId.length() > 10) {
            throw new IllegalArgumentException("Contact ID cannot be longer than 10 characters.");
        }
    }

    private static void validateFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name cannot be null.");
        }
        if (firstName.length() > 10) {
            throw new IllegalArgumentException("First name cannot be longer than 10 characters.");
        }
    }

    private static void validateLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null.");
        }
        if (lastName.length() > 10) {
            throw new IllegalArgumentException("Last name cannot be longer than 10 characters.");
        }
    }

    private static void validatePhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone cannot be null.");
        }
        // Exactly 10 digits
        if (!phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits.");
        }
    }

    private static void validateAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null.");
        }
        if (address.length() > 30) {
            throw new IllegalArgumentException("Address cannot be longer than 30 characters.");
        }
    }
}
