package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class contactsDataSQL {
    public static ObservableList getContactsID() {
        ObservableList list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Contact_ID FROM contacts");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("Contact_ID"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ObservableList getContactsName() {
        ObservableList list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Contact_Name FROM contacts");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("Contact_Name"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList getContactsEmail() {
        ObservableList list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Email FROM contacts");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("Email"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static int getContactsIDByName(String contactName) {
        int contactID = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM contacts WHERE Contact_Name = '" + contactName + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (contactName.equals(rs.getString("Contact_Name"))) {
                    contactID = rs.getInt("Contact_ID");
                    return contactID;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "no contact found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactID;
    }
    public static String getContactsNameByID(int contactID) {
        String contactName = "";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Contact_Name FROM contacts WHERE Contact_ID = '" + contactID + "'");
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                contactName = rs.getString("Contact_Name");
            }
            return contactName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactName;
    }
}
