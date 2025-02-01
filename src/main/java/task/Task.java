package task;

public class Task {

    private StringBuilder description = new StringBuilder();
    private boolean isDone;

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
