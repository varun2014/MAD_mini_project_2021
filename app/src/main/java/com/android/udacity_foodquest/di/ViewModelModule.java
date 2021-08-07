package com.android.udacity_foodquest.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.udacity_foodquest.ui.cuisineslist.restaurants.CuisinesRestaurantsViewModel;
import com.android.udacity_foodquest.ui.detail.RestaurantDetailViewModel;
import com.android.udacity_foodquest.ui.establishments.EstablishmentsViewModel;
import com.android.udacity_foodquest.ui.main.MainViewModel;
import com.android.udacity_foodquest.ui.search.SearchViewModel;
import com.android.udacity_foodquest.viewmodel.VMFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindsCuisinesViewModel(MainViewModel cuisinesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EstablishmentsViewModel.class)
    abstract ViewModel bindsEstablishmentsViewModel(EstablishmentsViewModel establishmentsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantDetailViewModel.class)
    abstract ViewModel bindsRestaurantDetailViewModel(RestaurantDetailViewModel restaurantDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CuisinesRestaurantsViewModel.class)
    abstract ViewModel bindsCuisinesRestauViewModel(CuisinesRestaurantsViewModel cuisinesRestauViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindsSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(VMFactory vmFactory);
}
