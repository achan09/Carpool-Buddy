package com.example.carpoolbuddy;

import java.util.ArrayList;

public class Skateboard extends Vehicle{

    private double size;
    private String brand;


    public Skateboard(String owner, String model, int capacity, String vehicleID,
                      ArrayList<String> ridersUIDs, boolean open, String vehicleType,
                      double basePrice, double size, String brand)
    {
        super(owner, model, capacity, vehicleID, ridersUIDs, open, vehicleType, basePrice);
        this.size = size;
        this.brand = brand;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
