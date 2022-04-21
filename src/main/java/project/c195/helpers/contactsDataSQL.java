package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gets a list of only contact names from the database
 */
public class contactsDataSQL {
    public static ObservableList<String> getContactsName() {
        ObservableList <String> list = FXCollections.observableArrayList();
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

    /**
     * Finds a specific contacts ID using only the name provided
     * @param contactName the name used to find the Contact ID
     * @return contact ID from database
     */
    public static int getContactsIDByName(String contactName) {
        int contactID = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM contacts WHERE Contact_Name = '" + contactName + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (contactName.equals(rs.getString("Contact_Name"))) {
                    contactID = rs.getInt("Contact_ID");
                    return contactID;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactID;
    }

    /**
     * Finds a specific contacts name using only the ID provided
     * @param contactID used to find the contact name
     * @return contact Name from the database
     */
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
