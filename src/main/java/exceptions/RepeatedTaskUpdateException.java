package exceptions;

/**
 * Represents an exception thrown when a task has already been updated before
 */
public class RepeatedTaskUpdateException extends Exception {

    public RepeatedTaskUpdateException() {
        super("Error! This task has already been updated before.");
    }

}
