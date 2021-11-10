package com.example.product_catalog.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.product_catalog.service.response.ResCatalog;
import com.example.product_catalog.service.response.ResCategories;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private final ApiService apiService;
    OnResultListener listener;

    public interface OnResultListener {
        void setResponseCategories(ResCategories resCategories);
        void setResponseProducts(ResCatalog resCatalog);

        void setFailure(Throwable t);
    }

    public ApiRepository(OnResultListener listener) {
        apiService = new ApiService().provideRetrofit();
        this.listener = listener;
    }

    public void getRepoCategories() {
        Call<ResCategories> call = apiService.apiInterface.getCategories();
        call.enqueue(new Callback<ResCategories>() {
            @Override
            public void onResponse(@NonNull Call<ResCategories> call, @NonNull Response<ResCategories> response) {
                Log.wtf(getClass().getSimpleName(), " response " + response.body());
                listener.setResponseCategories(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResCategories> call, @NonNull Throwable t) {
                Log.wtf(getClass().getSimpleName(), " onFailure " + t.getCause() + t.getLocalizedMessage());
                listener.setFailure(t);
            }
        });
    }

    public void getProducts() {
        Call<ResCatalog> call = apiService.apiInterface.getProductos();
        call.enqueue(new Callback<ResCatalog>() {
            @Override
            public void onResponse(@NonNull Call<ResCatalog> call, @NonNull Response<ResCatalog> response) {
                listener.setResponseProducts(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResCatalog> call, @NonNull Throwable t) {
                listener.setFailure(t);
            }
        });
    }
}
