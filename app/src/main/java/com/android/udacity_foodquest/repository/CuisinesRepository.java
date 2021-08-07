package com.android.udacity_foodquest.repository;

import com.android.udacity_foodquest.data.remote.ApiService;
import com.android.udacity_foodquest.model.restaurant.search.SearchResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CuisinesRepository {

    private ApiService apiService;

    @Inject
    public CuisinesRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Flowable<SearchResponse> getRestaurants(int entityId, String entityType, String cuisineId) {
        return apiService.getSearchDatas(null, null, entityId,
                entityType, null, null, cuisineId)
                .onErrorResumeNext(t -> { Timber.e(String.valueOf(t)); })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
