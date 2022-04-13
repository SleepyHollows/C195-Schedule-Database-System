package project.c195.helpers;

import javafx.scene.control.Alert;

import java.util.Locale;
import java.util.ResourceBundle;

public class errorMessages {
    public static void translatedError(String message) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("es") || Locale.getDefault().getLanguage().equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString(message));
                alert.showAndWait();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}

