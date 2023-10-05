package com.example.motorbikeapplication;

import android.support.annotation.NonNull;

public class Trips {

    int location_id;
    String pickup_name;
    String destination_name;
    String price;

    public Trips(int location_id, String pickup_name, String destination_name, String price) {
        this.location_id = location_id;
        this.pickup_name = pickup_name;
        this.destination_name = destination_name;
        this.price = price;
    }


    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getPickup_name() {
        return pickup_name;
    }

    public void setPickup_name(String pickup_name) {
        this.pickup_name = pickup_name;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public void setDestination_name(String destination_name) {
        this.destination_name = destination_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return pickup_name + " > " + destination_name;
    }
}
