package com.android.udacity_foodquest.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.android.udacity_foodquest.data.local.entity.CommonRestaurant;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FavRestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertFavorite(CommonRestaurant restaurant);

    @Delete
    abstract void deleteFavRestaurant(CommonRestaurant commonRestaurant);

    @Query("SELECT * FROM favrestaurant")
    abstract LiveData<List<CommonRestaurant>> getAllFavorites();

    @Query("SELECT * FROM favrestaurant")
    abstract List<CommonRestaurant> getAllFavsForWidget();

    @Query("SELECT * FROM favrestaurant WHERE id = :id")
    abstract Flowable<CommonRestaurant> getSingleRestaurant(String id);
}
