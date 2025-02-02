package exceptions;

/**
 * Represents an exception thrown when the deadline details are missing
 */
public class MissingDeadlineDetailsException extends MissingArgumentException {

    public MissingDeadlineDetailsException() {
        super("Error! The deadline details cannot be empty.\nFollow the format: deadline <task> /by <date>");
    }

}
