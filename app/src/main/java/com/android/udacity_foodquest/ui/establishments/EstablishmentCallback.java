package com.android.udacity_foodquest.ui.establishments;

import android.widget.ImageView;

import com.android.udacity_foodquest.model.establishments.Establishment;
import com.android.udacity_foodquest.model.restaurant.search.Restaurant;

public interface EstablishmentCallback {

    interface TypesCalback {
        void onEstablishmentTypesClick(Establishment establishment);
    }

    interface RestaurantCallback {
        void onEstablishmentClick(Restaurant restaurant, ImageView sharedElement);

        void onEstablishmentMarkerClick(Restaurant restaurant);
    }
}
