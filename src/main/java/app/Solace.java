package app;

import java.util.Scanner;

import commands.Command;
import commands.CommandParser;
import exceptions.EmptyTaskListException;
import exceptions.InvalidTaskNumberException;
import exceptions.MissingArgumentException;
import exceptions.RepeatedTaskUpdateException;
import exceptions.UnknownCommandException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import storage.Storage;
import taskList.TaskList;
import ui.Ui;

/**
 * Represents the main application logic for Solace
 * Executes the corresponding commands from user input
 */
public class Solace extends Application {

    private static boolean isAlive;
    private final TaskList taskList;
    private final String filePath = "bin/storage";
    private static Ui UI;
    private final Storage storage;

    /**
     * Creates a new Solace application
     * Loads stored tasks from file
     */
    public Solace() {
        UI = new Ui();
        isAlive = true;
        this.storage = new Storage(filePath);
        this.taskList = storage.load();
    }

    public Ui getUi() {
        return UI;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public void setAlive() {
        // Setter function
        isAlive = !isAlive;
    }

    public TaskList getTaskList() {
        // Getter function
        return this.taskList;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Solace Chatbot");
            stage.setScene(scene);
            stage.show();

            // Connect Solace instance to MainWindow
            MainWindow controller = fxmlLoader.getController();
            controller.setSolace(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResponse(String input) {
        try {
            Command command = CommandParser.parse(input);
            return command.execute(this); // I need to change the outputs for all Commands
        } catch (MissingArgumentException | UnknownCommandException | InvalidTaskNumberException
                 | RepeatedTaskUpdateException | EmptyTaskListException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }

    /**
     * Main entry to the application
     * Handles user input and executes commands until program exits
     * @param args Not used
     */
//    public static void main(String[] args) {
//        Solace solace = new Solace();
//        Scanner input = new Scanner(System.in);
//
//        while (isAlive) {
//
//            try {
//
//                if (!input.hasNextLine()) {
//                    break; //Fixing bug when there is no line to read but below call nextLine
//                }
//
//                String userInput = UI.readCommand(input);
//                Command command = CommandParser.parse(userInput);
//                command.execute(solace);
//
//            } catch (MissingArgumentException | UnknownCommandException | InvalidTaskNumberException
//                     | RepeatedTaskUpdateException | EmptyTaskListException e) {
//                UI.printMessage(e.getMessage() + "\n");
//            } catch (Exception e) {
//                UI.printMessage("An error occurred: " + e.getMessage());
//            }
//        }
//        input.close();
//    }
}
