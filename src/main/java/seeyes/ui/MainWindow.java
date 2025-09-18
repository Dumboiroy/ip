package seeyes.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seeyes.Seeyes;

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
            this.getClass().getResourceAsStream("/images/User.jpeg"));
    private Image seeyesImage = new Image(
            this.getClass().getResourceAsStream("/images/Seeyes.jpeg"));

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
        assert input instanceof String : "input should be a string";
        assert response instanceof String : "response should be a string";
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSeeyesDialog(response, seeyesImage));
        userInput.clear();
        checkExit(input);
    }

    private void checkExit(String input) {
        if (input.trim().equals("bye")) {
            seeyes.exit();
        }
    }
}
