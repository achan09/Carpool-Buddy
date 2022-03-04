package com.example.carpoolbuddy;

import java.util.ArrayList;

public class Student extends User{

    private String graduatingYear;


    public Student(String userID, String name, String email, String userType,
                   double priceMultiplier, ArrayList<String> ownedVehicles, String graduatingYear)
    {
        super(userID, name, email, userType, priceMultiplier, ownedVehicles);
        this.graduatingYear = graduatingYear;
    }

    public String getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(String graduatingYear) {
        this.graduatingYear = graduatingYear;
    }
}
