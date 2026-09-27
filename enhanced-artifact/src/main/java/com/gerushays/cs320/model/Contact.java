package com.gerushays.cs320.model;

import com.gerushays.cs320.validation.Validation;

public class Contact implements Identifiable {
    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        this.contactId = Validation.requiredText(contactId, "Contact ID", 10);
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setAddress(address);
    }

    @Override public String getId() { return contactId; }
    public String getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    public void setFirstName(String value) { firstName = Validation.requiredText(value, "First name", 10); }
    public void setLastName(String value) { lastName = Validation.requiredText(value, "Last name", 10); }
    public void setPhone(String value) { phone = Validation.phone(value); }
    public void setAddress(String value) { address = Validation.requiredText(value, "Address", 30); }
}
