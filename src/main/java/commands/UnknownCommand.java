package commands;
import app.Solace;
import exceptions.UnknownCommandException;

public class UnknownCommand extends Command {

    public UnknownCommand() {
    }

    @Override
    public void execute(Solace solace) throws UnknownCommandException {
        throw new UnknownCommandException();
    }

    @Override
    public void logExecution() {
        System.out.println("Unknown Command is executed");
    }
}
