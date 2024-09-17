package com.example.project_riseup;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Body;
import retrofit2.http.Path;

public interface StepsApi {

    @POST("/steps")
    Call<Steps> insertSteps(@Body Steps steps);  // Insert new steps data

    @GET("/steps")
    Call<List<Steps>> getAllSteps();  // Get all steps data

    @GET("/steps/{date}")
    Call<Steps> getStepsByDate(@Path("date") String date);  // Get steps by date

    @PUT("/steps/{date}")
    Call<Steps> updateSteps(@Path("date") String date, @Body Steps steps);  // Update steps by date

    @DELETE("/steps/{date}")
    Call<Void> deleteSteps(@Path("date") String date);  // Delete steps data by date
}
