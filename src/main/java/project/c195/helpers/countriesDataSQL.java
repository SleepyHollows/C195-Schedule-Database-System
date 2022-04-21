package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class countriesDataSQL {

    /**
     * Gets a specific country ID using only a provided name
     * @param countryName used to find the country ID
     * @return the country ID found
     */
    public static int getCountryIDByName(String countryName) {
        int countryID = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Country_ID FROM countries WHERE Country = '" + countryName + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                    countryID = rs.getInt("Country_ID");
                    return countryID;
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryID;
    }

    /**
     * Creates a list of all country names in the database
     * @return a list of names
     */
    public static ObservableList <String> getCountryName() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Country FROM countries");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("Country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Gets a specific country name using only a provided ID
     * @param countryID used to find the country ID
     * @return the country name found
     */
    public static String getCountryNameByID(int countryID) {
        String countryName = "";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Country FROM countries WHERE Country_ID = '" + countryID + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                countryName = rs.getString("Country");
                return countryName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryName;
    }
}
