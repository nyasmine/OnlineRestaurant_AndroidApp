package com.yasmine.mytelp;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class YelpResponse implements Serializable {

    @SerializedName("total")
    public int total;

    @SerializedName("businesses")
    public ArrayList<Businesses> businesses;

    class Businesses{

        @SerializedName("name")
        public String name;

        @SerializedName("image_url")
        public String imageYelpUrl;

        @SerializedName("rating")
        public Float rating;

        public Float getRating(){
            return rating;
        }

        @SerializedName("price")
        public String price;

        public int getPrice(){
            if (price == null){
                return 0;
            } if (price.equals("$")) {
                return 1;
            } if (price.equals("$$")){
                return 2;
            } if (price.equals("$$$")){
                return 3;
            } if (price.equals("$$$$")){
                return 4;
            }
            return 0;
        }

        @SerializedName("phone")
        public String phone;

        @SerializedName("categories")
        public ArrayList<Categories> categories;
        class Categories{

            @SerializedName("alias")
            public String categoryAlias;

            @SerializedName("title")
            public String categoryTitle;
        }

        @SerializedName("location")
        public Location location;

        class Location{ //Check video arr 1h55
            @SerializedName("address1")
            public String address1;

            @SerializedName("city")
            public String city;

            @SerializedName("state")
            public String state;
        }
    }
}
