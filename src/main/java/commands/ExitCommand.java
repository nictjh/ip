package commands;
import app.Solace;
import ui.Ui;

/**
 * Represents the command to exit the application
 *
 */
public class ExitCommand extends Command {

    @Override
    public String execute(Solace solace) {
        logExecution();
        solace.setAlive();
        return displayGoodbyeMessage(solace);
    }
    /**
     * Displays the status message of the command execution
     *
     * @param solace The Solace instance to get the UI instance
     * @return Random Bye message from the UI instance as a String
     */
    private String displayGoodbyeMessage(Solace solace) {
        Ui ui = solace.getUi();
        String randomByeMsg = ui.getRandomGoodbye();
        ui.printMessage(randomByeMsg + "\n");
        return randomByeMsg;
    }
    @Override
    public void logExecution() {
        System.out.println("Exit Command is executed");
    }
}
