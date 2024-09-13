package com.example.project_riseup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserGroupApi {
    @POST("/Users/{userId}/Groups/{groupId}")
    Call<Void> addUserToGroup(@Path("userId") long userId, @Path("groupId") long groupId, @Body String status);

    @GET("/Users/{userId}/Groups")
    Call<List<Group>> getGroupsForUser(@Path("userId") long userId, @Query("status") String status);

    @GET("/Groups/{groupId}/Users")
    Call<List<User>> getUsersForGroup(@Path("groupId") long groupId, @Query("status") String status);

    // Get the status of a specific user across all groups
    @GET("/Users/{userId}/Groups/Status")
    Call<List<UserGroupJoin>> getUserStatusInAllGroups(@Path("userId") long userId);

    // Get the status of a specific user in a specific group
    @GET("/Users/{userId}/Groups/{groupId}/Status")
    Call<UserGroupJoin> getUserStatusInGroup(@Path("userId") long userId, @Path("groupId") long groupId);

    @PUT("/Users/{userId}/Groups/{groupId}/Status")
    Call<Void> updateUserStatusInGroup(@Path("userId") long userId, @Path("groupId") long groupId, @Body String status);


}