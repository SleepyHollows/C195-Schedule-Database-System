package project.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import project.c195.helpers.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class addCustomerController implements Initializable {

    @FXML
    private TextField addressTextBox;

    @FXML
    private ComboBox<String> countryDropDown;

    @FXML
    private TextField nameTextBox;

    @FXML
    private TextField phoneTextBox;

    @FXML
    private TextField postalTextBox;

    @FXML
    private ComboBox<String> stateDropDown;

    //All objects and variables used in the class
    Alert alert;
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
    String name, address, postal, phone, createdBy;
    int customerID, divisionID;

    /**
     * Sets the dropdown menus for state and country with all the data from the database.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryDropDown.setItems(countriesDataSQL.getCountryName());
            stateDropDown.setItems(divisionsDataSQL.getDivisionNameByCountryID(countriesDataSQL.getCountryIDByName(countryDropDown.getSelectionModel().getSelectedItem())));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This assigns the variables with the corresponding values in each textbox/dropdown.
     * Checks for empty values before inserting in initiated.
     * Confirms with the user that they want to create the customer
     * Calls the customerInsertSQL from the customerDataSQL class.
     * @param event opens the overviewMenu after the insert is completed
     */
    @FXML
    void addCustomer(ActionEvent event){
        try {
            name = nameTextBox.getText();
            address = addressTextBox.getText();
            postal = postalTextBox.getText();
            phone = phoneTextBox.getText();
            createdBy = usersDataSQL.getCurrentUsers().getUsername();
            customerID = customerDataSQL.getCustomerID();
            divisionID = divisionsDataSQL.getDivisionIDByName(String.valueOf(stateDropDown.getSelectionModel().getSelectedItem()));

            if(name.isBlank() || address.isBlank() || postal.isBlank() || phone.isBlank()
                    || countryDropDown.getValue() == null || stateDropDown.getValue() == null) {
                alert = new Alert(Alert.AlertType.ERROR, "All boxes must be filled");
                alert.showAndWait();
            }
            else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Create customer");
                alert.setHeaderText("Are you sure you want to create this customer?");
                alert.setContentText("Create?");
                alert.getButtonTypes().setAll(yes, no);
                alert.showAndWait().ifPresent(button -> {
                    if(button == yes) {
                        try {
                            customerDataSQL.customerInsertSQL(customerID, name, address, postal, phone, divisionID, createdBy, createdBy);
                            sceneController.switchScreen(event, "/project/c195/overviewMenu.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(button == no) {
                        alert.close();
                    }
                });
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This confirms with user that they want to return to overviewMenu
     * @param event opens overviewMenu
     */
    public void openOverviewMenu(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel customer creation");
        alert.setHeaderText("Are you sure you want to cancel, all data enter will be lost");
        alert.setContentText("Cancel?");
        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait().ifPresent(type -> {
            if(type == yes) {
                try {
                    sceneController.switchScreen(event, "overviewMenu.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(type == no) {
                alert.close();
            }
        });
    }

    /**
     * This will set the dropdown for states to the appropriate list of states based on country picked
     */
    public void setDivisions() {
        stateDropDown.setItems(divisionsDataSQL.getDivisionNameByCountryID(countriesDataSQL.getCountryIDByName(countryDropDown.getSelectionModel().getSelectedItem())));
    }
}
