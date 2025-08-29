package seeyes.exception;

public class CommandFailedException extends RuntimeException {
    public CommandFailedException() {
    }

    public CommandFailedException(String message) {
        super(message);
    }
}
