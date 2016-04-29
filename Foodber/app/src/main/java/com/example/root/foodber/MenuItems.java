package com.example.root.foodber;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by kedar on 4/28/2016.
 */
public class MenuItems implements Parcelable {

    private String itemName,description,cost;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MenuItems(String itemName, String description, String cost) {
        this.itemName = itemName;
        this.description = description;
        this.cost = cost;
    }

    protected  MenuItems(Parcel in){
        itemName=in.readString();
        description=in.readString();
        cost=in.readString();
        quantity=in.readInt();
    }

    public MenuItems() {

    }

    public static final Creator<MenuItems> CREATOR = new Creator<MenuItems>() {
        @Override
        public MenuItems createFromParcel(Parcel in) {
            return new MenuItems(in);
        }

        @Override
        public MenuItems[] newArray(int size) {
            return new MenuItems[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(description);
        dest.writeString(cost);
        dest.writeInt(quantity);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override

    public String toString() {
        return "MenuItems{" +
                "itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
