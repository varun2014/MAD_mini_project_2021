package com.android.udacity_foodquest.ui.search;

import android.widget.ImageView;

import com.android.udacity_foodquest.model.restaurant.search.Restaurant;

public interface SearchResultsCallback {
    void onRestaurantClick(Restaurant restaurant, ImageView sharedElement);

    void onRestaurantMarkerClick(Restaurant restaurant);
}
