package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.c195.model.customerData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class customerDataSQL {
    public static ObservableList<customerData> getCustomerData() {
        ObservableList<customerData> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                list.add(
                        new customerData(rs.getInt("Customer_ID"),
                                rs.getString("Customer_Name"), rs.getString("Address"),
                                rs.getString("Postal_Code"), rs.getString("Phone"),
                                rs.getInt("Division_ID")));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
