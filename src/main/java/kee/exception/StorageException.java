package kee.exception;

/**
 * A class to represent an exception related to writing and loading from files.
 */
public class StorageException extends Exception {
    public StorageException(String message) {
        super(message);
    }
}
