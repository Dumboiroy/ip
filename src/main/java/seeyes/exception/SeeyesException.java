package seeyes.exception;

/**
 * Base exception class for the Seeyes application. This serves as the parent class for all application-specific
 * exceptions and provides a common exception hierarchy for the Seeyes task management system.
 */
public class SeeyesException extends RuntimeException {
    public SeeyesException() {
    }

    public SeeyesException(String message) {
        super(message);
    }
}
