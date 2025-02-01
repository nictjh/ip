package commands;
import app.Solace;
import ui.Ui;
import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;

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

    @Override
    public void logExecution() {
        System.out.println("Mark Command is executed");
    }
}
