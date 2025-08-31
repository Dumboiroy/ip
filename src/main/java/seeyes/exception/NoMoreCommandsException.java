package seeyes.exception;

/**
 * Exception thrown when there are no more commands available to read. This typically occurs when the input stream
 * reaches EOF or when the application runs out of commands to process.
 */
public class NoMoreCommandsException extends RuntimeException {
    public NoMoreCommandsException() {
    }

    public NoMoreCommandsException(String message) {
        super(message);
    }
}
