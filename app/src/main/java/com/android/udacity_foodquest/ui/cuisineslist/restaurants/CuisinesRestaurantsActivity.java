package com.android.udacity_foodquest.ui.cuisineslist.restaurants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.ImageView;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.common.BaseOnlyActivity;
import com.android.udacity_foodquest.databinding.ActivityCuisinesRestaurantsBinding;
import com.android.udacity_foodquest.model.cuisines.Cuisine;
import com.android.udacity_foodquest.model.restaurant.search.Restaurant;
import com.android.udacity_foodquest.ui.detail.RestaurantDetailActivity;
import com.android.udacity_foodquest.util.Constants;
import com.android.udacity_foodquest.util.LocationUtils;
import com.android.udacity_foodquest.util.SPUtils;

import javax.inject.Inject;

import timber.log.Timber;

public class CuisinesRestaurantsActivity extends BaseOnlyActivity<ActivityCuisinesRestaurantsBinding, CuisinesRestaurantsViewModel>
implements CuisinesRestaurantsCallback {

    private CuisinesRestaurantsAdapter adapter;
    private String cuisineId;
    private Cuisine cuisine;

    @Inject
    SharedPreferences preferences;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_cuisines_restaurants;
    }

    @Override
    public Class<CuisinesRestaurantsViewModel> getViewModel() {
        return CuisinesRestaurantsViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            cuisine = (Cuisine) getIntent().getExtras().getSerializable(Constants.CUISINES_BUNDLE_KEY);
            cuisineId = String.valueOf(cuisine.getCuisine().getCuisineId());
        }

        setupToolbar(cuisine);

        adapter = new CuisinesRestaurantsAdapter(this);
        dataBinding.cuisinesRestaurantRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.cuisinesRestaurantRecyclerview.setAdapter(adapter);

        int entityId = SPUtils.getIntegerPreference(preferences, Constants.ENTITY_ID, 0);
        String entityType = SPUtils.getStringPreference(preferences, Constants.ENTITY_TYPE);

        viewModel.getRestaurants(entityId, entityType, cuisineId).observe(this, restaurants -> {
            dataBinding.setListSize(restaurants.size());
            adapter.submitList(restaurants);

            Timber.e(String.valueOf(restaurants.size()));
        });
    }

    @Override
    public void onRestaurantClick(Restaurant restaurant, ImageView sharedElement) {
        Intent i = new Intent(CuisinesRestaurantsActivity.this, RestaurantDetailActivity.class);
        i.putExtra(Constants.RESTAURANTS_BUNDLE_KEY, restaurant);
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElement, getString(R.string.shared_element_transition_name)).toBundle();
        startActivity(i, options);
    }

    @Override
    public void onRestaurantMarkerClick(Restaurant restaurant) {
        LocationUtils.openGoogleMaps(this, Double.parseDouble(restaurant.getRestaurant().getLocation().getLatitude()), Double.parseDouble(restaurant.getRestaurant().getLocation().getLongitude()));
    }

    private void setupToolbar(Cuisine cuisine) {
        setSupportActionBar(dataBinding.cuisinesRestaurantToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(cuisine.getCuisine().getCuisineName());
    }
}
