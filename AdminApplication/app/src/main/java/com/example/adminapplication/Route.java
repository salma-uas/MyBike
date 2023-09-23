package com.example.adminapplication;


public class Route {

    private int location_id;
    private String price;
    private String location;
    private String destination;

    public Route( int location_id, String location, String destination, String price) {
        this.location_id = location_id;
        this.price = price;
        this.location = location;
        this.destination = destination;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }






}
