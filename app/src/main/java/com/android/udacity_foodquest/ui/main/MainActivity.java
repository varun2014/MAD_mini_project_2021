package com.android.udacity_foodquest.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.common.BaseOnlyActivity;
import com.android.udacity_foodquest.databinding.ActivityMainBinding;
import com.android.udacity_foodquest.model.cuisines.Cuisine;
import com.android.udacity_foodquest.model.geocode.NearbyRestaurant;
import com.android.udacity_foodquest.model.locations.LocationSuggestion;
import com.android.udacity_foodquest.ui.cuisineslist.CuisinesListActivity;
import com.android.udacity_foodquest.ui.cuisineslist.restaurants.CuisinesRestaurantsActivity;
import com.android.udacity_foodquest.ui.detail.RestaurantDetailActivity;
import com.android.udacity_foodquest.ui.establishments.EstablishmentsActivity;
import com.android.udacity_foodquest.ui.favorites.FavoritesActivity;
import com.android.udacity_foodquest.ui.nearbyrestaurants.NearbyRestaurantsActivity;
import com.android.udacity_foodquest.ui.search.SearchActivity;
import com.android.udacity_foodquest.util.Constants;
import com.android.udacity_foodquest.util.EntityType;
import com.android.udacity_foodquest.util.GPSUtils;
import com.android.udacity_foodquest.util.LocationUtils;
import com.android.udacity_foodquest.util.SPUtils;
import com.android.udacity_foodquest.widget.AppWidgetHelper;

import java.util.List;

import javax.inject.Inject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

