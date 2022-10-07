package com.dsterwz.dbtest_legend.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FoodApi {
    @GET("dishes/version")
    Call<DishesVersion> getVersion();

    @GET("dishes")
    Call<List<Dish>> getDishes(
            @Query("version") String version
    );

    @POST("auth/register")
    Call<User> registerUser(@Body User user);

    @FormUrlEncoded
    @POST("auth/login")
    Call<User> loginUser(
            @Field("email")String email,
            @Field("password") String password
    );
}