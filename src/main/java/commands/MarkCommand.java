package commands;
import app.Solace;
import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;
import taskList.TaskList;
import ui.Ui;

public class MarkCommand extends Command {
    private int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(Solace solace) throws InvalidTaskNumberException, RepeatedTaskUpdateException {
        String status = solace.getTaskList().markTask(index);
        Ui ui = solace.getUi();
        ui.printMessage(status);
    }

    public void execute(TaskList tasklist) throws InvalidTaskNumberException, RepeatedTaskUpdateException {
        String status = tasklist.markTask(index);
    }

    @Override
    public void logExecution() {
        System.out.println("Mark Command is executed");
    }
}
