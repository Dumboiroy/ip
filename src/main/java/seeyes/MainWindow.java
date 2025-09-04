package seeyes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Seeyes seeyes;

    private Image userImage = new Image(
            this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image seeyesImage = new Image(
            this.getClass().getResourceAsStream("/images/DaSeeyes.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Seeyes instance */
    public void setSeeyes(Seeyes s) {
        seeyes = s;
        // Show welcome message when Seeyes is injected
        String welcomeMessage = seeyes.getUi().getWelcomeMessage();
        dialogContainer.getChildren()
                .add(DialogBox.getSeeyesDialog(welcomeMessage, seeyesImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Seeyes's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = seeyes.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSeeyesDialog(response, seeyesImage));
        userInput.clear();
    }
}
