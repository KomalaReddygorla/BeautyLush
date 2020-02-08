package com.example.harsha1123.capstone.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavouriteProductsModel.class},version = 1,exportSchema = false)
public abstract class ProductsDatabase extends RoomDatabase {

    public abstract MyDao myDao();

    private static ProductsDatabase INSTANCE;
    static ProductsDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (ProductsDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProductsDatabase.class, "favuorite.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
