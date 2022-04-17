package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class countriesDataSQL {
    public static ObservableList getCountryID() {
        ObservableList list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Country_ID FROM countries");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("Country_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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

    public static String getCountryNameByDivID(String divID) {
        String countryName = "";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Country FROM countries WHERE Div");
            ResultSet rs = ps.executeQuery();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return countryName;
    }
}
