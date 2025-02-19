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
        this.textFlow = initializeMessage(message);
        this.displayPic = initializeImage(i);
        this.setSpacing(10);
        this.getStyleClass().add("dialog-box");
        setAlignmentAndOrder(isUser);
    }

    /**
     * Initializes the TextFlow for the message.
     *
     * @param message The message to be displayed.
     * @return Configured TextFlow for the dialog box.
     */
    private TextFlow initializeMessage(String message) {
        Text text = new Text(message);
        text.setStyle("-fx-fill: white;");

        TextFlow flow = new TextFlow(text);
        flow.setMaxWidth(500);
        flow.setStyle("-fx-padding: 10px;");

        return flow;
    }

    /**
     * Initializes the ImageView for the display picture.
     *
     * @param i The image to be displayed.
     * @return Configured ImageView for the dialog box.
     */
    private ImageView initializeImage(Image i) {
        ImageView imageView = new ImageView(i);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        return imageView;
    }

    /**
     * Sets alignment and order of children based on whether it's a user or bot message.
     *
     * @param isUser Boolean flag indicating if the dialog box is for the user or the bot.
     */
    private void setAlignmentAndOrder(boolean isUser) {
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
    /**
     * Factory method to create a user dialog box
     *
     * @param message The message to be displayed.
     * @param i The image to be displayed.
     * @return DialogBox object for the user.
     */
    public static DialogBox getUserDialog(String message, Image i) {
        return new DialogBox(message, i, true);
    }
    /**
     * Factory method to create a bot dialog box
     *
     * @param message The message to be displayed.
     * @param i The image to be displayed.
     * @return DialogBox object for the bot.
     */
    public static DialogBox getBotDialog(String message, Image i) {
        return new DialogBox(message, i, false);
    }
}
