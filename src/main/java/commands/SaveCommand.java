package commands;
import app.Solace;
import ui.Ui;
import storage.Storage;
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
