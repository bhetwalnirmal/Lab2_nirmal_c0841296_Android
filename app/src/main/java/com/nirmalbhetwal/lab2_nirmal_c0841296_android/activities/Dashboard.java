package com.nirmalbhetwal.lab2_nirmal_c0841296_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.nirmalbhetwal.lab2_nirmal_c0841296_android.R;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.abstracts.ProductDatabase;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.adapters.ProductAdapter;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    RecyclerView productsRecyclerView;
    List<Product> productList = new ArrayList<>();
    ProductDatabase appDb;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        appDb = ProductDatabase.getInstance(this);

        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);
        productList = appDb.productDao().getProductList();
        ProductAdapter productAdapter = new ProductAdapter(this, productList);
        layoutManager = new LinearLayoutManager(this);
        productsRecyclerView.setLayoutManager(layoutManager);
        productsRecyclerView.setAdapter(productAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(productsRecyclerView.getContext(), layoutManager.getOrientation());
        productsRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}