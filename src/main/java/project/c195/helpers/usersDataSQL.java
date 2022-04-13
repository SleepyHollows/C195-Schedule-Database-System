package project.c195.helpers;

import project.c195.model.usersData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class usersDataSQL {
    private static usersData currentUsers;

    public static boolean loginAttempt(String userName, String password) {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement("SELECT * FROM users WHERE User_Name='" + userName + "' AND Password='" + password + "'");
            ResultSet rs = ps.executeQuery();

            if(rs.next())  {
                currentUsers = new usersData(rs.getString("User_Name"), rs.getInt("User_ID"));
                rs.close();
                return true;
            }
            else {
                rs.close();
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static usersData getCurrentUsers() {
        return currentUsers;
    }
}
