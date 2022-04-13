package project.c195;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.c195.controller.addAppointmentController;
import project.c195.controller.loginMenuController;
import project.c195.controller.overviewMenuController;
import project.c195.helpers.JDBC;
import project.c195.helpers.contactsDataSQL;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/c195/loginMenu.fxml"));
            Parent root = loader.load();
            loginMenuController controller = loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}