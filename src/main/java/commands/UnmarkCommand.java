package commands;
import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;
import ui.Ui;
import app.Solace;

public class UnmarkCommand extends Command {
    private int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(Solace solace) throws InvalidTaskNumberException, RepeatedTaskUpdateException, RepeatedTaskUpdateException, InvalidTaskNumberException {
        String status = solace.getTaskList().unmarkTask(index);
        Ui ui = solace.getUi();
        ui.printMessage(status);
    }

    @Override
    public void logExecution() {
        System.out.println("Unmark Command is executed");
    }
}
