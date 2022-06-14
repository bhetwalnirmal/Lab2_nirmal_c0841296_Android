package com.nirmalbhetwal.lab2_nirmal_c0841296_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
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

        if (productList.size() == 0) {
            populateDummyData();
        }

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
                Product product = productList.get(position);

                Intent intent = new Intent(DashboardAcitvity.this, ProductDetailsActivity.class);
                intent.putExtra("product_id", product.getId());
                startActivity(intent);
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
                            new AlertDialog.Builder(DashboardAcitvity.this)
                                    .setTitle("Delete product")
                                    .setMessage("Are you sure you want to delete this entry?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            deleteProduct(position);
                                            productAdapter.setProductList(productList);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, null)
                                    .show();
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

    private void populateDummyData() {
        Product product = new Product("DEWALT [HARDWARE]", "Often the art of writing a great product description that sells the product lies in the ability to describe the little things that make all the difference.\n" +
                "\n" +
                "The little differences and features in a product that may seem trivial or boring to you are important to shoppers and help them make purchase decisions.\n" +
                "\n" +
                "DeWalt, the creator of the 14 oz Mig Weld Framing Hammer, does just that", 59.49);
        this.appDb.productDao().insertProduct(product);
        product = new Product("PATAGONIA [OUTDOOR GEAR]", "It’s no accident that Patagonia finds themselves on a list like this.\n" +
                "\n" +
                "They’ve spent years crafting and perfecting their well-known brand, and every single product description is an extension of that.\n" +
                "\n" +
                "Let’s pick a product, any product. This Linked Pack 28L will do", 249.99);
        this.appDb.productDao().insertProduct(product);
        product = new Product("APPSUMO [ONLINE COURSES]", "Not every ecommerce product is a physical one, yet it still takes the same wordsmith finesse to sell a service.\n" +
                "\n" +
                "AppSumo’s Make Your First Dollar online course is a prime example of the persuasive power of what I like to call the “ideal you.”\n" +
                "\n", 59.49);
        this.appDb.productDao().insertProduct(product);
        product = new Product("SUBARU [AUTOS]", "Selling an item where the price point is in the tens of thousands of dollars takes a product description that is more than just a clever sentence or two. \n" +
                "\n" +
                "Selling something like a car, for example, requires a full understanding of your ideal customers’ psychographics. You need to speak to their deepest fears, pain points, and desires.\n" +
                "\n" +
                "Their new Subaru Ascent’s product page is handcrafted for their ideal customer: families", 12199.99);
        this.appDb.productDao().insertProduct(product);

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
        this.productList = this.appDb.productDao().getSearchResults(s);
        productAdapter.setProductList(productList);
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