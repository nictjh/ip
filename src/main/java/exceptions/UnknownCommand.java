package exceptions;

public class UnknownCommand extends Exception {

    public UnknownCommand() {
        super("Error! Unknown command please try again with another valid command");
    }

}
