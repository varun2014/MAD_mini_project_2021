package com.android.udacity_foodquest.repository;

import com.android.udacity_foodquest.data.remote.ApiService;
import com.android.udacity_foodquest.model.establishments.EstablishmentsResponse;
import com.android.udacity_foodquest.model.restaurant.search.SearchResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class EstablishmentsRepository {

    private final ApiService apiService;

    @Inject
    public EstablishmentsRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Flowable<EstablishmentsResponse> getEstablishmentTypes(int cityId, Double lat, Double lon) {
        return apiService.getEstablishments(cityId, lat, lon)
                .onErrorResumeNext(t -> { Timber.e(String.valueOf(t)); })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<SearchResponse> getRestaurants(String establishmentId, int entityId, String entityType) {
        return apiService.getSearchDatas(null, establishmentId, entityId, entityType, null, null, null)
                .onErrorResumeNext(t -> { Timber.e(String.valueOf(t)); })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
