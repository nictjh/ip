package app;

import java.util.Scanner;

import commands.Command;
import commands.CommandParser;
import exceptions.EmptyTaskListException;
import exceptions.InvalidTaskNumberException;
import exceptions.MissingArgumentException;
import exceptions.RepeatedTaskUpdateException;
import exceptions.UnknownCommandException;
import storage.Storage;
import taskList.TaskList;
import ui.Ui;

public class Solace {

    private static boolean isAlive;
    private final TaskList taskList;
    private final String FILE_PATH = "bin/storage";
    private static Ui UI;
    private final Storage STORAGE;

    public Solace() {
        UI = new Ui();
        isAlive = true;
        this.STORAGE = new Storage(FILE_PATH);
        this.taskList = STORAGE.load();
    }

    public Ui getUi() {
        return UI;
    }

    public Storage getStorage() {
        return this.STORAGE;
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

                String userInput = UI.readCommand(input);
                Command command = CommandParser.parse(userInput);
                command.execute(solace);

            } catch (MissingArgumentException | UnknownCommandException | InvalidTaskNumberException
                     | RepeatedTaskUpdateException | EmptyTaskListException e) {
                UI.printMessage(e.getMessage() + "\n");
            } catch (Exception e) {
                UI.printMessage("An error occurred: " + e.getMessage());
            }
        }
        input.close();
    }
}
