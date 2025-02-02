package exceptions;

/**
 * Represents an exception thrown when an unknown command is entered
 */
public class UnknownCommandException extends Exception {

    public UnknownCommandException() {
        super("Error! Unknown command please try again with another valid command");
    }

}
