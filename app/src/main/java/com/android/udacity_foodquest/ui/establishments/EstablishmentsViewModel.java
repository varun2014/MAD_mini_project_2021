package com.android.udacity_foodquest.ui.establishments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.udacity_foodquest.common.BaseViewModel;
import com.android.udacity_foodquest.model.establishments.Establishment;
import com.android.udacity_foodquest.model.establishments.EstablishmentsResponse;
import com.android.udacity_foodquest.model.restaurant.search.Restaurant;
import com.android.udacity_foodquest.model.restaurant.search.SearchResponse;
import com.android.udacity_foodquest.repository.EstablishmentsRepository;

import java.util.List;

import javax.inject.Inject;

public class EstablishmentsViewModel extends BaseViewModel {

    private EstablishmentsRepository repository;

    private MutableLiveData<List<Establishment>> establishmentsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Restaurant>> restaurantLiveList = new MutableLiveData<>();

    @Inject
    public EstablishmentsViewModel(EstablishmentsRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Establishment>> getEstablishmentTypes(int cityId, Double lat, Double lon) {
        disposable.add(repository.getEstablishmentTypes(cityId, lat, lon)
                .map(EstablishmentsResponse::getEstablishments)
                .flatMapIterable(response -> response)
                .toList()
                .subscribe(response -> establishmentsLiveData.setValue(response)));

        return establishmentsLiveData;
    }

    public LiveData<List<Restaurant>> getEstablishmentList(String establishmentId, int entityId, String entityType) {
        disposable.add(repository.getRestaurants(establishmentId, entityId, entityType)
                .map(SearchResponse::getRestaurants)
                .subscribe(restaurants -> restaurantLiveList.setValue(restaurants)));

        return restaurantLiveList;
    }
}
