package com.example.harsha1123.capstone.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ProductsRepository {
    MyDao myDao;
    LiveData<List<FavouriteProductsModel>> movieslist;
    public ProductsRepository(Application application) {
        ProductsDatabase dataBase=ProductsDatabase.getDatabase(application);
        myDao=dataBase.myDao();
        movieslist=myDao.getAllData();
    }

    LiveData<List<FavouriteProductsModel>> getData(){
        return movieslist;
    }

    public void insert(FavouriteProductsModel productsModel){
        new TaskInsert(myDao).execute(productsModel);
    }

    public void delete(FavouriteProductsModel productsModel){
        new TaskDelete(myDao).execute(productsModel);
    }

    public List<FavouriteProductsModel> getList() {
        return myDao.getList();
    }

    class TaskInsert extends AsyncTask<FavouriteProductsModel,Void,Void> {
        private MyDao dao;
        public TaskInsert(MyDao moviesDAO) {

            dao=moviesDAO;
        }

        @Override
        protected Void doInBackground(FavouriteProductsModel... favouriteProductsModels) {
            dao.insertData(favouriteProductsModels[0]);
            return null;
        }
    }

    private class TaskDelete extends AsyncTask<FavouriteProductsModel,Void,Void> {
        MyDao mdao;
        public TaskDelete(MyDao moviesDAO) {
            mdao=moviesDAO;
        }

        @Override
        protected Void doInBackground(FavouriteProductsModel... favouriteProductsModels) {
            mdao.deleteData(favouriteProductsModels[0]);
            return null;

        }
    }
}
