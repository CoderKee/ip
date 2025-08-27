package kee.exception;

/**
 * A class to represent exception related to the logical error or missing arguments from user input.
 */
public class KeeException extends Exception {
    public KeeException(String message) {
        super(message);
    }
}
