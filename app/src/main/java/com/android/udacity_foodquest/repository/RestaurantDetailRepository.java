package com.android.udacity_foodquest.repository;

import androidx.lifecycle.LiveData;

import com.android.udacity_foodquest.data.local.dao.FavRestaurantDao;
import com.android.udacity_foodquest.data.local.entity.CommonRestaurant;
import com.android.udacity_foodquest.data.remote.ApiService;
import com.android.udacity_foodquest.model.restaurant.reviews.ReviewsResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RestaurantDetailRepository {

    private ApiService apiService;
    private FavRestaurantDao favDao;

    @Inject
    public RestaurantDetailRepository(ApiService apiService, FavRestaurantDao favDao) {
        this.apiService = apiService;
        this.favDao = favDao;
    }

    public Flowable<ReviewsResponse> getReviews(int restauntId) {
        return apiService.getReviews(restauntId)
                .onErrorResumeNext(t -> { Timber.e(String.valueOf(t)); })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable insertFavRestaurant(CommonRestaurant restaurant) {
        return Completable.fromAction(() -> favDao.insertFavorite(restaurant))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteFavRestaurant(CommonRestaurant restaurant) {
        return Completable.fromAction(() -> favDao.deleteFavRestaurant(restaurant))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<CommonRestaurant> isFavorite(String id) {
        return favDao.getSingleRestaurant(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public LiveData<List<CommonRestaurant>> getAllFavRestaurants() {
        return favDao.getAllFavorites();
    }

}
