package com.android.udacity_foodquest.model.establishments;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EstablishmentsResponse {

    @SerializedName("establishments")
    private List<Establishment> establishments;

    public List<Establishment> getEstablishments() {
        return establishments;
    }

    public void setEstablishments(List<Establishment> establishments) {
        this.establishments = establishments;
    }
}
