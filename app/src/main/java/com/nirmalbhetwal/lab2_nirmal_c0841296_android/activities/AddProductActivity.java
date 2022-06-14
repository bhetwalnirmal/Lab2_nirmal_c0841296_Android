package com.nirmalbhetwal.lab2_nirmal_c0841296_android.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.nirmalbhetwal.lab2_nirmal_c0841296_android.R;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.abstracts.ProductDatabase;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.models.Product;

public class AddProductActivity extends AppCompatActivity {

    EditText etProductName, etProductDescription, etProductPrice;
    Button btnAddNewProduct;
    ProductDatabase appDb;
    Product product = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // refer to the xml view by id
        etProductName = findViewById(R.id.editTextProductName);
        etProductDescription = findViewById(R.id.editTextProductDescription);
        etProductPrice = findViewById(R.id.editTextProductPrice);
        btnAddNewProduct = findViewById(R.id.addNewProduct);

        appDb = ProductDatabase.getInstance(this);

        // set click listener of the button
        btnAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etProductName.getText().toString().trim();
                String description = etProductDescription.getText().toString().trim();
                double price = 0;

                if (!etProductPrice.getText().toString().trim().equals("")) {
                    price = Double.parseDouble(etProductPrice.getText().toString().trim());
                }

                if (product == null) {
                    // parse the data from the form and trim the trailing white space

                    // create a new product
                    Product product = new Product(name, description, price);
                    // save the product to database
                    saveProductToDatabase(product);
                } else {
                    product.setName(name);
                    product.setDescription(description);
                    product.setPrice(price);

                    updateProduct(product);
                }

                Intent intent = new Intent(AddProductActivity.this, DashboardAcitvity.class);
                intent.putExtra("refresh_dashboard", 1);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            populateAddProductForm(product);
        }
    }

    private void updateProduct(Product product) {
        appDb.productDao().updateProduct(product);

        Toast.makeText(AddProductActivity.this, "Updated product with id " + product.getId() + " successfully!", Toast.LENGTH_LONG).show();
    }

    private void populateAddProductForm(Product product) {
        etProductName.setText(product.getName());
        etProductDescription.setText(product.getDescription());
        etProductPrice.setText(String.format("%.2f", product.getPrice()));

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(String.format("Update product of id = %d", product.getId()));
        }

        btnAddNewProduct.setText("UPDATE PRODUCT");
    }

    protected void saveProductToDatabase (Product product) {
        this.appDb.productDao().insertProduct(product);

        // display a toast to inform user that the data has been saved
        Toast.makeText(AddProductActivity.this, "Product saved", Toast.LENGTH_SHORT).show();

        // reset the form
        resetForm();
    }

    protected void resetForm () {
        etProductName.setText("");
        etProductDescription.setText("");
        etProductPrice.setText("");
    }
}