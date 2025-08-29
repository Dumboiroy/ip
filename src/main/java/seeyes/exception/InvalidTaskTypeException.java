package seeyes.exception;

public class InvalidTaskTypeException extends RuntimeException {
    public InvalidTaskTypeException() {
    }

    public InvalidTaskTypeException(String message) {
        super(message);
    }
}
