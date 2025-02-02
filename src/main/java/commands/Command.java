package commands;
import app.Solace;

/**
 * Represents an abstract command that can be executed by Solace
 * All commands should extend this class and implement the 'execute' method
 */
public abstract class Command {

    /**
     * Executes the command using the provided Solace interface
     *
     * @param solace The Solace instance to execute the command with
     * @throws Exception If an error occurs during the execution
     */
    public abstract void execute(Solace solace) throws Exception;
    void logExecution() {}
}
