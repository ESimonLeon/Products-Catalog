package com.example.product_catalog;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.product_catalog.service.ApiRepository;
import com.example.product_catalog.service.response.ResCatalog;
import com.example.product_catalog.service.response.ResCategories;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel implements ApiRepository.OnResultListener {

    ApiRepository apiRepository;

    private final MutableLiveData<ArrayList<ResCategories.ResultCategories>> _categoriesList = new MutableLiveData<>();
    MutableLiveData<ArrayList<ResCategories.ResultCategories>> categoriesList = _categoriesList;

    private final MutableLiveData<ArrayList<ResCatalog.ResultCatalog.ResultCatalogProducts>> _productsList = new MutableLiveData<>();
    MutableLiveData<ArrayList<ResCatalog.ResultCatalog.ResultCatalogProducts>> productsList = _productsList;

    private final MutableLiveData<Throwable> _dailureRes = new MutableLiveData<>();
    MutableLiveData<Throwable> dailureRes = _dailureRes;

    public MainActivityViewModel() {
        apiRepository = new ApiRepository(this);
    }

    public void getCategories() {
        Thread thread = new Thread(() -> apiRepository.getRepoCategories());
        thread.start();
    }


    @Override
    public void setResponseCategories(ResCategories resCategories) {
        _categoriesList.postValue(resCategories.getCategories());
    }

    @Override
    public void setResponseProducts(ResCatalog resCatalog) {
        _productsList.postValue(resCatalog.getResults().getProducts());
    }

    @Override
    public void setFailure(Throwable t) {

    }


    public void getProducts() {
        Thread thread = new Thread(() -> apiRepository.getProducts());
        thread.start();
    }
}
