package seeyes.exception;

/**
 * Exception thrown when an invalid command is encountered. This occurs when the user input cannot be parsed into a
 * valid command or when the command format is incorrect.
 */
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException() {
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}
