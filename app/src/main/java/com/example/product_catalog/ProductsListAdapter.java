package com.example.product_catalog;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.product_catalog.databinding.ItemProductBinding;
import com.example.product_catalog.service.response.ResCatalog;

import java.util.ArrayList;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductsListAdapterProductsListHolder> {

    OnClickItemProduct listener;
    ArrayList<ResCatalog.ResultCatalog.ResultCatalogProducts> resultCatalogProducts;

    public ProductsListAdapter(OnClickItemProduct listener, ArrayList<ResCatalog.ResultCatalog.ResultCatalogProducts> resultCatalogProducts) {
        this.listener = listener;
        this.resultCatalogProducts = resultCatalogProducts;
    }

    interface OnClickItemProduct {
        void setProduct(ResCatalog.ResultCatalog.ResultCatalogProducts resultCatalogProducts);
    }

    @NonNull
    @Override
    public ProductsListAdapterProductsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsListAdapterProductsListHolder(ItemProductBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsListAdapterProductsListHolder holder, int position) {
        holder.binding.setResultCatalogProducts(resultCatalogProducts.get(position));

        Glide.with(holder.itemView.getContext())
                .load(resultCatalogProducts.get(position).getProduct_image())
                .into(holder.binding.ivImageProduct);


        holder.itemView.setOnClickListener(view -> listener.setProduct(resultCatalogProducts.get(position)));
    }

    @Override
    public int getItemCount() {
        return resultCatalogProducts.size();
    }

    public static class ProductsListAdapterProductsListHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

        public ProductsListAdapterProductsListHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
