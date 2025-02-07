package app;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {

    private Label text;

    public DialogBox(String message) {
        text = new Label(message);
        this.getChildren().add(text);
    }

    public static DialogBox getUserDialog(String message) {
        DialogBox db = new DialogBox(message);
        db.setAlignment(Pos.TOP_RIGHT);
        return db;
    }

    public static DialogBox getBotDialog(String message) {
        DialogBox db = new DialogBox(message);
        db.setAlignment(Pos.TOP_LEFT);
        return db;
    }
}
