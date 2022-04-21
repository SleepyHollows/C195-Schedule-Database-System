package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.c195.Main;

import java.io.IOException;

/**
 * Opens the login screen and initiates the whole project
 */
public abstract class sceneController {
    public static void switchScreen(ActionEvent event, String location) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(location));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        Stage overviewStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        overviewStage.setScene(scene);
        overviewStage.show();
    }
}
