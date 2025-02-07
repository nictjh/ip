package app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ui.Ui;

public class MainWindow {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Solace solace;

    /**
     * Sets the Solace instance.
     */
    public void setSolace(Solace s) {
        this.solace = s;

        // Display welcome message at startup
        dialogContainer.getChildren().add(
                DialogBox.getBotDialog(Ui.getWelcomeMessage())
        );
    }

    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = solace.getResponse(input);

        // Create user and bot dialog boxes
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getBotDialog(response)
        );

        userInput.clear();
    }
}
