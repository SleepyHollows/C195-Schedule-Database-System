package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import project.c195.helpers.countriesDataSQL;
import project.c195.helpers.customerDataSQL;
import project.c195.helpers.divisionsDataSQL;
import project.c195.model.customerData;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class addCustomerController implements Initializable {

    @FXML
    private TextField addressTextBox;

    @FXML
    private ComboBox<customerData> countryDropDown;

    @FXML
    private TextField nameTextBox;

    @FXML
    private TextField phoneTextBox;

    @FXML
    private TextField postalTextBox;

    @FXML
    private ComboBox<customerData> stateDropDown;

    Random rnd = new Random();
    String name;
    String address;
    String postal;
    String phone;
    int customerID;
    int divisionID;

    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        name = nameTextBox.getText();
        phone = phoneTextBox.getText();
        address = addressTextBox.getText();
        postal = postalTextBox.getText();
        customerID = customerDataSQL.getCustomerID();
        divisionID = divisionsDataSQL.getDivisionIDByName(String.valueOf(stateDropDown.getSelectionModel().getSelectedItem()));

        customerDataSQL.customerInsertSQL(customerID, divisionID, name, address, phone, postal);
        sceneController.switchScreen(event, "/project/c195/overviewMenu.fxml");
    }


    public void openOverviewMenu(ActionEvent event) throws IOException {
        sceneController.switchScreen(event, "/project/c195/overviewMenu.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            stateDropDown.setItems(divisionsDataSQL.getDivisionName());
            countryDropDown.setItems(countriesDataSQL.getCountryName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
