package com.example.harsha1123.capstone.Activities;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.harsha1123.capstone.Database.FavouriteProductsModel;
import com.example.harsha1123.capstone.Database.ProductsViewModel;
import com.example.harsha1123.capstone.Others.ProductsWidget;
import com.example.harsha1123.capstone.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {


    ImageView pimage;
    LikeButton likeButton;
    TextView bName, pName, prices, pLink, descrip;
    String brands, names, desc, mrp, mrpsign, plink, pImage, pid;
    ProductsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        likeButton = findViewById(R.id.like_button);
        bName = findViewById(R.id.brandName);
        pName = findViewById(R.id.productName);
        prices = findViewById(R.id.price);
        pLink = findViewById(R.id.productLink);
        descrip = findViewById(R.id.description);
        pimage = findViewById(R.id.product_image);

        viewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);


        pid = getIntent().getStringExtra("ids");
        names = getIntent().getStringExtra("name");
        brands = getIntent().getStringExtra("brand");
        mrp = getIntent().getStringExtra("price");
        mrpsign = getIntent().getStringExtra("price_sign");
        desc = getIntent().getStringExtra("desc");
        plink = getIntent().getStringExtra("plink");
        pImage = getIntent().getStringExtra("pimage");
        bName.setText(brands);
        pName.setText(names);
        prices.setText(mrpsign + mrp);
        pLink.setText(plink);
        descrip.setText(desc);
        Glide.with(this).load(pImage).placeholder(R.mipmap.ic_launcher).into(pimage);
        favouriteProducts();
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(ProductDetailsActivity.this, "Added to Liked Products", Toast.LENGTH_SHORT).show();
                FavouriteProductsModel model =
                        new FavouriteProductsModel(pImage, brands, names, desc, mrp, mrpsign, plink, pid);
                viewModel.insertList(model);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(ProductDetailsActivity.this, "Removed from Liked Products", Toast.LENGTH_SHORT).show();
                FavouriteProductsModel model =
                        new FavouriteProductsModel(pImage, brands, names, desc, mrp, mrpsign, plink, pid);
                viewModel.deletelist(model);
            }
        });
        widgets();

    }

    private void favouriteProducts() {
        List<FavouriteProductsModel> productsModels = viewModel.getListData();
        for (int i = 0; i < productsModels.size(); i++) {
            String ids = productsModels.get(i).getIds();
            if (ids.equalsIgnoreCase(pid)) {
                likeButton.setLiked(true);
            }
        }/*.observe(this, new Observer<List<FavouriteProductsModel>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteProductsModel> productsModels) {

            }
        });*/

    }

    void widgets() {
        SharedPreferences sharedPreferences = getSharedPreferences("komala", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pname", names);
        editor.putString("brand", brands);
        editor.putString("mrp", mrpsign + mrp);
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplicationContext())
                .getAppWidgetIds(new ComponentName(getApplication(), ProductsWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }
}