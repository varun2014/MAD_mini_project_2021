package com.android.udacity_foodquest.ui.cuisineslist.restaurants;

import android.widget.ImageView;

import com.android.udacity_foodquest.model.restaurant.search.Restaurant;

public interface CuisinesRestaurantsCallback {
    void onRestaurantClick(Restaurant restaurant, ImageView sharedElement);

    void onRestaurantMarkerClick(Restaurant restaurant);
}
