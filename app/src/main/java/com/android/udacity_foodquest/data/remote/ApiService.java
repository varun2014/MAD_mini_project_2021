package com.android.udacity_foodquest.data.remote;

import com.android.udacity_foodquest.model.categories.CategoryResponse;
import com.android.udacity_foodquest.model.cities.CitiesResponse;
import com.android.udacity_foodquest.model.cuisines.CuisinesResponse;
import com.android.udacity_foodquest.model.establishments.EstablishmentsResponse;
import com.android.udacity_foodquest.model.geocode.GeocodeResponse;
import com.android.udacity_foodquest.model.locationDetails.LocationDetailsResponse;
import com.android.udacity_foodquest.model.locations.LocationsResponse;
import com.android.udacity_foodquest.model.restaurant.detail.RestaurantDetailResponse;
import com.android.udacity_foodquest.model.restaurant.reviews.ReviewsResponse;
import com.android.udacity_foodquest.model.restaurant.search.SearchResponse;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // categories
    @GET("categories")
    Single<CategoryResponse> getCategories();

    // cities
    @GET("cities")
    Single<CitiesResponse> getCities(@Query("q") String cityName,
                                     @Query("lat") Double lat,
                                     @Query("lon") Double lon);

    // cuisines
    @GET("cuisines")
    Flowable<CuisinesResponse> getCuisines(@Query("city_id") int cityId,
                                           @Query("lat") Double lat,
                                           @Query("lon") Double lon);

    // establishments
    @GET("establishments")
    Flowable<EstablishmentsResponse> getEstablishments(@Query("city_id") int cityId,
                                                     @Query("lat") Double lat,
                                                     @Query("lon") Double lon);

    // geocode
    @GET("geocode")
    Flowable<GeocodeResponse> getGeoCode(@Query("lat") Double lat,
                                         @Query("lon") Double lon);

    // location_details
    @GET("location_details")
    Single<LocationDetailsResponse> getLocationDetails(@Query("entity_id") int entityId,
                                                       @Query("entity_type") String entityType);

    // locations
    @GET("locations")
    Flowable<LocationsResponse> getLocations(@Query("query") String query);

    // restaurant details
    @GET("restaurant")
    Single<RestaurantDetailResponse> getRestaurantDetails(@Query("res_id") int restaurantId);

    // reviews
    @GET("reviews")
    Flowable<ReviewsResponse> getReviews(@Query("res_id") int restaurantId);

    // search
    @GET("search")
    Flowable<SearchResponse> getSearchDatas(@Query("q") String query,
                                            @Query("establishment_type") String establishmentType,
                                            @Query("entity_id") Integer entityId,
                                            @Query("entity_type") String entityType,
                                            @Query("lat") Double lat,
                                            @Query("lon") Double lon,
                                            @Query("cuisines") String cuisinesId);
}
