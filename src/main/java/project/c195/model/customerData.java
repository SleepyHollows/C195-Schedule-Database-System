package project.c195.model;

public class customerData {

    int id, divID;
    String name, address, postalCode, phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDivID() {
        return divID;
    }

    public void setDivID(int divID) {
        this.divID = divID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public customerData(int id, String name, String address, String postalCode, String phone, int divID) {
        this.id = id;
        this.divID = divID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }
}