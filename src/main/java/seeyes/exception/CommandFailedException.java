package seeyes.exception;

/**
 * Exception thrown when a command fails to execute properly.
 * This typically occurs when there are runtime errors during command execution.
 */
public class CommandFailedException extends RuntimeException {
    public CommandFailedException() {
    }

    public CommandFailedException(String message) {
        super(message);
    }
}
