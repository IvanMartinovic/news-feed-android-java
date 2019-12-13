package com.example.vesti;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

   public static final String BASE_URL = "http://91.187.137.161:8080/Vesti/rest/";
    private static ApiInterface apiInterface = null;

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();


    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
            .build();

    public static <S> S getClient(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }


}
