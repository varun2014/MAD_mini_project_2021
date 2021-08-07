package com.android.udacity_foodquest.di;

import com.android.udacity_foodquest.ui.cuisineslist.CuisinesListActivity;
import com.android.udacity_foodquest.ui.cuisineslist.restaurants.CuisinesRestaurantsActivity;
import com.android.udacity_foodquest.ui.detail.RestaurantDetailActivity;
import com.android.udacity_foodquest.ui.establishments.EstablishmentsActivity;
import com.android.udacity_foodquest.ui.favorites.FavoritesActivity;
import com.android.udacity_foodquest.ui.main.MainActivity;
import com.android.udacity_foodquest.ui.nearbyrestaurants.NearbyRestaurantsActivity;
import com.android.udacity_foodquest.ui.search.SearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract CuisinesListActivity cuisinesListActivity();

    @ContributesAndroidInjector
    abstract NearbyRestaurantsActivity nearbyRestaurantsActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract EstablishmentsActivity establishmentsActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract RestaurantDetailActivity restaurantDetailActivity();

    @ContributesAndroidInjector
    abstract FavoritesActivity favoritesActivity();

    @ContributesAndroidInjector
    abstract CuisinesRestaurantsActivity cuisinesRestauActivity();

    @ContributesAndroidInjector
    abstract SearchActivity searchActivity();
}
