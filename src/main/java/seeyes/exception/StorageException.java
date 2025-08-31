package seeyes.exception;

/**
 * Exception thrown when there are issues with storage operations.
 * This includes problems reading from or writing to the storage file.
 */
public class StorageException extends RuntimeException {
    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }
}
