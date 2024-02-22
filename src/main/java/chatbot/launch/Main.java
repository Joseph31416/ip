package chatbot.launch;

import java.io.IOException;

import chatbot.cortana.Cortana;
import chatbot.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Cortana using FXML.
 */
public class Main extends Application {

    private Cortana cortana = new Cortana(".");

    @Override
    public void start(Stage stage) {
        try {
            String fxmlPath = java.nio.file.Paths.get(".", "view", "MainWindow.fxml").toString();
            fxmlPath = fxmlPath.substring(1);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlPath));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle(Cortana.NAME);
            fxmlLoader.<MainWindow>getController().setChatbot(cortana);
            fxmlLoader.<MainWindow>getController().greetUser();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
