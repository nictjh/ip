package task;

/**
 * Represents a general task that all types of tasks should extend from
 * Contains a description and a status of whether the task is done
 */
public class Task {

    private final StringBuilder description = new StringBuilder();
    private boolean isDone;

    /**
     * Creates a new task with a description
     *
     * @param description The description of the task
     */
    public Task(String description) {
        this.description.append(description);
        this.isDone = false;
    }

    public boolean getStatus() {
        return isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String getDescription() {
        return this.description.toString();
    }

    @Override
    public String toString() {
        String checkBox = isDone ? "[X] " : "[ ] ";
        return checkBox +  description.toString();
    }
}
