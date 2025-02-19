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
    public String execute(Solace solace) {
        logExecution();
        Storage storage = solace.getStorage();
        try {
            storage.save(solace.getTaskList());
            displayStatusMessage(solace, "Task list saved successfully in " + filePath
                    + File.separator + FILE_NAME);
            return "Task list saved successfully in " + filePath + File.separator + FILE_NAME;
        } catch (IOException e) {
            displayStatusMessage(solace, "An error occurred while saving the task list");
            return "An error occurred while saving the task list";
        }
    }
    /**
     * Displays the status message of the command execution
     *
     * @param solace The Solace instance to get the UI instance
     * @param message The status message to display
     */
    private void displayStatusMessage(Solace solace, String message) {
        Ui ui = solace.getUi();
        ui.printMessage(message);
    }
    @Override
    public void logExecution() {
        System.out.println("Save Command is executed");
    }

}
