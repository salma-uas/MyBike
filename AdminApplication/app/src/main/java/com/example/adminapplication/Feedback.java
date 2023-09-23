package com.example.adminapplication;


public class Feedback {

    private int booking_id;
    private String time_date;
    private String price;
    private String status;
    private String location;
    private String destination;
    private String rating;
    private String rider_name;
    private String passenger_name;


    public Feedback(int booking_id, String time_date, String price, String status, String location, String destination, String rating, String rider_name, String passenger_name) {
        this.booking_id = booking_id;
        this.time_date = time_date;
        this.price = price;
        this.status = status;
        this.location = location;
        this.destination = destination;
        this.rating = rating;
        this.rider_name = rider_name;
        this.passenger_name = passenger_name;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getTime_date() {
        return time_date;
    }

    public void setTime_date(String time_date) {
        this.time_date = time_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRider_name() {
        return rider_name;
    }

    public void setRider_name(String rider_name) {
        this.rider_name = rider_name;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }




}
