package seeyes;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * TODO: javadocs
 */
public class Main extends Application {

    private Seeyes seeyes = new Seeyes("./data/data.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setSeeyes(seeyes); // inject the Seeyes instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
