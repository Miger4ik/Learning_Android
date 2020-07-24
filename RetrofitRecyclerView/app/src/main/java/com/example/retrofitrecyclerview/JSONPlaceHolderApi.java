package com.example.retrofitrecyclerview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// інтерфейс в якому оголошені всі методи для роботи з даними
public interface JSONPlaceHolderApi {
    @GET("/posts")
    public Call<List<Post>> getPostWithID();
}
