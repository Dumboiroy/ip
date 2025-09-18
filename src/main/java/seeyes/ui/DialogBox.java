package seeyes.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of a label and an image. Used for displaying user and Seeyes messages in the chat
 * UI.
 */
public class DialogBox extends HBox {

    /**
     * The label containing the dialog text.
     */
    @FXML
    private Label dialog;
    /**
     * The image view displaying the user's or Seeyes's avatar.
     */
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the specified text and image. Loads the FXML layout and sets the dialog text and
     * image.
     *
     * @param text
     *            the text to display in the dialog
     * @param img
     *            the image to display as the avatar
     */
    public DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the image is on the left and text on the right. Used for Seeyes messages.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections
                .observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);

    }

    /**
     * Returns a DialogBox for user messages.
     *
     * @param text
     *            the user's message
     * @param img
     *            the user's avatar image
     * @return a DialogBox representing the user's message
     */
    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        // Style for user messages - darker green
        db.dialog.setStyle(
                "-fx-background-color: #A5D6A7; -fx-padding: 12; -fx-border-radius: 18;"
                        + "-fx-background-radius: 18; -fx-text-fill: #1B5E20; -fx-font-size: 14;");
        return db;
    }

    /**
     * Returns a DialogBox for Seeyes messages, with the image on the left.
     *
     * @param text
     *            the Seeyes message
     * @param img
     *            the Seeyes avatar image
     * @return a DialogBox representing the Seeyes message
     */
    public static DialogBox getSeeyesDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        // Style for Seeyes messages - lighter green
        db.dialog.setStyle(
                "-fx-background-color: #C8E6C9; -fx-padding: 12; -fx-border-radius: 18;"
                        + " -fx-background-radius: 18; -fx-text-fill: #2E7D32; -fx-font-size: 14;");
        db.flip();
        return db;
    }
}
