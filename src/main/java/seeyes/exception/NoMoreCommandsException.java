package seeyes.exception;

/**
 * Exception thrown when no more commands are available from the input source.
 * This typically occurs when the input stream is exhausted or closed.
 */
public class NoMoreCommandsException extends RuntimeException {
    public NoMoreCommandsException() {
    }

    public NoMoreCommandsException(String message) {
        super(message);
    }
}
