package seeyes.exception;

/**
 * Exception thrown when an invalid task number is provided. This occurs when the user refers to a task using a number
 * that is out of bounds or does not correspond to an existing task.
 */
public class InvalidTaskNumberException extends RuntimeException {
    public InvalidTaskNumberException() {
    }

    public InvalidTaskNumberException(String message) {
        super(message);
    }
}
