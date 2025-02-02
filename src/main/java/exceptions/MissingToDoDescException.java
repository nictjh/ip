package exceptions;

/**
 * Represents an exception thrown when the description of a todo command is missing
 */
public class MissingToDoDescException extends MissingArgumentException {

    public MissingToDoDescException() {
        super("Error! The description of todo command cannot be empty.\nFollow the format: todo <desc>");
    }

}
