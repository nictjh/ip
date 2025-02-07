package commands;

import java.util.ArrayList;

import app.Solace;
import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;
import taskList.TaskList;
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
        String status = solace.getTaskList().markTask(indexes);
        Ui ui = solace.getUi();
        ui.printMessage(status);
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

    @Override
    public void logExecution() {
        System.out.println("Mark Command is executed");
    }
}
