package com.example.project_riseup;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupApi {

    // Get all groups
    @GET("groups")
    Call<List<Group>> getAllGroups();

    // Insert a new group
    @POST("groups")
    Call<Group> insertGroup(@Body Group group);

    // Update a group by its ID
    @PUT("groups/{id}")
    Call<Group> updateGroup(@Path("id") long id, @Body Group group);

    // Delete a group by its ID
    @DELETE("groups/{id}")
    Call<Void> deleteGroup(@Path("id") long id);

    // Get groups filtered by location (ensure the endpoint supports this)
    @GET("groups/location") // This assumes the endpoint is correct; otherwise adjust to your backend
    Call<List<Group>> getGroupsByLocation(@Query("location") String location);

    // Get a specific group by its ID
    @GET("groups/{id}")
    Call<Group> getGroupById(@Path("id") long id);
}
//package com.example.project_riseup;
//
//import java.util.List;
//import retrofit2.Call;
//import retrofit2.http.DELETE;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.PUT;
//import retrofit2.http.Body;
//import retrofit2.http.Path;
//import retrofit2.http.Query;
//
//public interface GroupApi {
//
//    // Get all groups
//    @GET("groups")
//    Call<List<Group>> getAllGroups();
//
//    // Insert a new group
//    @POST("groups")
//    Call<Group> insertGroup(@Body Group group);
//
//    // Update a group by its ID
//    @PUT("groups/{id}")
//    Call<Group> updateGroup(@Path("id") long id, @Body Group group);
//
//    // Delete a group by its ID
//    @DELETE("groups/{id}")
//    Call<Void> deleteGroup(@Path("id") long id);
//
//    // Get groups filtered by location (ensure the endpoint supports this)
//    @GET("groups/location") // This assumes the endpoint is correct; otherwise adjust to your backend
//    Call<List<Group>> getGroupsByLocation(@Query("location") String location);
//
//    // Get a specific group by its ID
//    @GET("groups/{id}")
//    Call<Group> getGroupById(@Path("id") long id);
//}
