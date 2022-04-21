package project.c195.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class divisionsDataSQL {

    /**
     * Creates a list of all first level divisions names in the database
     * @return list of all division names
     */
    public static ObservableList<String> getDivisionName() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Division FROM first_level_divisions");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("Division"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Gets a specific division ID using only a provided name.
     * @param divisionName name used to find the ID.
     * @return A division ID.
     */
    public static int getDivisionIDByName(String divisionName) {
        int divisionID = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM first_level_divisions WHERE Division = '" + divisionName + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (divisionName.equals(rs.getString("Division"))) {
                    divisionID = rs.getInt("Division_ID");
                    return divisionID;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionID;
    }

    /**
     * Gets a specific division name using only a provided ID.
     * @param divisionID ID used to find the Name.
     * @return A division Name.
     */
    public static String getDivisionNameByID(int divisionID) {
        String divisionName = "";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Division FROM first_level_divisions WHERE Division_ID = '" + divisionID + "'");
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                divisionName = rs.getString("Division");
            }
            return divisionName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionName;
    }

    /**
     * Creates a list of all divisions names in a specific country using only the country ID provided
     * @param countryID the country ID used to find all names
     * @return a list of division names
     */
    public static ObservableList<String> getDivisionNameByCountryID(int countryID) {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Division FROM first_level_divisions WHERE Country_ID = '" + countryID + "'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("Division"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Gets a specific divisions country ID using only the name provided
     * @param divisionName The name used to find the Country ID
     * @return a country ID
     */
    public static int getDivisionCountryIDByName(String divisionName) {
        int countryID = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT Country_ID FROM first_level_divisions WHERE Division = '" + divisionName + "'");
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
}
