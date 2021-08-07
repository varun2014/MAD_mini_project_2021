package com.android.udacity_foodquest.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.udacity_foodquest.common.BaseViewModel;
import com.android.udacity_foodquest.model.restaurant.search.Restaurant;
import com.android.udacity_foodquest.model.restaurant.search.SearchResponse;
import com.android.udacity_foodquest.repository.SearchRepository;

import java.util.List;

import javax.inject.Inject;

public class SearchViewModel extends BaseViewModel {

    private SearchRepository repository;

    private MutableLiveData<List<Restaurant>> restaurantsLiveData = new MutableLiveData<>();

    @Inject
    public SearchViewModel(SearchRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Restaurant>> getRestaurants(String query, int entityId, String entityType) {
        disposable.add(repository.getSearchResults(query, entityId, entityType)
                .map(SearchResponse::getRestaurants)
                .subscribe(restaurants -> restaurantsLiveData.setValue(restaurants)));

        return restaurantsLiveData;
    }
}
