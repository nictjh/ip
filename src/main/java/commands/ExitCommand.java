package commands;
import app.Solace;

public class ExitCommand extends Command {
    @Override
    public void execute(Solace solace) {
        solace.setAlive(false);
        System.out.println("Bye. Hope to see you again soon!\n" + solace.getDividerLine());
//        logExecution();
    }

    @Override
    public void logExecution() {
        System.out.println("Exit Command is executed");
    }
}
