package commands;
import app.Solace;
import tasklist.TaskList;
import ui.Ui;

/**
 * Represents the command find tasks by keyword.
 *
 */
public class FindCommand extends Command {

    private String keyword;

    public FindCommand(String target) {
        this.keyword = target;
    }

    @Override
    public String execute(Solace solace) {
        TaskList taskList = solace.getTaskList(); // get TaskList from Solace
        String statusMsg = taskList.findTasksByKeyword(this.keyword);
        displayStatusMessage(solace, statusMsg);
        return statusMsg;
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
        System.out.println("Find Command is executed");
    }
}
