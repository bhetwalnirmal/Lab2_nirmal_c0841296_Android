package com.nirmalbhetwal.lab2_nirmal_c0841296_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nirmalbhetwal.lab2_nirmal_c0841296_android.R;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.abstracts.ProductDatabase;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.models.Product;

import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    EditText etProductName, etProductDescription, etProductPrice;
    Button btnAddNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // refer to the xml view by id
        etProductName = findViewById(R.id.editTextProductName);
        etProductDescription = findViewById(R.id.editTextProductDescription);
        etProductPrice = findViewById(R.id.editTextProductPrice);
        btnAddNewProduct = findViewById(R.id.addNewProduct);

        ProductDatabase appDb = ProductDatabase.getInstance(this);

        // set click listener of the button
        btnAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // parse the data from the form and trim the trailing white space
                String name = etProductName.getText().toString().trim();
                String description = etProductDescription.getText().toString().trim();
                double price = 0;
                if (!etProductPrice.getText().toString().trim().equals("")) {
                    price = Double.parseDouble(etProductPrice.getText().toString().trim());
                }

                Product product = new Product(name, description, price);
                appDb.productDao().insertProduct(product);
            }
        });
    }
}