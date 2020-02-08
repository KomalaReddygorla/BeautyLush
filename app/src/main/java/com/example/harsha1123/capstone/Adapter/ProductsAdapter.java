package com.example.harsha1123.capstone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.harsha1123.capstone.Activities.ProductDetailsActivity;
import com.example.harsha1123.capstone.Database.FavouriteProductsModel;
import com.example.harsha1123.capstone.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    Context productsActivity;
    List<FavouriteProductsModel> productsModels;
    public ProductsAdapter(Context productsActivity, List<FavouriteProductsModel> productsModels) {
        this.productsModels = productsModels;
        this.productsActivity = productsActivity;
    }

    @NonNull
    @Override
    public ProductsAdapter.ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsViewHolder(LayoutInflater.from(productsActivity).inflate(R.layout.product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ProductsViewHolder holder, int position) {

         Glide.with(productsActivity).load(productsModels.get(position).getpImages()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productsModels.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public ProductsViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.productImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            Intent intent=new Intent(productsActivity, ProductDetailsActivity.class);
            intent.putExtra("ids",productsModels.get(pos).getIds());
            intent.putExtra("name",productsModels.get(pos).getName());
            intent.putExtra("brand",productsModels.get(pos).getBrands());
            intent.putExtra("price",productsModels.get(pos).getMrp());
            intent.putExtra("price_sign",productsModels.get(pos).getMrpsign());
            intent.putExtra("desc",productsModels.get(pos).getDesc());
            intent.putExtra("plink",productsModels.get(pos).getpLink());
            intent.putExtra("pimage",productsModels.get(pos).getpImages());
            productsActivity.startActivity(intent);
        }
    }
}