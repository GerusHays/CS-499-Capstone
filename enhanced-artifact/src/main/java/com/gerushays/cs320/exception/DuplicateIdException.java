package com.gerushays.cs320.exception;

public class DuplicateIdException extends IllegalArgumentException {
    public DuplicateIdException(String id) {
        super("An item with ID '" + id + "' already exists.");
    }
}
