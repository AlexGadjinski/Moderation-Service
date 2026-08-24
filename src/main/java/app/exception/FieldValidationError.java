package app.exception;

public record FieldValidationError(String field, String message) {}
