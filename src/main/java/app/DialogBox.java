package app;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView and Label to simulate a chat bubble.
 */
public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPic;

    /**
     * Creates a dialog box with the given message and image.
     *
     * @param message
     * @param i
     * @param isUser
     */
    public DialogBox(String message, Image i, boolean isUser) {
        this.text = new Label(message);
        this.displayPic = new ImageView(i);

        // Set image size
        displayPic.setFitHeight(40);
        displayPic.setFitWidth(40);

        text.setWrapText(true);
        text.setMaxWidth(250);

        this.setSpacing(10);
        this.getStyleClass().add("dialog-box");

        if (isUser) {
            this.getStyleClass().add("user-message");
            this.setAlignment(Pos.TOP_RIGHT);
            this.getChildren().addAll(text, displayPic); // User: Text first, then image
        } else {
            this.getStyleClass().add("bot-message");
            this.setAlignment(Pos.TOP_LEFT);
            this.getChildren().addAll(displayPic, text); // Bot: Image first, then text
        }
    }


    public static DialogBox getUserDialog(String message, Image i) {
        return new DialogBox(message, i, true);
    }

    public static DialogBox getBotDialog(String message, Image i) {
        return new DialogBox(message, i, false);
    }
}
