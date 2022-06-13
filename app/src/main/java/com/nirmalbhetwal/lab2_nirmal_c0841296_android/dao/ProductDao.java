package com.nirmalbhetwal.lab2_nirmal_c0841296_android.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nirmalbhetwal.lab2_nirmal_c0841296_android.models.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("Select * from products");
    List<Product> getProductList();

    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);
}
