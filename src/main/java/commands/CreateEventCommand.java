package commands;
import app.Solace;
import task.EventTask;
import ui.Ui;

public class CreateEventCommand extends Command {

    private String description;
    private String from;
    private String to;

    public CreateEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(Solace solace) {
        EventTask newEventTask = new EventTask(this.description, this.from, this.to); //Creates the event task
        String statusMsg = solace.getTaskList().addTask(newEventTask);
        Ui ui = solace.getUi();
        ui.printMessage(statusMsg + solace.getTaskList().getSize());
    }

    @Override
    public void logExecution() {
        System.out.println("Create Event Command is executed");
    }
}
