package com.example.carpoolbuddy;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class User {

    private String userID;
    private String name;
    private String email;
    private String userType;
    private double priceMultiplier;
    private ArrayList<String> ownedVehicles;

    public User(String userID, String name, String email, String userType, double priceMultiplier,
                ArrayList<String> ownedVehicles)
    {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.priceMultiplier = priceMultiplier;
        this.ownedVehicles = ownedVehicles;
    }

    public User(String email)
    {
        this.email = email;
        userID = "";
        name = "";
        userType = "";
        priceMultiplier = 1.0;
        ownedVehicles = new ArrayList<String>();
    }

    public User()
    {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public ArrayList<String> getOwnedVehicles() {
        return ownedVehicles;
    }

    public void setOwnedVehicles(ArrayList<String> ownedVehicles) {
        this.ownedVehicles = ownedVehicles;
    }

    public void addVehicle(String vehicleID)
    {
        ownedVehicles.add(vehicleID);
    }
}
