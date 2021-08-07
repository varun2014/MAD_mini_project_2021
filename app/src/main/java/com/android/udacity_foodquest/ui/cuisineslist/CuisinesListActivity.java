package com.android.udacity_foodquest.ui.cuisineslist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.common.BaseOnlyActivity;
import com.android.udacity_foodquest.databinding.ActivityCuisinesListBinding;
import com.android.udacity_foodquest.model.cuisines.Cuisine;
import com.android.udacity_foodquest.ui.cuisineslist.restaurants.CuisinesRestaurantsActivity;
import com.android.udacity_foodquest.ui.main.MainViewModel;
import com.android.udacity_foodquest.util.Constants;
import com.android.udacity_foodquest.util.SPUtils;

import javax.inject.Inject;

public class CuisinesListActivity extends BaseOnlyActivity<ActivityCuisinesListBinding, MainViewModel> implements CuisinesListCallback {

    private CuisinesListRecyclerAdapter adapter;

    @Inject
    SharedPreferences preferences;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_cuisines_list;
    }

    @Override
    public Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();
        
        adapter = new CuisinesListRecyclerAdapter(this);
        dataBinding.cuisinesListRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.cuisinesListRecyclerview.setAdapter(adapter);

        int cityId = SPUtils.getIntegerPreference(preferences, Constants.CITY_ID, 0);
        viewModel.getCuisines(cityId, null, null, null).observe(this, response -> {
            dataBinding.setListSize(response.size());
            adapter.submitList(response);
        });
    }

    @Override
    public void onCouisineClick(Cuisine cuisine) {
        Intent intent = new Intent(CuisinesListActivity.this, CuisinesRestaurantsActivity.class);
        intent.putExtra(Constants.CUISINES_BUNDLE_KEY, cuisine);
        startActivity(intent);
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

    private void setupToolbar() {
        setSupportActionBar(dataBinding.cuisinesListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.cuisines));
    }
}
