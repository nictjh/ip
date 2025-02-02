package exceptions;

/**
 * Represents an exception thrown when a command is missing an argument
 */
public class MissingArgumentException extends Exception {

    public MissingArgumentException(String message) {
        super(message);
    }

}
