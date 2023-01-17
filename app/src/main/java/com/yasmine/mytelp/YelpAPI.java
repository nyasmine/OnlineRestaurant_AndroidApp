package com.yasmine.mytelp;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpAPI {

    //Get Array List of Restaurant
    @GET("businesses/search")
    Call<YelpResponse> getBusinesses(@Query("location") String location,
                                     @Query("categories") String categories);
}
