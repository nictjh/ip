package taskList;
import task.DeadlineTask;
import task.EventTask;
import task.Task;
import java.time.LocalDate;
import java.util.ArrayList;
import exceptions.InvalidTaskNumberException;
import exceptions.EmptyTaskListException;
import exceptions.RepeatedTaskUpdateException;

/**
 * Represents a list of tasks, to be initialized in Solace
 * Contains methods to manipulate the list of tasks
 *
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    /**
     * Creates an empty new TaskList
     * Default constructor
     */
    public TaskList() {
        this.tasks = new ArrayList<>(100);
    }

    /**
     * Creates a new TaskList with a list of tasks
     * Overloaded constructor
     *
     * @param tasks List of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getList() {
        return this.tasks;
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Adds a task to the list
     *
     * @param task the Task object to be added
     * @return A String message indicating the task has been added
     */
    public String addTask(Task task) {
        this.tasks.add(task);
        String defMsg = "Got it. I've added this task:\n\t";
        return defMsg + task.toString() + "\n";
    }

    /**
     * Removes a task from the list
     *
     * @param num The index of the task to be removed, this is 1-indexed
     * @return A String message indicating the task has been removed
     * @throws InvalidTaskNumberException InvalidTaskNumberException If the task number is invalid
     * @throws EmptyTaskListException EmptyTaskListException If the task list is empty
     */
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

    /**
     * Marks a task as done
     *
     * @param num The index of the task to be marked as done, this is 1-indexed
     * @return A String message indicating the task has been marked as done
     * @throws InvalidTaskNumberException InvalidTaskNumberException If the task number is invalid
     * @throws RepeatedTaskUpdateException RepeatedTaskUpdateException If the task is already marked as done
     */
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

    /**
     * Unmarks a task as done
     *
     * @param num The index of the task to be unmarked as done, this is 1-indexed
     * @return A String message indicating the task has been unmarked as done
     * @throws InvalidTaskNumberException InvalidTaskNumberException If the task number is invalid
     * @throws RepeatedTaskUpdateException RepeatedTaskUpdateException If the task is already marked as not done
     */
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

    /**
     * Prints the list of tasks to be displayed in 1-indexed format
     *
     * @return A String message containing the list of tasks
     */
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

    /**
     * Prints the list of tasks to be displayed in 1-indexed format
     * Overloaded method to print from specific ArrayList
     *
     * @param listOfTasks The ArrayList<Task> to be printed
     * @return A String message containing the list of tasks
     */
    public String printTask(ArrayList<Task> listOfTasks) {

        StringBuilder taskList = new StringBuilder();

        if (listOfTasks.isEmpty()) {
            return "No tasks found.\n";
        }

        taskList.append("Here are the tasks found:\n");
        for (int i = 0; i < listOfTasks.size(); ++i) {
            // change to 1 indexed
            String entry = (i+1) + "." + listOfTasks.get(i).toString() + "\n";
            taskList.append(entry);
        }

        return taskList.toString();
    }

    /**
     * Finds tasks that match the date provided
     *
     * @param date The date to be matched
     * @return A String message containing the list of tasks that match the date
     */
    public String findTasksByDate(LocalDate date) {
//        System.out.println("Check running");
//        System.out.println("Query: " + date);
        ArrayList<Task> matched = new ArrayList<>();

        for (Task task : this.tasks) {
            if (task instanceof DeadlineTask) {
                LocalDate taskDate = ((DeadlineTask) task).getLocalDate();

                if (taskDate == null) {
//                    System.out.println("DeadlineTask has null date. Skipping...");
                    continue;  // skip this task
                }

//                System.out.println("Checking~~~~~~~~~~~ : " + taskDate);
                if (taskDate.toString().trim().equals(date.toString().trim())) {
                    matched.add(task);
//                    System.out.println("Added something from Deadline");
                }
            } else if (task instanceof EventTask) {
                LocalDate eventDate = ((EventTask) task).getStartLocalDate();
                LocalDate eventEndDate = ((EventTask) task).getEndLocalDate();

                if (eventDate == null && eventEndDate == null) {
//                    System.out.println("Skipping EventTask due to null start and end dates.");
                    continue;
                }

                // Match conditions:
                boolean startMatches = (eventDate != null && eventDate.toString().trim().equals(date.toString().trim()));
                boolean endMatches = (eventEndDate != null && eventEndDate.toString().trim().equals(date.toString().trim()));

                // Add to matched list if either start or end date matches
                if (startMatches || endMatches) {
                    matched.add(task);
//                    System.out.println("EventTask matched and added!");
                }
            }
        }
//        System.out.println("Matched Tasks: " + matched.size());
        return printTask(matched);
    }




}
