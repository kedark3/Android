package com.example.root.foodber;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by kedar on 4/29/2016.
 */
public class OrderClass implements Parcelable{

    private String user,key,cost,status,assignedTo,timestamp,paidToDeliverer;
    private ArrayList<MenuItems> orderedItems;


    protected OrderClass(Parcel in) {
        user = in.readString();
        key = in.readString();
        cost = in.readString();
        status = in.readString();
        assignedTo = in.readString();
        timestamp = in.readString();
        paidToDeliverer = in.readString();
        orderedItems = in.createTypedArrayList(MenuItems.CREATOR);
    }

    public static final Creator<OrderClass> CREATOR = new Creator<OrderClass>() {
        @Override
        public OrderClass createFromParcel(Parcel in) {
            return new OrderClass(in);
        }

        @Override
        public OrderClass[] newArray(int size) {
            return new OrderClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(key);
        dest.writeString(status);
        dest.writeString(assignedTo);
        dest.writeString(timestamp);
        dest.writeString(paidToDeliverer);
        dest.writeTypedList(orderedItems);
    }

    public OrderClass() {
    }

    public OrderClass(String user, String cost,
                      String status, String assignedTo, String timestamp,
                      String paidToDeliverer, ArrayList<MenuItems> orderedItems) {
        this.user = user;
        this.cost = cost;
        this.status = status;
        this.assignedTo = assignedTo;
        this.timestamp = timestamp;
        this.paidToDeliverer = paidToDeliverer;
        this.orderedItems = orderedItems;
    }

    @Override
    public String toString() {
        return "OrderClass{" +
                "user='" + user + '\'' +
                ", key='" + key + '\'' +
                ", cost='" + cost + '\'' +
                ", status='" + status + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", paidToDeliverer='" + paidToDeliverer + '\'' +
                ", orderedItems=" + orderedItems +
                '}';
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaidToDeliverer() {
        return paidToDeliverer;
    }

    public void setPaidToDeliverer(String paidToDeliverer) {
        this.paidToDeliverer = paidToDeliverer;
    }

    public ArrayList<MenuItems> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(ArrayList<MenuItems> orderedItems) {
        this.orderedItems = orderedItems;
    }


}
