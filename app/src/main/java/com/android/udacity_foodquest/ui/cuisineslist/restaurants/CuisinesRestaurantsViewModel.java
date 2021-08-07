package com.android.udacity_foodquest.ui.cuisineslist.restaurants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.udacity_foodquest.common.BaseViewModel;
import com.android.udacity_foodquest.model.restaurant.search.Restaurant;
import com.android.udacity_foodquest.model.restaurant.search.SearchResponse;
import com.android.udacity_foodquest.repository.CuisinesRepository;

import java.util.List;

import javax.inject.Inject;

public class CuisinesRestaurantsViewModel extends BaseViewModel {

    private CuisinesRepository repository;

    private MutableLiveData<List<Restaurant>> restaurantsLiveData = new MutableLiveData<>();

    @Inject
    public CuisinesRestaurantsViewModel(CuisinesRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Restaurant>> getRestaurants(int entityId, String entityType, String cuisineId) {
        disposable.add(repository.getRestaurants(entityId, entityType, cuisineId)
                .map(SearchResponse::getRestaurants)
                .subscribe(restaurants -> restaurantsLiveData.setValue(restaurants)));

        return restaurantsLiveData;
    }
}
