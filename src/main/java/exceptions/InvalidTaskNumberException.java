package exceptions;

public class InvalidTaskNumberException extends Exception {

    public InvalidTaskNumberException() {
        super("Error! Invalid Task number entered.\nDo /list to check which tasks are available.");
    }
}
