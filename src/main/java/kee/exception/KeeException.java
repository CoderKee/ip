package kee.exception;

/**
 * Represents exception related to the logical error or missing arguments from user input.
 */
public class KeeException extends Exception {
    public KeeException(String message) {
        super(message);
    }
}
