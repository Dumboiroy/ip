package seeyes.exception;

/**
 * Exception thrown when an invalid task number is referenced.
 * This occurs when trying to access a task with an index that doesn't exist.
 */
public class InvalidTaskNumberException extends RuntimeException {
    public InvalidTaskNumberException() {
    }

    public InvalidTaskNumberException(String message) {
        super(message);
    }
}
