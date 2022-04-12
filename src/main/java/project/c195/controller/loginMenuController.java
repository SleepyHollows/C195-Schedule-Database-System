package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.c195.Main;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginMenuController implements Initializable {

    @FXML
    private TextField usernameTextField;
    @FXML
    private Text usernameTitle;
    @FXML
    private Text passwordTitle;
    @FXML
    private Button loginBtn;
    @FXML
    private Text loginTitle;
    @FXML
    private TextField passwordTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("es")) {
                usernameTitle.setText(rb.getString("Username"));
                passwordTitle.setText(rb.getString("Password"));
                loginTitle.setText(rb.getString("loginTitle"));
                loginBtn.setText(rb.getString("loginBtn"));
            }
        }
        catch(Exception e) {
            System.out.print("Language Error");
        }
    }

    //Need to add login credential check!
    public void openOverviewMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("overviewMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);
        Stage overviewStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        overviewStage.setScene(scene);
        overviewStage.show();
    }
}
