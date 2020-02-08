package com.example.harsha1123.capstone.Others;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.example.harsha1123.capstone.Activities.ProductsActivity;
import com.example.harsha1123.capstone.Adapter.ProductsAdapter;
import com.example.harsha1123.capstone.Database.FavouriteProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyLoader extends AsyncTask<String,Void,String> {
    ProductsActivity context;
    String url = "http://makeup-api.herokuapp.com/api/v1/products.json?product_type=";

    public MyLoader(ProductsActivity context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url1 = new URL(url+strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ArrayList<FavouriteProductsModel> models = new ArrayList<>();
        String brands, name, desc, mrp, mrpsign, pLink, ids, pImages;
        try {
            JSONArray array = new JSONArray(s);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                pImages = object.optString("image_link");
                ids = object.getString("id");
                brands = object.getString("brand");
                name = object.getString("name");
                desc = object.getString("description");
                mrp = object.getString("price");
                mrpsign = object.getString("price_sign");
                pLink = object.getString("product_link");
                FavouriteProductsModel list = new FavouriteProductsModel(pImages, brands, name, desc, mrp, mrpsign, pLink, ids);
                models.add(list);
            }
            int orien = context.getResources().getConfiguration().orientation;
            if (orien == Configuration.ORIENTATION_PORTRAIT) {
                context.rv.setLayoutManager(new GridLayoutManager(context, 2));
            } else {
                context.rv.setLayoutManager(new GridLayoutManager(context, 3));
            }
            context.rv.setAdapter(new ProductsAdapter(context, models));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

