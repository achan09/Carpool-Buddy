package com.example.carpoolbuddy;

import java.util.ArrayList;

public class Teacher extends User{

    private String inSchoolTitle;

    public Teacher(String userID, String name, String email, String userType,
                   double priceMultiplier, ArrayList<String> ownedVehicles, String inSchoolTitle)
    {
        super(userID, name, email, userType, priceMultiplier, ownedVehicles);
        this.inSchoolTitle = inSchoolTitle;
    }

    public String getInSchoolTitle() {
        return inSchoolTitle;
    }

    public void setInSchoolTitle(String inSchoolTitle) {
        this.inSchoolTitle = inSchoolTitle;
    }
}
