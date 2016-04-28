package com.example.root.foodber;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kedar on 4/28/2016.
 */
public class RestaurantObjects implements Parcelable {

    String restaurantName,latitude,longitude;

    public RestaurantObjects(String restaurantName, String latitude, String longitude) {
        this.restaurantName = restaurantName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public RestaurantObjects() {

    }

    public static final Creator<RestaurantObjects> CREATOR = new Creator<RestaurantObjects>() {
        @Override
        public RestaurantObjects createFromParcel(Parcel in) {
            return new RestaurantObjects(in);
        }

        @Override
        public RestaurantObjects[] newArray(int size) {
            return new RestaurantObjects[size];
        }
    };

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "RestaurantObjects{" +
                "restaurantName='" + restaurantName + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    @Override
    public int describeContents() {

        return 0;
    }

    protected RestaurantObjects(Parcel in){
        restaurantName=in.readString();
        latitude=in.readString();
        longitude=in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restaurantName);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }
}
