package commands;

import ui.Ui;

import exceptions.EmptyTaskListException;
import exceptions.InvalidTaskNumberException;

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
    public void execute(app.Solace solace) throws InvalidTaskNumberException, EmptyTaskListException {
        String status = solace.getTaskList().removeTask(index);
        Ui ui = solace.getUi();
        ui.printMessage(status + solace.getTaskList().getSize());
    }

    @Override
    public void logExecution() {
        System.out.println("DeleteTask Command is executed");
    }
}
