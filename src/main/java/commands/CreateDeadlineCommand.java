package commands;
import app.Solace;
import task.DeadlineTask;
import taskList.TaskList;
import ui.Ui;

public class CreateDeadlineCommand extends Command {
    private String description;
    private String deadline;

    public CreateDeadlineCommand(String description, String deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    @Override
    public void execute(Solace solace) {
        DeadlineTask newDeadlineTask = new DeadlineTask(description, deadline); //Creates the deadline task
        String statusMsg = solace.getTaskList().addTask(newDeadlineTask);
        Ui ui = solace.getUi();
        ui.printMessage(statusMsg + solace.getTaskList().getSize());
    }

    public String execute(TaskList tasklist) {
        DeadlineTask newDeadlineTask = new DeadlineTask(description, deadline); //Creates the deadline task
        return tasklist.addTask(newDeadlineTask);
    }

    @Override
    public void logExecution() {
        System.out.println("Create Deadline Command is executed");
    }
}
