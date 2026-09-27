package com.gerushays.cs320.exception;

public class RecordNotFoundException extends IllegalArgumentException {
    public RecordNotFoundException(String id) {
        super("No item was found with ID '" + id + "'.");
    }
}
