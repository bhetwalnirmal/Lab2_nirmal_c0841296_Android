package com.nirmalbhetwal.lab2_nirmal_c0841296_android.abstracts;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nirmalbhetwal.lab2_nirmal_c0841296_android.dao.ProductDao;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.models.Product;

@Database(entities = Product.class, exportSchema = false, version = 1)
public abstract class ProductDatabase extends RoomDatabase {
    private static final String DB_NAME = "products_db";
    private static ProductDatabase instance;

    public static synchronized ProductDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract ProductDao productDao();
}
