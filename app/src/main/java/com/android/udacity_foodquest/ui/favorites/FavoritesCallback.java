package com.android.udacity_foodquest.ui.favorites;

import android.widget.ImageView;

import com.android.udacity_foodquest.data.local.entity.CommonRestaurant;

public interface FavoritesCallback {
    void onFavoriteRestaurantClick(CommonRestaurant restaurant, ImageView sharedElement);

    void onFavoriteRestaurantMarkerClick(CommonRestaurant restaurant);
}
