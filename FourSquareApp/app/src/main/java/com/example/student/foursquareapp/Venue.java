package com.example.student.foursquareapp;

/**
 * Created by student on 3/21/16.
 */
public class Venue {

    String venueId, venueName,categoryName,categoryIcon,checkInCount;
    boolean visited;


    public Venue(String venueId, String venueName, String categoryName, String categoryIcon, String checkInCount) {
        this.venueId = venueId;
        this.venueName = venueName;
        this.categoryName = categoryName;
        this.categoryIcon = categoryIcon;
        this.checkInCount = checkInCount;
        visited=false;

    }


    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {

        return visited;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCheckInCount() {
        return checkInCount;
    }

    public void setCheckInCount(String checkInCount) {
        this.checkInCount = checkInCount;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "venueId='" + venueId + '\'' +
                ", venueName='" + venueName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryIcon='" + categoryIcon + '\'' +
                ", checkInCount='" + checkInCount + '\'' +
                '}';
    }
}
