package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.c195.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class reportsMenuController implements Initializable {

    public void openOverviewMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("overviewMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        Stage overviewStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        overviewStage.setScene(scene);
        overviewStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
