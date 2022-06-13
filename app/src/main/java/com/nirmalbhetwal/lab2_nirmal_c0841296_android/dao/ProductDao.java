package com.nirmalbhetwal.lab2_nirmal_c0841296_android.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nirmalbhetwal.lab2_nirmal_c0841296_android.models.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("Select * from products")
    List<Product> getProductList();

    @Insert
    void insertProduct(Product product);
//
//    @Update
//    void updateProduct(Product product);
//
//    @Delete
//    void deleteProduct(Product product);
}
