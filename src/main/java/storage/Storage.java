package storage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import task.DeadlineTask;
import task.EventTask;
import task.Task;
import task.ToDoTask;
import taskList.TaskList;

/**
 * Handles reading from and writing to the task storage file
 * The file is stored in the specified file path
 */
public class Storage {

    private final String filePath;

    /**
     * Initializes the storage object with the specified file path
     *
     * @param filePath The file path to store the task list
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the task list from the storage file
     *
     * @return The TaskList object loaded from the storage file
     */
    public TaskList load() {
        ArrayList<Task> newTask = new ArrayList<Task>(100);
        File file = new File(this.filePath + File.separator + "taskList.txt");
        if (!file.exists()) {
            return new TaskList(newTask);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("todo")) {
                    newTask.add(new ToDoTask(line.substring(5).trim()));
                } else if (line.startsWith("deadline")) {
                    String[] parts = line.substring(9).split(" /by ");
                    newTask.add(new DeadlineTask(parts[0], parts[1]));
                } else if (line.startsWith("event")) {
                    String[] parts = line.substring(6).split(" /from | /to "); //Splits OR
                    newTask.add(new EventTask(parts[0], parts[1], parts[2]));
                } else if (line.startsWith("mark")) {
                    int markIndex = Integer.parseInt(line.substring(5).trim()) - 1; // 0 indexed
                    newTask.get(markIndex).markDone();
                }
            }
            // System.out.println("Tasks loaded from storage");
        } catch (IOException e) {
            System.out.println("An error occurred while loading the task list from storage");
        }

        return new TaskList(newTask);
    }

    /**
     * Saves the task list to the storage file in .txt
     *
     * @param taskList The TaskList object to be saved
     * @throws IOException If an error occurs while saving the task list
     */
    public void save(TaskList taskList) throws IOException {
        ArrayList<Task> tasks = taskList.getList();
        File file = new File(filePath + File.separator + "taskList.txt");
        if (!file.getParentFile().exists()) {
            System.out.println("Directory does not exist");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            int index = 1;
            for (Task task : tasks) {
                writer.write(convertTaskToCommand(task));
                writer.newLine();
                if (task.getStatus()) {
                    writer.write("mark " + index);
                    writer.newLine();
                }
                index++;
            }
            //System.out.println("Task list saved successfully in " + file.toString());
        } catch (IOException e) {
            throw new IOException("An error occurred while saving the task list");
        }
    }


    /**
     * Converts a task object to a command string
     * Save command string for easy loading
     *
     * @param task The task object to be converted
     * @return The command string representing the task
     */
    private static String convertTaskToCommand(Task task) {
        if (task instanceof ToDoTask) {
            return "todo " + task.getDescription();
        } else if (task instanceof DeadlineTask) {
            // Account for the new format of DateTime
            if ((((DeadlineTask) task).getDateTime() == null)) {
                return "deadline " + task.getDescription() + " /by " + ((DeadlineTask) task).getDeadline();
            } else {
                return "deadline " + task.getDescription() + " /by " + ((DeadlineTask) task).writeDateTime();
            }

        } else if (task instanceof EventTask) {
            if ((((EventTask) task).getStartDateTime() != null) && (((EventTask) task).getEndDateTime() != null)) {
                return "event " + task.getDescription() + " /from "
                        + ((EventTask) task).writeDateTime(((EventTask) task).getStartDateTime())
                        + " /to " + ((EventTask) task).writeDateTime(((EventTask) task).getEndDateTime());
            } else if ((((EventTask) task).getStartDateTime() == null)
                    && (((EventTask) task).getEndDateTime() != null)) {
                return "event " + task.getDescription() + " /from " + ((EventTask) task).getFrom() + " /to "
                        + ((EventTask) task).writeDateTime(((EventTask) task).getEndDateTime());
            } else if ((((EventTask) task).getStartDateTime() != null)
                    && (((EventTask) task).getEndDateTime() == null)) {
                return "event " + task.getDescription() + " /from "
                        + ((EventTask) task).writeDateTime(((EventTask) task).getStartDateTime()) + " /to "
                        + ((EventTask) task).getTo();
            } else {
                return "event " + task.getDescription() + " /from " + ((EventTask) task).getFrom() + " /to "
                        + ((EventTask) task).getTo();
            }

        } else {
            return "";
        }
    }
}
