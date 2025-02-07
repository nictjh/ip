package commands;
import app.Solace;
import task.EventTask;
import ui.Ui;

/**
 * Represents the command to create an event task
 *
 */
public class CreateEventCommand extends Command {

    private String description;
    private String from;
    private String to;

    /**
     * Creates a new CreateEventCommand object
     *
     * @param description The description of the event task
     * @param from The starting date and time of the event task (formatted as "dd/MM/yyyy HHmm") or String
     * @param to The ending date and time of the event task (formatted as "dd/MM/yyyy HHmm") or String
     */
    public CreateEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public String execute(Solace solace) {
        EventTask newEventTask = new EventTask(this.description, this.from, this.to); //Creates the event task
        String statusMsg = solace.getTaskList().addTask(newEventTask);
        Ui ui = solace.getUi();
        ui.printMessage(statusMsg + solace.getTaskList().getSize());
        return statusMsg + solace.getTaskList().getSize();
    }

    @Override
    public void logExecution() {
        System.out.println("Create Event Command is executed");
    }
}
