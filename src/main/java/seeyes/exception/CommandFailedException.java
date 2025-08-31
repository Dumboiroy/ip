package seeyes.exception;

/**
 * Exception thrown when a command fails to execute properly. This is a runtime exception that indicates an error
 * occurred during command execution that prevents the command from completing successfully.
 */
public class CommandFailedException extends RuntimeException {
    public CommandFailedException() {
    }

    public CommandFailedException(String message) {
        super(message);
    }
}
