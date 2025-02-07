package commands;
import ui.Ui;

/**
 * Represents the command to list all tasks in the task list
 *
 */
public class ListCommand extends Command {

    @Override
    public String execute(app.Solace solace) {
        Ui ui = solace.getUi();
        ui.printMessage(solace.getTaskList().printTasks());
        return solace.getTaskList().printTasks();
    }

    @Override
    public void logExecution() {
        System.out.println("List Command is executed");
    }
}
