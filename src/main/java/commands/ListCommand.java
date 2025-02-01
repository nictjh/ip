package commands;
import app.Solace;
import ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(Solace solace) {
        Ui ui = solace.getUi();
        ui.printMessage(solace.getTaskList().printTasks());
    }

    @Override
    public void logExecution() {
        System.out.println("List Command is executed");
    }
}
