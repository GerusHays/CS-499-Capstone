package com.gerushays.cs320.service;

import com.gerushays.cs320.model.Contact;
import com.gerushays.cs320.repository.InMemoryRepository;
import com.gerushays.cs320.repository.Repository;
import java.util.List;

public class ContactService {
    private final Repository<Contact> contacts;
    public ContactService() { this(new InMemoryRepository<>()); }
    public ContactService(Repository<Contact> contacts) { this.contacts = contacts; }

    public void addContact(Contact contact) { contacts.add(contact); }
    public void deleteContact(String id) { contacts.deleteById(id); }
    public Contact getContact(String id) { return contacts.requireById(id); }
    public List<Contact> getAllContacts() { return contacts.findAll(); }
    public void updateFirstName(String id, String value) { getContact(id).setFirstName(value); }
    public void updateLastName(String id, String value) { getContact(id).setLastName(value); }
    public void updatePhone(String id, String value) { getContact(id).setPhone(value); }
    public void updateAddress(String id, String value) { getContact(id).setAddress(value); }
}
