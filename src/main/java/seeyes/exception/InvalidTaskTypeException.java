package seeyes.exception;

/**
 * Exception thrown when an invalid task type is encountered. This occurs when attempting to create or process a task
 * with a type that is not recognized or supported by the application.
 */
public class InvalidTaskTypeException extends RuntimeException {
    public InvalidTaskTypeException() {
    }

    public InvalidTaskTypeException(String message) {
        super(message);
    }
}
