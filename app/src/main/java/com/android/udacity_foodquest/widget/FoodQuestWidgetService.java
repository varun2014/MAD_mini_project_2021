package com.android.udacity_foodquest.widget;

import androidx.room.Room;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.data.local.FoodQuestDatabase;
import com.android.udacity_foodquest.data.local.dao.FavRestaurantDao;
import com.android.udacity_foodquest.data.local.entity.CommonRestaurant;
import com.android.udacity_foodquest.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class FoodQuestWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FoodQuestRemoteViewFactory(getApplicationContext());
    }

    public class FoodQuestRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

        FavRestaurantDao dao;
        AppExecutors executors = new AppExecutors();

        private Context context;
        private List<CommonRestaurant> favoritesList = new ArrayList();

        public FoodQuestRemoteViewFactory(Context context) {
            this.context = context;
            dao = Room.databaseBuilder(context, FoodQuestDatabase.class, "foodquest.db").build().favRestaurantDao();
        }

        @Override
        public void onCreate() {
            executors.diskIO().execute(() -> favoritesList = dao.getAllFavsForWidget());
        }

        @Override
        public void onDataSetChanged() {
            executors.diskIO().execute(() -> favoritesList = dao.getAllFavsForWidget());
        }

        @Override
        public void onDestroy() {
            dao = null;
        }

        @Override
        public int getCount() {
            return (favoritesList != null) ? favoritesList.size() : 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            String restaurantName = favoritesList.get(i).getName();
            String address = favoritesList.get(i).getAddress();
            String imgUrl = favoritesList.get(i).getFeaturedImage();

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.foodquest_app_widget_item);
            remoteViews.setTextViewText(R.id.widget_name, restaurantName);
            remoteViews.setTextViewText(R.id.widget_short_adress, address);

            Intent fillIntent = new Intent();
            remoteViews.setOnClickFillInIntent(R.id.widget_wrapper, fillIntent);

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
