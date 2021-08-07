package com.android.udacity_foodquest.ui.nearbyrestaurants;

import android.widget.ImageView;

import com.android.udacity_foodquest.model.geocode.NearbyRestaurant;

public interface NearbyRestaurantsCallback {
    void onNearbyRestaurantClick(NearbyRestaurant restaurant, ImageView sharedElement);

    void onNearbyRestaurantMarkerClick(NearbyRestaurant restaurant);
}
