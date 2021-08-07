package com.android.udacity_foodquest.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.udacity_foodquest.common.BaseViewModel;
import com.android.udacity_foodquest.data.local.entity.CommonRestaurant;
import com.android.udacity_foodquest.model.restaurant.reviews.ReviewsResponse;
import com.android.udacity_foodquest.model.restaurant.reviews.UserReview;
import com.android.udacity_foodquest.repository.RestaurantDetailRepository;

import java.util.List;

import javax.inject.Inject;

public class RestaurantDetailViewModel extends BaseViewModel {

    private RestaurantDetailRepository repository;

    private MutableLiveData<List<UserReview>> reviewsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CommonRestaurant>> favRestaurantLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFavLiveData = new MutableLiveData<>();

    @Inject
    public RestaurantDetailViewModel(RestaurantDetailRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<UserReview>> getReviews(int restaurantId) {
        disposable.add(repository.getReviews(restaurantId)
                .map(ReviewsResponse::getUserReviews)
                .flatMapIterable(response -> response)
                .toList()
                .subscribe(response -> reviewsLiveData.postValue(response)));
        return reviewsLiveData;
    }

    public void addRestaurantToFavorite(CommonRestaurant restaurant) {
        disposable.add(repository.insertFavRestaurant(restaurant)
                .subscribe());
    }

    public void deleteRestaurantFromFavorites(CommonRestaurant restaurant) {
        disposable.add(repository.deleteFavRestaurant(restaurant)
                .subscribe());
    }

    public LiveData<Boolean> isFav(String id) {
        disposable.add(repository.isFavorite(id)
                .subscribe(restaurant -> {
                    if (restaurant != null) {
                        if (restaurant.getId().equals(id)) isFavLiveData.postValue(true);
                        else isFavLiveData.postValue(false);
                    } else {
                        isFavLiveData.postValue(false);
                    }
                }));
        return isFavLiveData;
    }

    public LiveData<List<CommonRestaurant>> getAllFavRestaurants() {
        return repository.getAllFavRestaurants();
    }
}
