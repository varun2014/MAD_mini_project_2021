package com.android.udacity_foodquest.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.udacity_foodquest.data.local.dao.FavRestaurantDao;
import com.android.udacity_foodquest.data.local.entity.CommonRestaurant;

@Database(entities = {CommonRestaurant.class}, version = 1, exportSchema = false)
public abstract class FoodQuestDatabase extends RoomDatabase {

    public abstract FavRestaurantDao favRestaurantDao();
}
