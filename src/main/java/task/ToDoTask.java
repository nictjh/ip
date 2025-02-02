package task;

/**
 * Represents a task in the task list
 * Contains the description of the task
 */
public class ToDoTask extends Task {

    /**
     * Creates a new ToDoTask object
     *
     * @param desc Description of the task
     */
    public ToDoTask(String desc) {
        super(desc);
    }

    @Override
    public String toString() {
        String toDoId = "[T]";
        return toDoId + super.toString();
    }
}
