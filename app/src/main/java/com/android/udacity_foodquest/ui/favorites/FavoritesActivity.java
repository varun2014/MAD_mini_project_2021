package com.android.udacity_foodquest.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.common.BaseOnlyActivity;
import com.android.udacity_foodquest.data.local.entity.CommonRestaurant;
import com.android.udacity_foodquest.databinding.ActivityFavoritesBinding;
import com.android.udacity_foodquest.ui.detail.RestaurantDetailActivity;
import com.android.udacity_foodquest.ui.detail.RestaurantDetailViewModel;
import com.android.udacity_foodquest.util.Constants;
import com.android.udacity_foodquest.util.LocationUtils;
import com.android.udacity_foodquest.widget.AppWidgetHelper;

public class FavoritesActivity extends BaseOnlyActivity<ActivityFavoritesBinding, RestaurantDetailViewModel> implements FavoritesCallback {

    private FavoritesAdapter adapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_favorites;
    }

    @Override
    public Class<RestaurantDetailViewModel> getViewModel() {
        return RestaurantDetailViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();

        AppWidgetHelper.updateAppWidget(this);

        adapter = new FavoritesAdapter(this);
        dataBinding.favoritesRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.favoritesRecyclerview.setAdapter(adapter);

        viewModel.getAllFavRestaurants().observe(this, commonRestaurants -> {
            dataBinding.setListSize(commonRestaurants.size());
            adapter.submitList(commonRestaurants);
        });
    }

    @Override
    public void onFavoriteRestaurantClick(CommonRestaurant restaurant, ImageView sharedElement) {
        Intent i = new Intent(FavoritesActivity.this, RestaurantDetailActivity.class);
        i.putExtra(Constants.RESTAURANTS_BUNDLE_KEY, restaurant);
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElement, getString(R.string.shared_element_transition_name)).toBundle();
        startActivity(i, options);
    }

    @Override
    public void onFavoriteRestaurantMarkerClick(CommonRestaurant restaurant) {
        LocationUtils.openGoogleMaps(this, Double.parseDouble(restaurant.getLatitude()), Double.parseDouble(restaurant.getLongitude()));
    }

    private void setupToolbar() {
        setSupportActionBar(dataBinding.favoritesToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.favorites));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
