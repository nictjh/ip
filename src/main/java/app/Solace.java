package app;

import java.util.Scanner;

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

/**
 * Represents the main application logic for Solace
 * Executes the corresponding commands from user input
 */
public class Solace {

    private static boolean isAlive;
    private final TaskList TASKLIST;
    private final String FILE_PATH = "bin/storage";
    private static Ui UI;
    private final Storage STORAGE;

    /**
     * Creates a new Solace application
     * Loads stored tasks from file
     */
    public Solace() {
        UI = new Ui();
        isAlive = true;
        this.STORAGE = new Storage(FILE_PATH);
        this.TASKLIST = STORAGE.load();
    }

    public Ui getUi() {
        // Getter
        return UI;
    }

    public Storage getStorage() {
        // Getter function
        return this.STORAGE;
    }

    public void setAlive() {
        // Setter function
        isAlive = !isAlive;
    }

    public TaskList getTaskList() {
        // Getter function
        return this.TASKLIST;
    }

    /**
     * Main entry to the application
     * Handles user input and executes commands until program exits
     * @param args Not used
     */
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
