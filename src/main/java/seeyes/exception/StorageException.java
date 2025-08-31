package seeyes.exception;

/**
 * Exception thrown when storage operations fail. This occurs when there are issues reading from or writing to the data
 * storage, such as file I/O errors or data corruption.
 */
public class StorageException extends RuntimeException {
    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }
}
