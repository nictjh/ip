package commands;

import app.Solace;
import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;
import ui.Ui;

/**
 * Represents the command to unmark a task as done
 */
public class UnmarkCommand extends Command {

    private int index;

    /**
     * Creates a new UnmarkCommand object
     *
     * @param index The index of the task to be unmarked, needs to be 1-indexed
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(Solace solace) throws InvalidTaskNumberException, RepeatedTaskUpdateException,
            RepeatedTaskUpdateException, InvalidTaskNumberException {
        String status = solace.getTaskList().unmarkTask(index);
        displayStatusMessage(solace, status);
        return status;
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
        System.out.println("Unmark Command is executed");
    }
}
