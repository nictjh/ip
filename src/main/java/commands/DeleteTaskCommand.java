package commands;
import exceptions.EmptyTaskListException;
import exceptions.InvalidTaskNumberException;
import ui.Ui;

public class DeleteTaskCommand extends Command {

    private int index;

    public DeleteTaskCommand(int index) {
        this.index = index;
    }

    public void execute(app.Solace solace) throws InvalidTaskNumberException, EmptyTaskListException {
        String status = solace.getTaskList().removeTask(index);
        Ui ui = solace.getUi();
        ui.printMessage(status + solace.getTaskList().getSize());
    }

    public void logExecution() {
        System.out.println("DeleteTask Command is executed");
    }
}
