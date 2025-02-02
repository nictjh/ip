package commands;
import app.Solace;
import task.ToDoTask;
import ui.Ui;

public class CreateToDoCommand extends Command {

    private String description;

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
