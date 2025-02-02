package commands;
import app.Solace;
import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;
import taskList.TaskList;
import ui.Ui;

/**
 * Represents the command to mark a task as done
 */
public class MarkCommand extends Command {

    private final int index;

    /**
     * Creates a new MarkCommand object
     *
     * @param index The index of the task to be marked as done, should be 1-indexed
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(Solace solace) throws InvalidTaskNumberException, RepeatedTaskUpdateException {
        String status = solace.getTaskList().markTask(index);
        Ui ui = solace.getUi();
        ui.printMessage(status);
    }

    /**
     * Executes the command to mark a task as done
     * for testing purposes
     *
     * @param tasklist The tasklist to mark the task as done
     * @throws InvalidTaskNumberException If the task number is invalid or out of range
     * @throws RepeatedTaskUpdateException If the task is already marked as done
     */
    public void execute(TaskList tasklist) throws InvalidTaskNumberException, RepeatedTaskUpdateException {
        String status = tasklist.markTask(index);
    }

    @Override
    public void logExecution() {
        System.out.println("Mark Command is executed");
    }
}
