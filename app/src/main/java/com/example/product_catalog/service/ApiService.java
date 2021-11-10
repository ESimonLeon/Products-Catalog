package com.example.product_catalog.service;

import static com.example.product_catalog.service.ApiRoutes.SERVICE_URL;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public ApiServiceInterface apiInterface;

    public HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public OkHttpClient providesOkHttpClient() {
        return new OkHttpClient
                .Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(providesHttpLoggingInterceptor())
                .build();
    }


    public ApiService provideRetrofit() {
         Retrofit.Builder retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVICE_URL)
                .client(providesOkHttpClient());

         apiInterface = retrofit.build().create(ApiServiceInterface.class);
         return this;
    }


}
