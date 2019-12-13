package com.example.vesti;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


import java.util.ArrayList;



public interface ApiInterface {


    @GET("category/getCategories")
    Call<ArrayList<Category>> getCategories();

    @GET("category/getCategoriesENG")
    Call<ArrayList<Category>> getCategoriesENG();


    @GET("post/{category_id}")
    Call<ArrayList<Post>> getPosts(@Path("category_id") int category_id );

    @GET("post/ENG/{category_id}")
    Call<ArrayList<Post>> getPostsENG(@Path("category_id") int category_id );


}
