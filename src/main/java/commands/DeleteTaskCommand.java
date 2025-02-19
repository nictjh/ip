package commands;

import app.Solace;
import exceptions.EmptyTaskListException;
import exceptions.InvalidTaskNumberException;
import ui.Ui;

/**
 * Represents the command to delete a task from the task list
 *
 */
public class DeleteTaskCommand extends Command {

    private int index;

    /**
     * Creates a new DeleteTaskCommand object
     *
     * @param index The index of the task to be deleted, index should be 1-indexed
     */
    public DeleteTaskCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(app.Solace solace) throws InvalidTaskNumberException, EmptyTaskListException {
        logExecution();
        String status = solace.getTaskList().removeTask(index);
        displayStatusMessage(solace, status + solace.getTaskList().getSize());
        return status + solace.getTaskList().getSize();
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
        System.out.println("DeleteTask Command is executed");
    }
}
