package com.example.carpoolbuddy;

import java.util.ArrayList;

public class Alumni extends User{

    private String graduateYear;

    public Alumni(String userID, String name, String email, String userType,
                  double priceMultiplier, ArrayList<String> ownedVehicles, String graduateYear)
    {
        super(userID, name, email, userType, priceMultiplier, ownedVehicles);
        this.graduateYear = graduateYear;
    }

    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }
}
