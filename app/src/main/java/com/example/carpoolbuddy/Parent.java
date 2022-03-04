package com.example.carpoolbuddy;

import java.util.ArrayList;

public class Parent extends User{

    private ArrayList<String> childrenUserIDs;


    public Parent(String userID, String name, String email, String userType, double priceMultiplier,
                  ArrayList<String> ownedVehicles, ArrayList<String> childrenUserIDs)
    {
        super(userID, name, email, userType, priceMultiplier, ownedVehicles);
        this.childrenUserIDs = childrenUserIDs;
    }

    public ArrayList<String> getChildrenUserIDs() {
        return childrenUserIDs;
    }

    public void setChildrenUserIDs(ArrayList<String> childrenUserIDs) {
        this.childrenUserIDs = childrenUserIDs;
    }
}
