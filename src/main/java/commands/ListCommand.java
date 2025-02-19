package commands;
import app.Solace;
import ui.Ui;

/**
 * Represents the command to list all tasks in the task list
 *
 */
public class ListCommand extends Command {

    @Override
    public String execute(app.Solace solace) {
        logExecution();
        displayStatusMessage(solace, solace.getTaskList().printTasks());
        return solace.getTaskList().printTasks();
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
        System.out.println("List Command is executed");
    }
}
