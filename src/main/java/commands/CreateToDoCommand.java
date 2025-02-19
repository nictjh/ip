package commands;
import app.Solace;
import task.ToDoTask;
import ui.Ui;

/**
 * Represents the command to create a new ToDo task
 *
 */
public class CreateToDoCommand extends Command {

    private String taskDescription;

    /**
     * Creates a new CreateToDoCommand object
     *
     * @param description Description of the ToDo task
     */
    public CreateToDoCommand(String description) {
        this.taskDescription = description;
    }

    @Override
    public String execute(Solace solace) {
        logExecution();
        ToDoTask newTask = new ToDoTask(this.taskDescription);
        String statusMsg = solace.getTaskList().addTask(newTask);
        displayStatusMessage(solace, statusMsg + solace.getTaskList().getSize());
        return statusMsg + solace.getTaskList().getSize();
    }
    /**
     * Displays the status message of the command execution
     *
     * @param solace The Solace instance to get the UI instance
     * @param message The status message to display
     */
    private void displayStatusMessage(Solace solace, String message) {
        Ui ui = solace.getUi();
        ui.printMessage(message);
    }
    @Override
    public void logExecution() {
        System.out.println("CreateToDo Command is executed");
    }
}
