package com.example.product_catalog.service;

import static com.example.product_catalog.service.ApiRoutes.CATALOG;
import static com.example.product_catalog.service.ApiRoutes.CATEGORIES;

import com.example.product_catalog.service.response.ResCatalog;
import com.example.product_catalog.service.response.ResCategories;

import retrofit2.Call;
import retrofit2.http.GET;

interface ApiServiceInterface {

    @GET(CATALOG)
    Call<ResCatalog> getProductos();

    @GET(CATEGORIES)
    Call<ResCategories> getCategories();
}