public class MainActivity extends BaseOnlyActivity<ActivityMainBinding, MainViewModel>
        implements NavigationView.OnNavigationItemSelectedListener, CuisinesCallback, NearbyRestaurantsMainCallback,
        FoodQuestLocationCallback, EasyPermissions.PermissionCallbacks, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int TAKEN_CUISINES = 10;
    private static final int TAKEN_NEARBY_RESTAURANTS = 5;
    private static final int LOCATION_PERM_CODE = 101;

    private CuisinesRecyclerAdapter cuisinesAdapter;
    private NearbyRestaurantsMainAdapter nearbyAdapter;

    private ProgressDialog progressDialog;

    @Inject
    SharedPreferences preferences;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();
        navViewConfig();

        dataBinding.setLifecycleOwner(this);
        AppWidgetHelper.updateAppWidget(this);
        preferences.registerOnSharedPreferenceChangeListener(this);

        cuisinesAdapter = new CuisinesRecyclerAdapter(this);
        dataBinding.cuisinesRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dataBinding.cuisinesRecyclerview.setAdapter(cuisinesAdapter);

        nearbyAdapter = new NearbyRestaurantsMainAdapter(this);
        dataBinding.nearbyRestaurantsMainRecyclerview.setAdapter(nearbyAdapter);

        // initial loading
        setPrefValuesForInitial();
        int cityId = SPUtils.getIntegerPreference(preferences, Constants.CITY_ID, 0);
        double lat = SPUtils.getDoublePreference(preferences, Constants.LATITUDE, 0.0);
        double lon = SPUtils.getDoublePreference(preferences, Constants.LONGITUDE, 0.0);

        // first loading
        getCuisines(cityId);
        getNearbyRestaurants(lat, lon);

        dataBinding.viewAllCuisine.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CuisinesListActivity.class)));
        dataBinding.viewAllNearbyRestaurants.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NearbyRestaurantsActivity.class)));

        dataBinding.searchView.setOnClickListener(v -> dataBinding.searchView.setIconified(false));
        dataBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(Constants.SEARCH_QUERY_KEY, query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getNearbyRestaurants(double lat, double lon) {
        viewModel.getNearbyRestaurants(lat, lon, TAKEN_NEARBY_RESTAURANTS).observe(this, response -> {
            dataBinding.setRestaurantSize(response.size());
            nearbyAdapter.submitList(response);
        });
    }

    private void getCuisines(int cityId) {
        viewModel.getCuisines(cityId, null, null, TAKEN_CUISINES).observe(this, response -> {
            dataBinding.setCuisineSize(response.size());
            cuisinesAdapter.submitList(response);
        });
    }

    private void setPrefValuesForInitial() {
        // These initial values are for Bellevue, WA, USA
        int cityId = SPUtils.getIntegerPreference(preferences, Constants.CITY_ID, 0);
        if (cityId == 0) {
            SPUtils.setStringPreference(preferences, Constants.ENTITY_TYPE, EntityType.CITY.getType());
            SPUtils.setIntegerPreference(preferences, Constants.ENTITY_ID, 10659);
            SPUtils.setDoublePreferences(preferences, Constants.LATITUDE, 47.610378);
            SPUtils.setDoublePreferences(preferences, Constants.LONGITUDE, -122.200676);
            SPUtils.setIntegerPreference(preferences, Constants.CITY_ID, 10659);
            SPUtils.setIntegerPreference(preferences, Constants.COUNTRY_ID, 216);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Constants.CITY_ID)) {
            double lat = SPUtils.getDoublePreference(sharedPreferences, Constants.LATITUDE, 0.0);
            double lon = SPUtils.getDoublePreference(sharedPreferences, Constants.LONGITUDE, 0.0);
            int cityId = SPUtils.getIntegerPreference(preferences, Constants.CITY_ID, 0);

            getNearbyRestaurants(lat, lon);

            getCuisines(cityId);
        }
    }

    @Override
    public void onMainCuisineClick(Cuisine cuisine) {
        Intent intent = new Intent(MainActivity.this, CuisinesRestaurantsActivity.class);
        intent.putExtra(Constants.CUISINES_BUNDLE_KEY, cuisine);
        startActivity(intent);
    }

    @Override
    public void onMainNearbyRestaurantsClick(NearbyRestaurant nearbyRestaurant, ImageView sharedElement) {
        Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
        intent.putExtra(Constants.RESTAURANTS_BUNDLE_KEY, nearbyRestaurant);
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElement, getString(R.string.shared_element_transition_name)).toBundle();
        startActivity(intent, options);
    }

    @Override
    public void onMainNearbyRestaurantMarkerClick(NearbyRestaurant nearbyRestaurant) {
        LocationUtils.openGoogleMaps(this, Double.parseDouble(nearbyRestaurant.getRestaurant().getLocation().getLatitude()), Double.parseDouble(nearbyRestaurant.getRestaurant().getLocation().getLongitude()));
    }

    @Override
    public void onCurrentLocationClick() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            currentLocationConfig();
        } else {
            if (!GPSUtils.isGpsEnabled(this)) {
                gpsSettings();
            } else {
                getLastLocation();
            }
        }
    }

    @Override
    public void onSaveLocationClick(String value) {
        getprogressDialog(getString(R.string.validating));
        viewModel.getLocationDatas(value).observe(this, locationSuggestions -> {

            if (locationSuggestions.size() > 0) {
                LocationSuggestion ls = locationSuggestions.get(0);
                String entitType = ls.getEntityType();
                int entityId = ls.getEntityId();
                double latitude = ls.getLatitude();
                double longitude = ls.getLongitude();
                int cityId = ls.getCityId();
                int countryId = ls.getCountryId();

                SPUtils.setStringPreference(preferences, Constants.ENTITY_TYPE, entitType);
                SPUtils.setIntegerPreference(preferences, Constants.ENTITY_ID, entityId);
                SPUtils.setDoublePreferences(preferences, Constants.LATITUDE, latitude);
                SPUtils.setDoublePreferences(preferences, Constants.LONGITUDE, longitude);
                SPUtils.setIntegerPreference(preferences, Constants.CITY_ID, cityId);
                SPUtils.setIntegerPreference(preferences, Constants.COUNTRY_ID, countryId);

                Toast.makeText(this, getString(R.string.location_saved) + " - " + ls.getTitle(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.please_enter_valid_city_name), Toast.LENGTH_SHORT).show();
            }
            viewModel.getLocationDatas(value).removeObservers(this);
            progressDialog.dismiss();
        });
    }

    @AfterPermissionGranted(LOCATION_PERM_CODE)
    private void currentLocationConfig() {
        boolean hasLocationPermission = EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasLocationPermission) {
            if (!GPSUtils.isGpsEnabled(this)) {
                gpsSettings();
            } else {
                getLastLocation();
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.location_permission_warning),
                    LOCATION_PERM_CODE, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void gpsSettings() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.enable_gps))
                .setMessage(getString(R.string.require_gps_message))
                .setPositiveButton(getString(android.R.string.yes), (dialog, which) ->
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton(android.R.string.no, (dialog, which) -> {  /* do nothing */ })
                .show();
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        getprogressDialog(getString(R.string.finding_your_location)).show();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Timber.e("lat " + location.getLatitude() + "lon " + location.getLongitude());

                SPUtils.setDoublePreferences(preferences, Constants.LATITUDE, location.getLatitude());
                SPUtils.setDoublePreferences(preferences, Constants.LONGITUDE, location.getLongitude());

                viewModel.getLocationDatasByLatLon(location.getLatitude(), location.getLongitude()).observe(MainActivity.this, locationResponse -> {
                    SPUtils.setStringPreference(preferences, Constants.ENTITY_TYPE, locationResponse.getEntityType());
                    SPUtils.setIntegerPreference(preferences, Constants.ENTITY_ID, locationResponse.getEntityId());
                    SPUtils.setIntegerPreference(preferences, Constants.CITY_ID, locationResponse.getCityId());
                    SPUtils.setIntegerPreference(preferences, Constants.COUNTRY_ID, locationResponse.getCountryId());

                    progressDialog.dismiss();
                });
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        }, null);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Timber.e("onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Timber.e("onPermissionsDenied:" + requestCode + ":" + perms.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dataBinding.mainDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_action_my_location:
                showLocationBottomSheet();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_cuisines:
                startActivity(new Intent(MainActivity.this, CuisinesListActivity.class));
                break;
            case R.id.nav_establishments:
                startActivity(new Intent(MainActivity.this, EstablishmentsActivity.class));
                break;
            case R.id.nav_nearby_restaurants:
                startActivity(new Intent(MainActivity.this, NearbyRestaurantsActivity.class));
                break;
            case R.id.nav_fav_restaurants:
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
        }
        dataBinding.mainDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (dataBinding.mainDrawer.isDrawerOpen(GravityCompat.START)) {
            dataBinding.mainDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private ProgressDialog getprogressDialog(String message) {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle(getString(R.string.please_wait));
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    private void setupToolbar() {
        setSupportActionBar(dataBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void navViewConfig() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dataBinding.mainDrawer, dataBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dataBinding.mainDrawer.setDrawerListener(toggle);
        toggle.syncState();

        dataBinding.mainNavView.setNavigationItemSelectedListener(this);
    }

    private void showLocationBottomSheet() {
        LocationBottomSheetFragment bottomSheetFragment = new LocationBottomSheetFragment(this);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
}
