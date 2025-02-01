package commands;

import app.Solace;
import task.DeadlineTask;
import task.EventTask;
import task.Task;
import task.ToDoTask;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class SaveCommand extends Command {

    private String filePath;
    private final String FILE_NAME = "taskList.txt";

    public SaveCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute(Solace solace) {
        ArrayList<Task> taskList = solace.getTaskList().getList();
        // Define the file path
        File file = new File(filePath + File.separator + FILE_NAME);
        System.out.println(file.toString());
        if (!file.getParentFile().exists()) {
            System.out.println("Directory does not exist");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            int index = 1;
            for (Task task : taskList) {
                writer.write(convertTaskToCommand(task, index));
                writer.newLine();
                if (task.getStatus()) {
                    writer.write("mark " + index);
                    writer.newLine();
                }
                index++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the task list");
        }
    }

    @Override
    public void logExecution() {
        System.out.println("Save Command is executed");
    }

    private static String convertTaskToCommand(Task task, int index) {
        if (task instanceof ToDoTask) {
            return "todo " + task.getDescription();
        } else if (task instanceof DeadlineTask) {
            return "deadline " + task.getDescription() + " /by " + ((DeadlineTask) task).getDeadline();
        } else if (task instanceof EventTask) {
            return "event " + task.getDescription() + " /from " + ((EventTask) task).getFrom() + " /to "
                    + ((EventTask) task).getTo();
        } else {
            return "";
        }
    }
}
