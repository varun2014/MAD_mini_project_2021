package com.android.udacity_foodquest.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.udacity_foodquest.common.BaseViewModel;
import com.android.udacity_foodquest.model.cuisines.Cuisine;
import com.android.udacity_foodquest.model.cuisines.CuisinesResponse;
import com.android.udacity_foodquest.model.geocode.GeocodeResponse;
import com.android.udacity_foodquest.model.geocode.Location;
import com.android.udacity_foodquest.model.geocode.NearbyRestaurant;
import com.android.udacity_foodquest.model.locations.LocationSuggestion;
import com.android.udacity_foodquest.model.locations.LocationsResponse;
import com.android.udacity_foodquest.repository.MainActivityRepository;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends BaseViewModel {

    private MainActivityRepository repository;

    private MutableLiveData<List<Cuisine>> cuisinesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<NearbyRestaurant>> nearbyRestaurantsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<LocationSuggestion>> locationsLiveData = new MutableLiveData<>();
    private MutableLiveData<Location> locationByLatLonLive = new MutableLiveData<>();

    @Inject
    public MainViewModel(MainActivityRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Cuisine>> getCuisines(int cityId, Double lat, Double lon, Integer takeCount) {
        if (takeCount != null) {
            disposable.add(repository.getCuisines(cityId, lat, lon)
                    .map(CuisinesResponse::getCuisines)
                    .flatMapIterable(response -> response)
                    .take(takeCount)
                    .toList()
                    .subscribe(response -> cuisinesLiveData.setValue(response)));
        } else {
            disposable.add(
                    repository.getCuisines(cityId, lat, lon)
                            .map(CuisinesResponse::getCuisines)
                            .subscribe(response -> cuisinesLiveData.setValue(response)));
        }

        return cuisinesLiveData;
    }

    public LiveData<List<NearbyRestaurant>> getNearbyRestaurants(Double lat, Double lon, Integer takeCount) {
        if (takeCount != null) {
            disposable.add(repository.getNearbyRestaurants(lat, lon)
                    .map(GeocodeResponse::getNearbyRestaurants)
                    .flatMapIterable(response -> response)
                    .take(takeCount)
                    .toList()
                    .subscribe(response -> nearbyRestaurantsLiveData.setValue(response)));
        } else {
            disposable.add(repository.getNearbyRestaurants(lat, lon)
                    .map(GeocodeResponse::getNearbyRestaurants)
                    .subscribe(response -> nearbyRestaurantsLiveData.setValue(response)));
        }

        return nearbyRestaurantsLiveData;
    }

    public LiveData<List<LocationSuggestion>> getLocationDatas(String query) {
        disposable.add(repository.getLocationDatas(query)
                .map(LocationsResponse::getLocationSuggestions)
                .flatMapIterable(response -> response)
                .toList()
                .subscribe(response -> locationsLiveData.setValue(response)));
        return locationsLiveData;
    }

    public LiveData<Location> getLocationDatasByLatLon(Double lat, Double lon) {
        disposable.add(repository.getLocationDatasByLatLon(lat, lon)
                .map(GeocodeResponse::getLocation)
                .subscribe(location -> locationByLatLonLive.setValue(location)));

        return locationByLatLonLive;
    }
}
