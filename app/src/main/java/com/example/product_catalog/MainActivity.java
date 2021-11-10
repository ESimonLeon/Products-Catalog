package com.example.product_catalog;

import android.os.Bundle;

import com.example.product_catalog.service.response.ResCatalog;
import com.example.product_catalog.service.response.ResCategories;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.product_catalog.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ProductsListAdapter.OnClickItemProduct {


    private BottomSheetBehavior<NavigationView> bottomSheetBehavior;
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        createBottomNavigate();
        createRecyclerView();
        createViewModel();
        createObservers();

        setTitleBottomBar(getString(R.string.all_products));

        viewModel.getCategories();
        viewModel.getProducts();
    }

    private void setTitleBottomBar(String title) {
        binding.tvTitleBottomAppBar.setText(title);
    }

    private void createBottomNavigate() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.nvMenu);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.babActivity.setNavigationOnClickListener(this);
        binding.nvMenu.setNavigationItemSelectedListener(this);
    }

    private void createRecyclerView() {
        binding.rvProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        binding.rvProduct.addItemDecoration(dividerItemDecoration);
    }

    private void applyChangeList(ArrayList<ResCatalog.ResultCatalog.ResultCatalogProducts> resultCatalogProducts) {
        ProductsListAdapter adapterList = new ProductsListAdapter(this, resultCatalogProducts);
        adapterList.notifyItemChanged(adapterList.getItemCount());
        binding.rvProduct.setAdapter(adapterList);
    }

    private void createViewModel() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding.setLifecycleOwner(this);
    }

    private void createObservers() {
        viewModel.categoriesList.observe(this, categories -> {
            if (categories.size() > 0) createMenuNav(categories);
        });

        viewModel.productsList.observe(this, resultCatalogProducts -> {
            if (resultCatalogProducts.size() > 0) applyChangeList(resultCatalogProducts);
        });

        viewModel.dailureRes.observe(this, throwable -> {
            if (throwable != null) showAlertError(throwable);
        });
    }

    private void showAlertError(Throwable throwable) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.an_error_occurred))
                .setMessage(throwable.getLocalizedMessage()).setPositiveButton(getString(R.string.close_alert), (dialogInterface, i) -> dialogInterface.dismiss())
                .create()
                .show();
    }


    private void createMenuNav(ArrayList<ResCategories.ResultCategories> categories) {
        Menu dinamicMenu = binding.nvMenu.getMenu();

        dinamicMenu.add(1, 0, 0, getString(R.string.all_products));

        categories.forEach(resultCategories -> dinamicMenu.add(1, resultCategories.getId(), resultCategories.getId(), resultCategories.getName()));

        binding.nvMenu.invalidate();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        ArrayList<ResCatalog.ResultCatalog.ResultCatalogProducts> resultCatalogProductsFilter = new ArrayList<>();
        if (viewModel.productsList.getValue() != null) {
            viewModel.productsList.getValue().forEach(resultCatalogProducts -> {
                if (item.getItemId() == 0) {
                    if (binding.tvTitleBottomAppBar.getText() != item.getTitle()) {
                        resultCatalogProductsFilter.addAll(viewModel.productsList.getValue());
                        applyChangeList(resultCatalogProductsFilter);
                    }

                } else {
                    if (resultCatalogProducts.getCategory_id() == item.getItemId())
                        resultCatalogProductsFilter.add(resultCatalogProducts);

                    applyChangeList(resultCatalogProductsFilter);
                }
            });
        }
        setTitleBottomBar(item.getTitle().toString());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        return true;
    }

    @Override
    public void onClick(View view) {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        else bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void setProduct(ResCatalog.ResultCatalog.ResultCatalogProducts resultCatalogProducts) {
        Toast.makeText(this, " Selecciono el producto" + resultCatalogProducts.getProduct_name(), Toast.LENGTH_SHORT).show();
    }
}

