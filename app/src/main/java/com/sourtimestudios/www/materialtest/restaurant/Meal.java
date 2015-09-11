package com.sourtimestudios.www.materialtest.restaurant;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by user on 13/08/15.
 */
public class Meal {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private String mPrice;
    private Bitmap mPhoto;


    public Meal(String title, String description){
        mId = UUID.randomUUID();
        mTitle = title;
        mDescription = description;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Bitmap getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(Bitmap mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }
}
