package commands;
import java.io.File;
import java.io.IOException;

import app.Solace;
import storage.Storage;
import ui.Ui;

/**
 * Represents the command to save the task list to a .txt file
 *
 */
public class SaveCommand extends Command {

    private String filePath;
    private final String FILE_NAME = "taskList.txt";

    /**
     * Creates a new SaveCommand object
     *
     * @param filePath the file path to save the task list
     */
    public SaveCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute(Solace solace) {
        Ui ui = solace.getUi();
        Storage storage = solace.getStorage();
        try {
            storage.save(solace.getTaskList());
            ui.printMessage("Task list saved successfully in " + filePath + File.separator + FILE_NAME);
        } catch (IOException e) {
            ui.printMessage("An error occurred while saving the task list");
        }
    }

    @Override
    public void logExecution() {
        System.out.println("Save Command is executed");
    }

}
