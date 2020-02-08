package com.example.harsha1123.capstone.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ProductsViewModel extends AndroidViewModel {
    LiveData<List<FavouriteProductsModel>> listLiveData;
    ProductsRepository productsRepository;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
        productsRepository=new ProductsRepository(application);
        listLiveData=productsRepository.getData();
    }
    public LiveData<List<FavouriteProductsModel>> getListLiveData()
    {
        return listLiveData;
    }

    public void  insertList(FavouriteProductsModel favuoriteMovies){
        productsRepository.insert(favuoriteMovies);
    }

    public void deletelist(FavouriteProductsModel favuoriteMovies1){
        productsRepository.delete(favuoriteMovies1);
    }

    public List<FavouriteProductsModel> getListData() {
        return productsRepository.getList();
    }
}
