package commands;
import app.Solace;

public class ListCommand extends Command {
    @Override
    public void execute(Solace solace) {
        System.out.println(solace.getTaskList().printTasks() + solace.getDividerLine());
//        logExecution();
    }

    @Override
    public void logExecution() {
        System.out.println("List Command is executed");
    }
}
