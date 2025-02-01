package taskList;

import jdk.jfr.Event;
import task.DeadlineTask;
import task.EventTask;
import task.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import exceptions.*;
import task.ToDoTask;

import java.io.IOException;
import java.io.File;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>(100);
    }

    //Overload the constructor later to take in a list of tasks
    public TaskList(File file) {
        this.tasks = new ArrayList<>(100);
        loadTasks(file);
    }

    public ArrayList<Task> getList() {
        return this.tasks;
    }

    public String addTask(Task task) {
        this.tasks.add(task);
        String defMsg = "Got it. I've added this task:\n\t";
        return defMsg + task.toString() + "\n";
    }

    public String removeTask(int num) throws InvalidTaskNumberException, EmptyTaskListException {
        if (this.tasks.isEmpty()) {
            throw new EmptyTaskListException();
        }
        if (num - 1 < 0 || num - 1 >= this.tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        Task target = this.tasks.get(num-1);
        this.tasks.remove(num-1); // which might be faster
        String defMsg = "Noted. I've removed this task:\n\t";
        return defMsg + target.toString() + "\n";
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

    public void loadTasks(File file) {
        // Load tasks from file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("todo")) {
                    this.tasks.add(new ToDoTask(line.substring(5).trim()));
                } else if (line.startsWith("deadline")) {
                    String[] parts = line.substring(9).split(" /by ");
                    this.tasks.add(new DeadlineTask(parts[0], parts[1]));
                } else if (line.startsWith("event")) {
                    String[] parts = line.substring(6).split(" /from | /to "); //Splits OR
                    this.tasks.add(new EventTask(parts[0], parts[1], parts[2]));
                } else if (line.startsWith("mark")) {
                    int markIndex = Integer.parseInt(line.substring(5).trim()) - 1; // 0 indexed
                    this.tasks.get(markIndex).markDone();
                }
            }
//            System.out.println("Tasks loaded from storage");
        } catch (IOException e) {
            System.out.println("An error occurred while loading the task list from storage");
        }
    }
}
