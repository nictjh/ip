package exceptions;

/**
 * Represents an exception thrown when the event details are missing
 */
public class MissingEventDetailsException extends MissingArgumentException {

    public MissingEventDetailsException() {
        super("Error! The event details cannot be empty.\nFollow the format: event <task> /from <date> /to <date>");
    }

}
