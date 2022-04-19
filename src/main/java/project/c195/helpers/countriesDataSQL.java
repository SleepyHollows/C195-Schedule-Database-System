package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class countriesDataSQL {
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

    public static ObservableList getCountryName() {
        ObservableList list = FXCollections.observableArrayList();
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

}
