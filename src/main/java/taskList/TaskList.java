package taskList;

import task.Task;
import java.util.ArrayList;
import exceptions.*;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>(100);
    }

    public String addTask(Task task) {
        this.tasks.add(task);
        String defMsg = "Got it. I've added this task:\n\t";
        return defMsg + task.toString() + "\n";
    }

    public String markTask(int num) throws InvalidTaskNumberException, RepeatedTaskUpdateException {
        // Error handling
        if (num - 1 < 0 || num - 1 >= this.tasks.size()) {
            throw new InvalidTaskNumberException();
        } else if (this.tasks.get(num - 1).getStatus()) {
            throw new RepeatedTaskUpdateException();
        }
        Task target = this.tasks.get(num-1);
        target.markDone();
        return "Nice! I've marked this task as done: \n\t" + target.toString() + "\n";
    }

    public String unmarkTask(int num) throws InvalidTaskNumberException, RepeatedTaskUpdateException {
        // Error handling
        if (num - 1 < 0 || num - 1 >= this.tasks.size()) {
            throw new InvalidTaskNumberException();
        } else if (!this.tasks.get(num - 1).getStatus()) {
            throw new RepeatedTaskUpdateException();
        }
        Task target = this.tasks.get(num-1);
        target.unmark();
        return "OK, I've marked this task as not done yet: \n\t" + target.toString() + "\n";
    }

    public String getSize() {
        return "Now you have " + this.tasks.size() + " tasks in the list.\n";
    }

    public String printTasks() {
        StringBuilder taskList = new StringBuilder();

        if (this.tasks.isEmpty()) {
            return "No tasks found.\n";
        }

        taskList.append("Here are the tasks in your list:\n");
        for (int i = 0; i < this.tasks.size(); ++i) {
            // change to 1 indexed
            String entry = (i+1) + "." + this.tasks.get(i).toString() + "\n";
            taskList.append(entry);
        }

        return taskList.toString();
    }
}
