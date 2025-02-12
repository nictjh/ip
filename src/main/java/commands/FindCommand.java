package commands;
import app.Solace;
import taskList.TaskList;
import ui.Ui;

/**
 * Represents the command find tasks by keyword.
 *
 */
public class FindCommand extends Command {

    private String keyword;

    public FindCommand(String target) {
        this.keyword = target;
    }

    @Override
    public String execute(Solace solace) {
        Ui ui = solace.getUi();
        TaskList taskList = solace.getTaskList(); // get TaskList from Solace
        String statusMsg = taskList.findTasksByKeyword(this.keyword);
        ui.printMessage(statusMsg);
        return statusMsg;
    }
}
