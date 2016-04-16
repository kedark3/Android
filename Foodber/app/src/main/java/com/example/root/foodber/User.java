package com.example.root.foodber;

import org.json.JSONObject;

/**
 * Created by root on 4/15/16.
 */
public class User{

    String email,password,name,uncc800Num,phone;
    int rating,noOfRatings;
    JSONObject isDelivering;

    public User(String email, String password, String name, String uncc800Num, String phone, int rating, int noOfRatings, JSONObject isDelivering) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.uncc800Num = uncc800Num;
        this.phone = phone;
        this.rating = rating;
        this.noOfRatings = noOfRatings;
        this.isDelivering = isDelivering;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUncc800Num() {
        return uncc800Num;
    }

    public void setUncc800Num(String uncc800Num) {
        this.uncc800Num = uncc800Num;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getNoOfRatings() {
        return noOfRatings;
    }

    public void setNoOfRatings(int noOfRatings) {
        this.noOfRatings = noOfRatings;
    }

    public JSONObject getIsDelivering() {
        return isDelivering;
    }

    public void setIsDelivering(JSONObject isDelivering) {
        this.isDelivering = isDelivering;
    }
}
