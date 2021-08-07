package com.android.udacity_foodquest.ui.nearbyrestaurants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.common.BaseOnlyActivity;
import com.android.udacity_foodquest.databinding.ActivityNearbyRestaurantsBinding;
import com.android.udacity_foodquest.model.geocode.NearbyRestaurant;
import com.android.udacity_foodquest.ui.detail.RestaurantDetailActivity;
import com.android.udacity_foodquest.ui.main.MainViewModel;
import com.android.udacity_foodquest.util.Constants;
import com.android.udacity_foodquest.util.LocationUtils;
import com.android.udacity_foodquest.util.SPUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import javax.inject.Inject;

import timber.log.Timber;

public class NearbyRestaurantsActivity extends BaseOnlyActivity<ActivityNearbyRestaurantsBinding, MainViewModel> implements NearbyRestaurantsCallback {

    private NearbyRestaurantsAdapter adapter;
    private InterstitialAd mInterstitialAd;

    @Inject
    SharedPreferences preferences;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_nearby_restaurants;
    }

    @Override
    public Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadAds();
        setupToolbar();

        adapter = new NearbyRestaurantsAdapter(this);
        dataBinding.nearbyRestaurantsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.nearbyRestaurantsRecyclerview.setAdapter(adapter);

        double lat = SPUtils.getDoublePreference(preferences, Constants.LATITUDE, 0.0);
        double lon = SPUtils.getDoublePreference(preferences, Constants.LONGITUDE, 0.0);

        viewModel.getNearbyRestaurants(lat, lon, null).observe(this, response -> {
            dataBinding.setListSize(response.size());
            adapter.submitList(response);
        });
    }

    @Override
    public void onNearbyRestaurantClick(NearbyRestaurant restaurant, ImageView sharedElement) {
        Intent i = new Intent(NearbyRestaurantsActivity.this, RestaurantDetailActivity.class);
        i.putExtra(Constants.RESTAURANTS_BUNDLE_KEY, restaurant);
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElement, getString(R.string.shared_element_transition_name)).toBundle();
        startActivity(i, options);
    }

    @Override
    public void onNearbyRestaurantMarkerClick(NearbyRestaurant restaurant) {
        LocationUtils.openGoogleMaps(this, Double.parseDouble(restaurant.getRestaurant().getLocation().getLatitude()), Double.parseDouble(restaurant.getRestaurant().getLocation().getLongitude()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Timber.d("The interstitial wasn't loaded yet.");
        }
    }

    private void loadAds() {
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admob_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void setupToolbar() {
        setSupportActionBar(dataBinding.nearbyRestaurantsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.nearby_restaurants));
    }
}
