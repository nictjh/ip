package commands;
import app.Solace;
import ui.Ui;
import task.ToDoTask;

/**
 * Represents the command to create a new ToDo task
 *
 */
public class CreateToDoCommand extends Command {

    private String description;

    /**
     * Creates a new CreateToDoCommand object
     *
     * @param description Description of the ToDo task
     */
    public CreateToDoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(Solace solace) {
        ToDoTask newTask = new ToDoTask(description);
        String statusMsg = solace.getTaskList().addTask(newTask);
        Ui ui = solace.getUi();
        ui.printMessage(statusMsg + solace.getTaskList().getSize());
    }

    @Override
    public void logExecution() {
        System.out.println("CreateToDo Command is executed");
    }
}
