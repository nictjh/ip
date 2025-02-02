package commands;
import app.Solace;

import exceptions.UnknownCommandException;

/**
 * Represents an unknown command
 *
 */
public class UnknownCommand extends Command {

    @Override
    public void execute(Solace solace) throws UnknownCommandException {
        throw new UnknownCommandException();
    }

    @Override
    public void logExecution() {
        System.out.println("Unknown Command is executed");
    }
}
