package app;
import commands.CommandParser;
import commands.Command;
import ui.Ui;
import storage.Storage;
import taskList.TaskList;
import exceptions.EmptyTaskListException;
import exceptions.InvalidTaskNumberException;
import exceptions.MissingArgumentException;
import exceptions.UnknownCommandException;
import exceptions.RepeatedTaskUpdateException;
import java.util.Scanner;

public class Solace {

    private static boolean isAlive;
    private final TaskList taskList;
    private final String FILE_PATH = "bin/storage";
    private static Ui ui;
    private Storage storage;

    public Solace() {
        ui = new Ui();
        isAlive = true;
        this.storage = new Storage(FILE_PATH);
        this.taskList = storage.load();
    }

    public Ui getUi() {
        return ui;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setAlive() {
        // Setter function
        isAlive = !isAlive;
    }

    public TaskList getTaskList() {
        // Getter function
        return taskList;
    }

    public static void main(String[] args) {
        Solace solace = new Solace();
        Scanner input = new Scanner(System.in);

        while (isAlive) {

            try {

                if (!input.hasNextLine()) {
                    break; //Fixing bug when there is no line to read but below call nextLine
                }

                String userInput = ui.readCommand(input);
                Command command = CommandParser.parse(userInput);
                command.execute(solace);

            } catch (MissingArgumentException | UnknownCommandException | InvalidTaskNumberException
                     | RepeatedTaskUpdateException | EmptyTaskListException e) {
                ui.printMessage(e.getMessage() + "\n");
            } catch (Exception e) {
                ui.printMessage("An error occurred: " + e.getMessage());
            }
        }
        input.close();
    }
}
