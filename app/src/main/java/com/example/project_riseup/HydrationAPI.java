package com.example.project_riseup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HydrationAPI {

    @PUT("hydration/{id}")
    Call<Hydration> updateHydrationRecord(@Path("id") Long id, @Body Hydration hydration);

    @GET("hydration/user/{userId}")
    Call<List<Hydration>> getHydrationRecordsByUserId(@Path("userId") long userId);


}
