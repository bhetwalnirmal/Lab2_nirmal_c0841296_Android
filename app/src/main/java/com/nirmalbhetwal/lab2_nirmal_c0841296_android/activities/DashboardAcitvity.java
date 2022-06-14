package com.nirmalbhetwal.lab2_nirmal_c0841296_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

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
        List<Product> productList = appDb.productDao().getProductList();
        productAdapter.setProductList(productList);
    }

    private void deleteProduct(int position) {
        Product product = productList.get(position);

        productList.remove(position);
        appDb.productDao().deleteProduct(product);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_bar).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filterData(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterData(s);
                return false;
            }
        });

        return true;
    }

    private void filterData(String s) {
        Log.d("TAG", s);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            Log.d("TAG", query);
        }
    }
}