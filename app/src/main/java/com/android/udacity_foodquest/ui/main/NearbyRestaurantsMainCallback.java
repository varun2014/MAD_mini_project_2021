package com.android.udacity_foodquest.ui.main;

import android.widget.ImageView;

import com.android.udacity_foodquest.model.geocode.NearbyRestaurant;

public interface NearbyRestaurantsMainCallback {

    void onMainNearbyRestaurantsClick(NearbyRestaurant nearbyRestaurant, ImageView sharedElement);

    void onMainNearbyRestaurantMarkerClick(NearbyRestaurant nearbyRestaurant);
}
