package seeyes;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seeyes.ui.MainWindow;

/**
 * The entry point for the Seeyes application. This class launches the JavaFX UI and injects the Seeyes instance into
 * the main window.
 */
public class Main extends Application {

    /**
     * The Seeyes instance used by the application.
     */
    private Seeyes seeyes = new Seeyes("./data/data.txt");

    /**
     * Starts the JavaFX application and sets up the main window.
     *
     * @param stage
     *            the primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMinWidth(300);
            stage.setMinHeight(400);
            fxmlLoader.<MainWindow>getController().setSeeyes(seeyes); // inject the Seeyes instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
