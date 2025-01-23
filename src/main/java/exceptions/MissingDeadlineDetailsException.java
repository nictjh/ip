package exceptions;

public class MissingDeadlineDetailsException extends MissingArgumentException {

    public MissingDeadlineDetailsException() {
        super("Error! The deadline details cannot be empty.\nFollow the format: deadline <task> /by <date>");
    }

}
