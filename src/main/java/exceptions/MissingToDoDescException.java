package exceptions;

public class MissingToDoDescException extends MissingArgumentException {

    public MissingToDoDescException() {
        super("Error! The description of todo command cannot be empty.\nFollow the format: todo <desc>");
    }

}
