package com.nirmalbhetwal.lab2_nirmal_c0841296_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.nirmalbhetwal.lab2_nirmal_c0841296_android.R;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.abstracts.ProductDatabase;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.models.Product;

public class ProductDetailsActivity extends AppCompatActivity {
    TextView tvProductName, tvProductPrice, tvProductDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tvProductName = (TextView) findViewById(R.id.productName);
        tvProductDescription = (TextView) findViewById(R.id.productDescription);
        tvProductPrice = (TextView) findViewById(R.id.productPrice);

        int productId = getIntent().getIntExtra("product_id", -1);

        if (productId != -1) {
            Product product = ProductDatabase.getInstance(this).productDao().find(productId);

            if (product != null) {
                displayProductDetails(product);
            }
        }
    }

    private void displayProductDetails(Product product) {
        tvProductPrice.setText(String.format("$ %.2f", product.getPrice()));
        tvProductName.setText(product.getName());
        tvProductDescription.setText(product.getDescription());
    }
}