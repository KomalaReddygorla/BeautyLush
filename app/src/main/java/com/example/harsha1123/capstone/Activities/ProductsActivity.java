package com.example.harsha1123.capstone.Activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.harsha1123.capstone.Adapter.ProductsAdapter;
import com.example.harsha1123.capstone.Database.FavouriteProductsModel;

import com.example.harsha1123.capstone.Database.ProductsViewModel;
import com.example.harsha1123.capstone.Others.MyLoader;
import com.example.harsha1123.capstone.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    public static final int PRODUCT_KEY = 0;
    public static final String FAV_KEY = "favourites_key";
    public static final String SAVE_KEY = "saved_state";
    public static final String PR_KEY = "products";
    public static final int FAVOURITE_KEY = 1;

    String productName;
    public RecyclerView rv;
    List<FavouriteProductsModel> list;
    ProductsViewModel viewModel;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String var = "products_key";
    FirebaseAuth mAuth;
    String var1 = "";
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        dialog = new ProgressDialog(this);
        preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
        productName = preferences.getString("pro_key", "Blush");
        rv = findViewById(R.id.recycler);
        list = new ArrayList<>();

        viewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);

        if (preferences.getInt(SAVE_KEY, 0) == 0) {
            getProducts();
        } else if (preferences.getInt(SAVE_KEY, 0) == 1) {
            favouriteProducts();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.products:
                editor = preferences.edit();
                editor.putInt(SAVE_KEY, PRODUCT_KEY);
                editor.apply();
                getProducts();
                break;
            case R.id.favourites:
                editor = preferences.edit();
                editor.putInt(SAVE_KEY, FAVOURITE_KEY);
                editor.apply();
                favouriteProducts();
                break;
            case R.id.logout:
                signout();
                break;
        }
        return true;

    }

    private void signout() {
        mAuth.signOut();
        sendtoLogin();
        finish();
    }

    private void sendtoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void getProducts() {
        showProgress();
        new MyLoader(this).execute(productName);
        dialog.dismiss();
    }

    public void favouriteProducts() {
        showProgress();
        viewModel.getListLiveData().observe(this, new Observer<List<FavouriteProductsModel>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteProductsModel> favouriteProductsModels) {
                if (favouriteProductsModels.size()>= 0) {
                    ProductsAdapter favouritesAdapterClass = new ProductsAdapter(ProductsActivity.this, favouriteProductsModels);

                    int orien = getResources().getConfiguration().orientation;
                    if (orien == Configuration.ORIENTATION_PORTRAIT) {
                        rv.setLayoutManager(new GridLayoutManager(ProductsActivity.this, 2));
                    } else {
                        rv.setLayoutManager(new GridLayoutManager(ProductsActivity.this, 3));
                    }
                    rv.setAdapter(favouritesAdapterClass);
                    dialog.dismiss();
                } else {
                    Toast.makeText(ProductsActivity.this, "NO FAVOURITES AVAILABLE", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showProgress() {
        dialog = new ProgressDialog(this);
        dialog.show();
    }
}
