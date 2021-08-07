package com.android.udacity_foodquest.ui.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.common.BaseOnlyActivity;
import com.android.udacity_foodquest.databinding.ActivitySearchBinding;
import com.android.udacity_foodquest.model.restaurant.search.Restaurant;
import com.android.udacity_foodquest.ui.detail.RestaurantDetailActivity;
import com.android.udacity_foodquest.util.Constants;
import com.android.udacity_foodquest.util.LocationUtils;
import com.android.udacity_foodquest.util.SPUtils;

import javax.inject.Inject;

public class SearchActivity extends BaseOnlyActivity<ActivitySearchBinding, SearchViewModel> implements SearchResultsCallback {

    private SearchResultsAdapter adapter;
    private String query;

    @Inject
    SharedPreferences preferences;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    public Class<SearchViewModel> getViewModel() {
        return SearchViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            query = getIntent().getExtras().getString(Constants.SEARCH_QUERY_KEY);
        }

        setupToolbar(query);

        adapter = new SearchResultsAdapter(this);
        dataBinding.searchRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.searchRecyclerview.setAdapter(adapter);

        int entityId = SPUtils.getIntegerPreference(preferences, Constants.ENTITY_ID, 0);
        String entityType = SPUtils.getStringPreference(preferences, Constants.ENTITY_TYPE);

        viewModel.getRestaurants(query, entityId, entityType).observe(this, restaurants -> {
            dataBinding.setListSize(restaurants.size());
            if (restaurants.size() == 0) {
                dataBinding.noResultsLl.setVisibility(View.VISIBLE);
                dataBinding.searchProgressbar.setVisibility(View.GONE);
            } else if (restaurants.size() > 0){
                dataBinding.noResultsLl.setVisibility(View.GONE);
                dataBinding.searchProgressbar.setVisibility(View.GONE);
            }
            adapter.submitList(restaurants);
        });
    }

    @Override
    public void onRestaurantClick(Restaurant restaurant, ImageView sharedElement) {
        Intent intent = new Intent(SearchActivity.this, RestaurantDetailActivity.class);
        intent.putExtra(Constants.RESTAURANTS_BUNDLE_KEY, restaurant);
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElement, getString(R.string.shared_element_transition_name)).toBundle();
        startActivity(intent, options);
    }

    @Override
    public void onRestaurantMarkerClick(Restaurant restaurant) {
        LocationUtils.openGoogleMaps(this, Double.parseDouble(restaurant.getRestaurant().getLocation().getLatitude()), Double.parseDouble(restaurant.getRestaurant().getLocation().getLongitude()));
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

    private void setupToolbar(String title) {
        setSupportActionBar(dataBinding.searchToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);
    }

}
