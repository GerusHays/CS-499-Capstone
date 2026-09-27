package com.gerushays.cs320.validation;

public final class Validation {
    private Validation() { }

    public static String requiredText(String value, String fieldName, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank.");
        }
        if (value.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " cannot be longer than " + maxLength + " characters.");
        }
        return value;
    }

    public static String phone(String value) {
        if (value == null || !value.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone must be exactly 10 digits.");
        }
        return value;
    }
}
