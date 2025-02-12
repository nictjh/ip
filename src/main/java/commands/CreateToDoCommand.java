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
        ToDoTask newTask = new ToDoTask(this.taskDescription);
        String statusMsg = solace.getTaskList().addTask(newTask);
        Ui ui = solace.getUi();
        ui.printMessage(statusMsg + solace.getTaskList().getSize());
        return statusMsg + solace.getTaskList().getSize();
    }

    @Override
    public void logExecution() {
        System.out.println("CreateToDo Command is executed");
    }
}
