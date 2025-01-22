package commands;
import app.Solace;

public abstract class Command {

    public abstract void execute(Solace solace);
    void logExecution() {}
}
