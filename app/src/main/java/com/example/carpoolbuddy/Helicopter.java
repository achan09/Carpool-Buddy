package com.example.carpoolbuddy;

import java.util.ArrayList;

public class Helicopter extends Vehicle{

    private int maxAltitude;
    private int maxAirSpeed;


    public Helicopter(String owner, String model, int capacity, String vehicleID,
                      ArrayList<String> ridersUIDs, boolean open, String vehicleType,
                      double basePrice, int maxAirSpeed, int maxAltitude)
    {
        super(owner, model, capacity, vehicleID, ridersUIDs, open, vehicleType, basePrice);
        this.maxAirSpeed = maxAirSpeed;
        this.maxAltitude = maxAltitude;
    }

    public int getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(int maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public int getMaxAirSpeed() {
        return maxAirSpeed;
    }

    public void setMaxAirSpeed(int maxAirSpeed) {
        this.maxAirSpeed = maxAirSpeed;
    }
}
