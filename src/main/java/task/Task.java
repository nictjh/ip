package task;

public class Task {

    private final StringBuilder DESCRIPTION = new StringBuilder();
    private boolean isDone;

    public Task(String description) {
        this.DESCRIPTION.append(description);
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
        return this.DESCRIPTION.toString();
    }

    @Override
    public String toString() {
        String checkBox = isDone ? "[X] " : "[ ] ";
        return checkBox + DESCRIPTION.toString();
    }
}
