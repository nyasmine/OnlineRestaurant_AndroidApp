package com.yasmine.mytelp;

public class YelpObject {
    String name;
    Float rating;
    String price;
    String category;
    String phoneNumber;
    String address;
    int imageId;

    public YelpObject(String name, String price, Float rating, String category, String phoneNumber, String address, int imageId){
        this.name = name;
        this.imageId = imageId;
        this.rating =rating;
        this.price = price;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.address = address;


    }

}
