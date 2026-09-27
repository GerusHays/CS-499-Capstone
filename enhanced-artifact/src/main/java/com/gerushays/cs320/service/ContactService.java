package com.gerushays.cs320.service;

import com.gerushays.cs320.model.Contact;
import com.gerushays.cs320.repository.InMemoryRepository;
import com.gerushays.cs320.repository.Repository;
import java.util.ArrayList;
import java.util.Comparator;
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

    // This is a simple linear search because the repository is still small and in memory.
    // It also lets a user search either the first or last name without knowing the contact ID.
    public List<Contact> searchByName(String searchTerm) {
        String term = normalizeSearchTerm(searchTerm);
        List<Contact> matches = new ArrayList<>();

        for (Contact contact : contacts.findAll()) {
            String firstName = contact.getFirstName().toLowerCase();
            String lastName = contact.getLastName().toLowerCase();
            if (firstName.contains(term) || lastName.contains(term)) {
                matches.add(contact);
            }
        }
        return List.copyOf(matches);
    }

    // Return a sorted copy so sorting does not change the order of data in the repository.
    public List<Contact> getContactsSortedByName() {
        List<Contact> sorted = new ArrayList<>(contacts.findAll());
        sorted.sort(Comparator.comparing(Contact::getLastName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Contact::getFirstName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Contact::getContactId));
        return List.copyOf(sorted);
    }

    private static String normalizeSearchTerm(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            throw new IllegalArgumentException("Search term cannot be null or blank.");
        }
        return searchTerm.trim().toLowerCase();
    }
}
