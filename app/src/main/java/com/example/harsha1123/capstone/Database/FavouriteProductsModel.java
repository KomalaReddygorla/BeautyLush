package com.example.harsha1123.capstone.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "favourite_products")
public class FavouriteProductsModel implements Parcelable {

        String brands,name,desc,mrp,mrpsign,pLink,pImages;

        @PrimaryKey
                @NonNull
        String ids;

        public FavouriteProductsModel(String pImages, String brands,
                             String name, String desc, String mrp, String mrpsign, String pLink,@NonNull String ids) {
            this.pImages = pImages;
           // this.pColor = pColor;
            this.brands = brands;
            this.name = name;
            this.desc = desc;
            this.mrp = mrp;
            this.mrpsign = mrpsign;
            this.pLink = pLink;

            this.ids=ids;
        }

    protected FavouriteProductsModel(Parcel in) {
        brands = in.readString();
        name = in.readString();
        desc = in.readString();
        mrp = in.readString();
        mrpsign = in.readString();
        pLink = in.readString();
        pImages = in.readString();
        ids = in.readString();
    }

    public static final Creator<FavouriteProductsModel> CREATOR = new Creator<FavouriteProductsModel>() {
        @Override
        public FavouriteProductsModel createFromParcel(Parcel in) {
            return new FavouriteProductsModel(in);
        }

        @Override
        public FavouriteProductsModel[] newArray(int size) {
            return new FavouriteProductsModel[size];
        }
    };

    public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getpImages() {
            return pImages;
        }

        public void setpImages(String pImages) {
            this.pImages = pImages;
        }

        /*public ArrayList<String> getpColor() {
            return pColor;
        }

        public void setpColor(ArrayList<String> pColor) {
            this.pColor = pColor;
        }*/

        public String getBrands() {
            return brands;
        }

        public void setBrands(String brands) {
            this.brands = brands;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getMrpsign() {
            return mrpsign;
        }

        public void setMrpsign(String mrpsign) {
            this.mrpsign = mrpsign;
        }

        public String getpLink() {
            return pLink;
        }

        public void setpLink(String pLink) {
            this.pLink = pLink;
        }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(brands);
        parcel.writeString(name);
        parcel.writeString(desc);
        parcel.writeString(mrp);
        parcel.writeString(mrpsign);
        parcel.writeString(pLink);
        parcel.writeString(pImages);
        parcel.writeString(ids);
    }
}
