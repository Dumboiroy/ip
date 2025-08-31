package seeyes.exception;

/**
 * Base exception class for Seeyes application-specific errors.
 * All custom exceptions in the Seeyes application extend this class.
 */
public class SeeyesException extends RuntimeException {
    public SeeyesException() {
    }

    public SeeyesException(String message) {
        super(message);
    }
}
