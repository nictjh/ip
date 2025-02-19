package app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ui.Ui;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
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
    private Image botImg;
    private Image userImg;
    /**
     * Sets the Solace instance.
     */
    public void setSolace(Solace s) {
        this.solace = s;

        // Display welcome message at startup
        dialogContainer.getChildren().add(
                DialogBox.getBotDialog(Ui.getWelcomeMessage(), botImg)
        );
    }

    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        botImg = new Image(this.getClass().getResourceAsStream("/images/bot.png"));
        userImg = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
        dialogContainer.setPrefWidth(Region.USE_COMPUTED_SIZE);
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialogContainer.setFillWidth(true);

        userInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleUserInput();
            }
        });
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = solace.getResponse(input);

        // Create user and bot dialog boxes
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImg),
                DialogBox.getBotDialog(response, botImg)
        );

        userInput.clear();
    }
}
