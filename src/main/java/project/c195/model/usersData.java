package project.c195.model;

public class usersData {
    private final String username;
    private final int userID;

    public usersData(String username, int userID) {
        this.username = username;
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }
}

