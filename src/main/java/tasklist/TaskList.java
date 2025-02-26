package tasklist;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;

import exceptions.EmptyTaskListException;
import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;
import task.DeadlineTask;
import task.EventTask;
import task.Task;

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
        assert tasks != null : "Task List should not be null";
        this.tasks = tasks;
    }

    public ArrayList<Task> getList() {
        return this.tasks;
    }

    public Task getTask(int index) {
        assert index >= 0 && index < this.tasks.size() : "Index out of bounds";
        return this.tasks.get(index);
    }

    /**
     * Adds a task to the list
     *
     * @param task the Task object to be added
     * @return A String message indicating the task has been added
     */
    public String addTask(Task task) {
        assert task != null : "Task to be added should not be null";
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
        Task target = this.tasks.get(num - 1);
        this.tasks.remove(num - 1); // which might be faster
        String defMsg = "Noted. I've removed this task:\n\t";
        return defMsg + target.toString() + "\n";
    }

    /**
     * Marks tasks as done.
     *
     * @param nums The indexes of the task to be marked as done, this is 1-indexed
     * @return A String message indicating the task that has been marked as done
     * @throws InvalidTaskNumberException InvalidTaskNumberException If the task number is invalid
     * @throws RepeatedTaskUpdateException RepeatedTaskUpdateException If the task is already marked as done
     */
    public String markTask(ArrayList<Integer> nums) throws InvalidTaskNumberException, RepeatedTaskUpdateException {
        StringBuilder keepTrack = new StringBuilder();
        for (Integer num : nums) {
            if (num - 1 < 0 || num - 1 >= this.tasks.size()) {
                throw new InvalidTaskNumberException();
            } else if (this.tasks.get(num - 1).showStatus()) {
                throw new RepeatedTaskUpdateException();
            }
            Task target = this.tasks.get(num - 1);
            target.markDone();
            keepTrack.append(target.toString()).append("\n");
        }

        return "Nice! I've marked this task as done: \n" + keepTrack.toString().trim();
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
        } else if (!this.tasks.get(num - 1).showStatus()) {
            throw new RepeatedTaskUpdateException();
        }
        Task target = this.tasks.get(num - 1);
        target.unmark();
        return "OK, I've marked this task as not done yet: \n\t" + target.toString() + "\n";
    }

    /**
     * Prints the number of tasks in the taskList
     *
     */
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

        sortTasksByDate();

        taskList.append("Here are the tasks in your list:\n");
        for (int i = 0; i < this.tasks.size(); ++i) {
            // change to 1 indexed
            String entry = (i + 1) + "." + this.tasks.get(i).toString() + "\n";
            taskList.append(entry);
        }

        return taskList.toString();
    }

    /**
     * Prints the list of tasks to be displayed in 1-indexed format
     * Overloaded method to print from specific ArrayList
     *
     * @param listOfTasks The ArrayList of Task to be printed
     * @return A String message containing the list of tasks
     */
    public String printTask(ArrayList<Task> listOfTasks) {

        StringBuilder taskList = new StringBuilder();

        if (listOfTasks.isEmpty()) {
            return "No tasks found.\n";
        }

        // Sort
        sortTasksByDate();

        taskList.append("Here are the tasks found:\n");
        for (int i = 0; i < listOfTasks.size(); ++i) {
            // change to 1 indexed
            String entry = (i + 1) + "." + listOfTasks.get(i).toString() + "\n";
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
        assert date != null : "Date should not be null";
        ArrayList<Task> matched = new ArrayList<>();

        for (Task task : this.tasks) {
            if (task instanceof DeadlineTask) {
                LocalDate taskDate = ((DeadlineTask) task).getLocalDate();

                if (taskDate == null) {
                    continue; // Skip this task
                }

                if (taskDate.toString().trim().equals(date.toString().trim())) {
                    matched.add(task);
                }
            } else if (task instanceof EventTask) {
                LocalDate eventDate = ((EventTask) task).getStartLocalDate();
                LocalDate eventEndDate = ((EventTask) task).getEndLocalDate();

                if (eventDate == null && eventEndDate == null) {
                    continue;
                }

                // Match conditions:
                boolean startMatches = (eventDate != null
                        && eventDate.toString().trim().equals(date.toString().trim()));
                boolean endMatches = (eventEndDate != null
                        && eventEndDate.toString().trim().equals(date.toString().trim()));

                if (startMatches || endMatches) {
                    matched.add(task);
                }
            }
        }
        return printTask(matched);
    }
    /**
     * Finds tasks that match the keyword provided
     *
     * @param keyword The keyword to be matched
     * @return A String message containing the list of tasks that match the keyword
     */
    public String findTasksByKeyword(String keyword) {
        ArrayList<Task> matched = new ArrayList<>();
        for (Task task : this.tasks) {
            if (task.getDescription().contains(keyword)) {
                matched.add(task);
            }
        }
        return printTask(matched);
    }


    private LocalDateTime getTaskDate(Task task) {
        if (task instanceof DeadlineTask) {
            return ((DeadlineTask) task).getDateTime();
        } else if (task instanceof EventTask) {
            return ((EventTask) task).getStartDateTime();
        } else {
            return null; // if not DeadlineTask or EventTask
        }
    }

    /**
     * Sorts tasks by date
     *
     * @return
     */
    public void sortTasksByDate() {
        // Only for instanceof DeadlineTask and EventTask
        LocalDate currLocalDate = LocalDate.now();
        this.tasks.sort(Comparator.comparing(task -> {
            LocalDateTime taskDate = getTaskDate(task);
            if (taskDate == null) {
                return Long.MAX_VALUE; // put it at the end
            }
            return Math.abs(ChronoUnit.DAYS.between(currLocalDate, taskDate.toLocalDate()));
        }, Comparator.nullsLast(Comparator.naturalOrder())));
        System.out.println("Sorted taskList");
    }
}
