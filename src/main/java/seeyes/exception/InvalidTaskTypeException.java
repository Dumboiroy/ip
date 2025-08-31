package seeyes.exception;

/**
 * Exception thrown when an invalid task type is encountered.
 * This occurs when parsing task strings with unrecognized task type prefixes.
 */
public class InvalidTaskTypeException extends RuntimeException {
    public InvalidTaskTypeException() {
    }

    public InvalidTaskTypeException(String message) {
        super(message);
    }
}
