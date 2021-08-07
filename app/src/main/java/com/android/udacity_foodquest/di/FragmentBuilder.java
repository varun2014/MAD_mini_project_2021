package com.android.udacity_foodquest.di;

import com.android.udacity_foodquest.ui.detail.RestaurantInfoFragment;
import com.android.udacity_foodquest.ui.detail.RestaurantReviewsFragment;
import com.android.udacity_foodquest.ui.establishments.EstablishmentTypesListFragment;
import com.android.udacity_foodquest.ui.establishments.EstablishmentListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract EstablishmentTypesListFragment establishmentTypesListFragment();

    @ContributesAndroidInjector
    abstract EstablishmentListFragment establishmentListFragment();

    @ContributesAndroidInjector
    abstract RestaurantInfoFragment restaurantInfoFragment();

    @ContributesAndroidInjector
    abstract RestaurantReviewsFragment restaurantReviewsFragment();

}
