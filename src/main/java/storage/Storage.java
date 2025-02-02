package storage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import task.DeadlineTask;
import task.EventTask;
import task.Task;
import task.ToDoTask;
import taskList.TaskList;

public class Storage {

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    public void save(TaskList taskList) throws IOException {
        ArrayList<Task> tasks = taskList.getList();
        File file = new File(filePath + File.separator + "taskList.txt");
        if (!file.getParentFile().exists()) {
            System.out.println("Directory does not exist");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            int index = 1;
            for (Task task : tasks) {
                writer.write(convertTaskToCommand(task, index));
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


    private static String convertTaskToCommand(Task task, int index) {
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
