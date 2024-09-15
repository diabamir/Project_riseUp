package com.example.project_riseup;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @POST("/users")
    Call<User> insertUser(@Body User user);

    @GET("/users")
    Call<List<User>> getAllUsers();

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") long id);

    @PUT("/users/{id}")
    Call<User> updateUser(@Path("id") long id, @Body User user);


    @DELETE("/users/{id}")
    Call<Void> deleteUser(@Path("id") long id);
}
//package com.example.project_riseup;
//
//
//import java.util.List;
//import retrofit2.Call;
//import retrofit2.http.DELETE;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.Body;
//import retrofit2.http.PUT;
//import retrofit2.http.Path;
//
//public interface UserApi {
//    @POST("/users")
//    Call<User> insertUser(@Body User user);
//
//    @GET("/users")
//    Call<List<User>> getAllUsers();
//
//    @GET("/users/{id}")
//    Call<User> getUserById(@Path("id") long id);
//
//    @PUT("/users/{id}")
//    Call<User> updateUser(@Path("id") long id, @Body User user);
//
//    @DELETE("/users/{id}")
//    Call<Void> deleteUser(@Path("id") long id);
//}