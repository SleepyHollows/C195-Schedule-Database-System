package project.c195.model;

public class countriesData {
    int countryID;
    String countryName;

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public countriesData(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }


}
