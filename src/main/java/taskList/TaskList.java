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

    public String markTask(int num) {
        // Error handling
        if (num < 1 || this.tasks.get(num-1) == null) {
            return "Invalid task number.\n";
        }
        Task target = this.tasks.get(num-1);
        target.markDone();
        return "Nice! I've marked this task as done: \n\t" + target.toString() + "\n";
    }

    public String unmarkTask(int num) {
        // Error handling
        if (num < 1 || this.tasks.get(num-1) == null || !this.tasks.get(num-1).getStatus()) {
            return "Invalid task number or Task is not marked done yet.\n";
        }
        Task target = this.tasks.get(num-1);
        target.unmark();
        return "OK, I've marked this task as not done yet: \n\t" + target.toString() + "\n";
    }

    public String printTasks() {
        StringBuilder taskList = new StringBuilder();

        if (tasks.isEmpty()) {
            taskList.append("No tasks found.\n");
        }

        for (int i = 0; i < tasks.size(); ++i) {
            // change to 1 indexed
            String entry = (i+1) + "." + tasks.get(i).toString() + "\n";
            taskList.append(entry);
        }

        return taskList.toString();
    }
}
