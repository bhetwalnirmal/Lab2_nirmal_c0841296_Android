package com.nirmalbhetwal.lab2_nirmal_c0841296_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.R;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.abstracts.ProductDatabase;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.adapters.ProductAdapter;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.listeners.ProductListTouchListener;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DashboardAcitvity extends AppCompatActivity {

    RecyclerView productsRecyclerView;
    List<Product> productList = new ArrayList<>();
    ProductDatabase appDb;
    LinearLayoutManager layoutManager;
    FloatingActionButton fabAddNewProduct;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        appDb = ProductDatabase.getInstance(this);

        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);
        productList = appDb.productDao().getProductList();
        productAdapter = new ProductAdapter(this, productList);
        layoutManager = new LinearLayoutManager(this);
        productsRecyclerView.setLayoutManager(layoutManager);
        productsRecyclerView.setAdapter(productAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(productsRecyclerView.getContext(), layoutManager.getOrientation());
        productsRecyclerView.addItemDecoration(dividerItemDecoration);

        fabAddNewProduct = (FloatingActionButton) findViewById(R.id.fabCreateNewProduct);

        fabAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardAcitvity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        ProductListTouchListener touchListener = new ProductListTouchListener(this, productsRecyclerView);
        touchListener.setClickable(new ProductListTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
//                Toast.makeText(getApplicationContext(), productList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {

            }
        })
            .setSwipeOptionViews(R.id.delete_task,R.id.edit_task)
            .setSwipeable(R.id.rowFG, R.id.rowBG, new ProductListTouchListener.OnSwipeOptionsClickListener() {
                @Override
                public void onSwipeOptionClicked(int viewID, int position) {
                    switch (viewID){
                        case R.id.delete_task:
                            deleteProduct(position);
                            productAdapter.setProductList(productList);
                            break;
                        case R.id.edit_task:
                            Intent intent = new Intent(DashboardAcitvity.this, AddProductActivity.class);
                            Product product = productList.get(position);
                            intent.putExtra("product", product);
                            startActivity(intent);
                            break;

                    }
                }
            });

        productsRecyclerView.addOnItemTouchListener(touchListener);

        int refresh_dashboard = (int) getIntent().getIntExtra("refresh_dashboard", 0);

        if (refresh_dashboard != 0 && refresh_dashboard == 1) {
            List<Product> productList = appDb.productDao().getProductList();
            productAdapter.setProductList(productList);
        }
    }

    private void deleteProduct(int position) {
        Product product = productList.get(position);

        productList.remove(position);
        appDb.productDao().deleteProduct(product);
    }
}