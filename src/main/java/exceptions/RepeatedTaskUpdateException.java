package exceptions;

public class RepeatedTaskUpdateException extends Exception {

    public RepeatedTaskUpdateException() {
        super("Error! This task has already been updated before.");
    }

}
