package com.android.udacity_foodquest.ui.establishments;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.common.BaseActivity;
import com.android.udacity_foodquest.databinding.ActivityEstablishmentsBinding;

public class EstablishmentsActivity extends BaseActivity<ActivityEstablishmentsBinding> {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_establishments;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();

        if (dataBinding.establishmentsContainer != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.establishments_container, EstablishmentTypesListFragment.newInstance())
                    .commit();
        }
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
        setSupportActionBar(dataBinding.establishmentsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.establishments));
    }
}
