package seeyes.exception;

/**
 * Exception thrown when user input cannot be parsed into a valid command.
 * This occurs when the command format is incorrect or unrecognized.
 */
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException() {
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}
