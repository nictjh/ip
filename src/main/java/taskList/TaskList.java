package taskList;

import task.Task;
import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>(100);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public String printTasks() {
        StringBuilder taskList = new StringBuilder();

        if (tasks.isEmpty()) {
            taskList.append("No tasks found.\n");
        }

        for (int i = 0; i < tasks.size(); ++i) {
            // change to 1 indexed
            String entry = (i+1) + ". " + tasks.get(i).getDescription() + "\n";
            taskList.append(entry);
        }

        return taskList.toString();
    }
}
