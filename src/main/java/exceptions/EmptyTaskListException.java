package exceptions;

public class EmptyTaskListException extends Exception {

    public EmptyTaskListException() {
        super("Error! TaskList is completely empty!\nAdd some tasks first!");
    }
}
