package com.example.harsha1123.capstone.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyDao {

    @Query("SELECT * FROM favourite_products")
    LiveData<List<FavouriteProductsModel>> getAllData();

    @Insert
    void insertData(FavouriteProductsModel productsModel);

    @Delete
    void deleteData(FavouriteProductsModel productsModel);

    @Query("SELECT * FROM favourite_products")
    List<FavouriteProductsModel> getList();
}
