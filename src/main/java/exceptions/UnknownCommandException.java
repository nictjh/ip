package exceptions;

public class UnknownCommandException extends Exception {

    public UnknownCommandException() {
        super("Error! Unknown command please try again with another valid command");
    }

}
