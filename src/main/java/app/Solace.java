package app;

import java.io.File;

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
import tasklist.TaskList;
import ui.Ui;

/**
 * Represents the main application logic for Solace
 * Executes the corresponding commands from user input
 */
public class Solace extends Application {

    private static boolean isAlive;
    private final TaskList taskList;
    private final String filePath = "bin/storage";
    private Ui userInterface;
    private final Storage storage;

    /**
     * Creates a new Solace application
     * Loads stored tasks from file
     */
    public Solace() {
        isAlive = true;
        initialiseUI();
        initializeStorage();
        this.storage = new Storage(filePath);
        this.taskList = initializeTaskList();
    }
    /**
     * Initialises the user interface
     */
    private void initialiseUI() {
        this.userInterface = new Ui();
    }
    /**
     * Checks and creates the storage directory if it doesn't exist.
     */
    private void initializeStorage() {
        File storageDir = new File(filePath);
        if (!storageDir.exists()) {
            boolean isCreated = storageDir.mkdirs();
            printStorageStatus(isCreated);
        }
    }
    /**
     * Prints the status of storage directory creation.
     *
     * @param isCreated Boolean indicating if the directory was created.
     */
    private void printStorageStatus(boolean isCreated) {
        if (!isCreated) {
            this.userInterface.printMessage("An error occurred while creating the storage directory");
        } else {
            this.userInterface.printMessage("Storage directory created successfully");
        }
    }
    /**
     * Loads the task list from storage.
     *
     * @return The loaded task list.
     */
    private TaskList initializeTaskList() {
        return storage.load();
    }

    public Ui getUi() {
        return userInterface;
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
            setupStage(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Sets up the main application stage
     *
     * @param stage The primary stage
     * @throws Exception If FXML loading fails
     */
    private void setupStage(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Solace Chatbot");

        stage.setScene(scene);
        stage.show();

        // Connect Solace instance to MainWindow
        MainWindow controller = fxmlLoader.getController();
        controller.setSolace(this);
    }

    /**
     * Processes user input and retuns the response
     * @param input The user input
     * @return The bot's response
     */
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
}
