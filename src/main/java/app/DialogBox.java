package app;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Represents a dialog box consisting of an ImageView and Label to simulate a chat bubble.
 */
public class DialogBox extends HBox {

    private ImageView displayPic;
    private TextFlow textFlow;
    /**
     * Creates a dialog box with the given message and image.
     *
     * @param message The message to be displayed in the dialog box.
     * @param i The image to be displayed in the dialog box.
     * @param isUser A boolean flag to determine if the dialog box is for the user or the bot.
     */
    public DialogBox(String message, Image i, boolean isUser) {
        Text trial = new Text(message);
        this.displayPic = new ImageView(i);

        this.textFlow = new TextFlow(trial);
        trial.setStyle("-fx-fill: white;");
        this.textFlow.setMaxWidth(500);
        this.textFlow.setStyle("-fx-padding: 10px;");
        // Set image size
        displayPic.setFitHeight(40);
        displayPic.setFitWidth(40);

        this.setSpacing(10);
        this.getStyleClass().add("dialog-box");

        if (isUser) {
            this.getStyleClass().add("user-message");
            this.setAlignment(Pos.TOP_RIGHT);
            this.getChildren().addAll(textFlow, displayPic); // User: Text first, then image
        } else {
            this.getStyleClass().add("bot-message");
            this.setAlignment(Pos.TOP_LEFT);
            this.getChildren().addAll(displayPic, textFlow); // Bot: Image first, then text
        }
    }


    public static DialogBox getUserDialog(String message, Image i) {
        return new DialogBox(message, i, true);
    }

    public static DialogBox getBotDialog(String message, Image i) {
        return new DialogBox(message, i, false);
    }
}
