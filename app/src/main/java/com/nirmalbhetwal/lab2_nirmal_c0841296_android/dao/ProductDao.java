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

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("Select * from products where name like '%' || :search || '%' or description like '%' || :search || '%'" )
    List<Product> getSearchResults(String search);

    @Query("Select * from products where id = :productId" )
    Product find(int productId);
}
