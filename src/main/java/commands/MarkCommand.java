package commands;

import java.util.ArrayList;

import app.Solace;
import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;
import tasklist.TaskList;
import ui.Ui;

/**
 * Represents the command to mark a task as done
 */
public class MarkCommand extends Command {

    // private final int index;
    private ArrayList<Integer> indexes;

    /**
     * Creates a new MarkCommand object
     *
     * @param indexes The indexes of the task to be marked as done, should be 1-indexed
     */
    public MarkCommand(int ... indexes) {
        this.indexes = new ArrayList<>();
        for (int i : indexes) {
            this.indexes.add(i);
        }
    }

    @Override
    public String execute(Solace solace) throws InvalidTaskNumberException, RepeatedTaskUpdateException {
        logExecution();
        String status = solace.getTaskList().markTask(indexes);
        displayStatusMessage(solace, status);
        return status;
    }

    /**
     * Executes the command to mark a task as done
     * for testing purposes
     *
     * @param tasklist The tasklist to mark the task as done
     * @throws InvalidTaskNumberException If the task number is invalid or out of range
     * @throws RepeatedTaskUpdateException If the task is already marked as done
     */
    public String execute(TaskList tasklist) throws InvalidTaskNumberException, RepeatedTaskUpdateException {
        String status = tasklist.markTask(indexes);
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
        System.out.println("Mark Command is executed");
    }
}
