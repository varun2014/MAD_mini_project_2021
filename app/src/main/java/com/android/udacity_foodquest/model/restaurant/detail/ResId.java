package com.android.udacity_foodquest.model.restaurant.detail;

import com.google.gson.annotations.SerializedName;

public class ResId {

    @SerializedName("res_id")
    private int resId;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
