package exceptions;

/**
 * Represents an exception when an invalid task number is entered
 */
public class InvalidTaskNumberException extends Exception {

    public InvalidTaskNumberException() {
        super("Error! Invalid Task number entered.\nDo /list to check which tasks are available.");
    }
}
