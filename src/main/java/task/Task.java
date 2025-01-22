package task;

public class Task {

    StringBuilder description = new StringBuilder();

    public Task(String description) {
        this.description.append(description);
    }

    public String getDescription() {
        return description.toString();
    }
}
