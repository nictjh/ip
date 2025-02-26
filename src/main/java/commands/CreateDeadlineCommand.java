package commands;
import app.Solace;
import task.DeadlineTask;
import tasklist.TaskList;
import ui.Ui;

/**
 * Represents the command to create a deadline task
 * and add it to the task list
 */
public class CreateDeadlineCommand extends Command {

    private String desc;
    private String deadline;

    /**
     * Creates a new CreateDeadlineCommand object
     *
     * @param description Description of the deadline task
     * @param dueDate Deadline of the task
     */
    public CreateDeadlineCommand(String description, String dueDate) {
        this.desc = description;
        this.deadline = dueDate;
    }
    @Override
    public String execute(Solace solace) {
        logExecution();
        DeadlineTask newDeadlineTask = new DeadlineTask(this.desc, this.deadline); //Creates the deadline task
        String statusMsg = solace.getTaskList().addTask(newDeadlineTask);
        displayStatusMessage(solace, statusMsg + solace.getTaskList().getSize());
        return statusMsg + solace.getTaskList().getSize();
    }

    /**
     * Executes the command to create a deadline task
     * and add it to the task list
     * Overloaded method for testing purposes
     *
     * @param tasklist Task list to add the deadline task to
     * @return Status message of the execution
     */
    public String execute(TaskList tasklist) {
        DeadlineTask newDeadlineTask = new DeadlineTask(this.desc, this.deadline); //Creates the deadline task
        return tasklist.addTask(newDeadlineTask);
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
        System.out.println("Create Deadline Command is executed");
    }
}
