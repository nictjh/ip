package commands;
import app.Solace;
import ui.Ui;

/**
 * Represents the command to exit the application
 *
 */
public class ExitCommand extends Command {

    @Override
    public void execute(Solace solace) {
        solace.setAlive();
        Ui ui = solace.getUi();
        ui.printMessage("Bye. Hope to see you again soon!\n");
    }

    @Override
    public void logExecution() {
        System.out.println("Exit Command is executed");
    }
}
