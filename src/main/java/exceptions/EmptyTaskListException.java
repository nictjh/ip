package exceptions;

/**
 * Represents an exception when the TaskList is empty
 */
public class EmptyTaskListException extends Exception {

    public EmptyTaskListException() {
        super("Error! TaskList is completely empty!\nAdd some tasks first!");
    }
}
