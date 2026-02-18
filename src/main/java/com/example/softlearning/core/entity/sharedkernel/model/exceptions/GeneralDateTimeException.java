package com.example.softlearning.core.entity.sharedkernel.model.exceptions;

/**
 * Exception thrown for general DateTime-related errors in the application.
 */
public class GeneralDateTimeException extends RuntimeException {
    public GeneralDateTimeException(String message) {
        super(message);
    }
}
