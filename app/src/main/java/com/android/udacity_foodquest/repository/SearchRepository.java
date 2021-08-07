package com.android.udacity_foodquest.repository;

import com.android.udacity_foodquest.data.remote.ApiService;
import com.android.udacity_foodquest.model.restaurant.search.SearchResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SearchRepository {

    private ApiService apiService;

    @Inject
    public SearchRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Flowable<SearchResponse> getSearchResults(String query, int entityId, String entityType) {
        return apiService.getSearchDatas(query, null, entityId, entityType, null, null, null)
                .onErrorResumeNext(t -> { Timber.e(String.valueOf(t)); })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
