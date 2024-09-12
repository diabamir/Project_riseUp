package com.example.project_riseup;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GroupApi {

    @GET("groups")
    Call<List<Group>> getAllGroups();

    @POST("groups")
    Call<Group> insertGroup(@Body Group group);

    @PUT("groups/{id}")
    Call<Group> updateGroup(@Path("id") int id, @Body Group group);

    @DELETE("groups/{id}")
    Call<Void> deleteGroup(@Path("id") int id);

    // Assuming you want to filter groups by location using a query parameter
    @GET("groups")
    Call<List<Group>> getGroupsByLocation(@Path("location") String location);

    // Assuming each group can be fetched by its ID
    @GET("groups/{id}")
    Call<Group> getGroupById(@Path("id") int id);
}