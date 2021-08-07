package com.android.udacity_foodquest.ui.establishments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.common.BaseFragment;
import com.android.udacity_foodquest.databinding.FragmentEstablishmentListBinding;
import com.android.udacity_foodquest.model.establishments.Establishment;
import com.android.udacity_foodquest.model.restaurant.search.Restaurant;
import com.android.udacity_foodquest.ui.detail.RestaurantDetailActivity;
import com.android.udacity_foodquest.util.Constants;
import com.android.udacity_foodquest.util.LocationUtils;
import com.android.udacity_foodquest.util.SPUtils;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstablishmentListFragment extends BaseFragment<EstablishmentsViewModel, FragmentEstablishmentListBinding>
        implements EstablishmentCallback.RestaurantCallback {

    private static final String ESTABLISHMENT_ID_KEY = "establishment_id";

    private EstablishmentListAdapter adapter;

    @Inject
    SharedPreferences preferences;

    public static EstablishmentListFragment newInstance(Establishment establishment) {

        Bundle args = new Bundle();

        args.putInt(ESTABLISHMENT_ID_KEY, establishment.getEstablishment().getId());
        EstablishmentListFragment fragment = new EstablishmentListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public EstablishmentListFragment() {
        // Required empty public constructor
    }

    @Override
    public Class<EstablishmentsViewModel> getViewModel() {
        return EstablishmentsViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_establishment_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        adapter = new EstablishmentListAdapter(this);
        dataBinding.establishmentListRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        dataBinding.establishmentListRecyclerview.setAdapter(adapter);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int establishmentId = getArguments().getInt(ESTABLISHMENT_ID_KEY);
        int entityId = SPUtils.getIntegerPreference(preferences, Constants.ENTITY_ID, 0);
        String entityType = SPUtils.getStringPreference(preferences, Constants.ENTITY_TYPE);

        viewModel.getEstablishmentList(String.valueOf(establishmentId), entityId, entityType).observe(this, restaurants -> {
            dataBinding.setListSize(restaurants.size());
            adapter.submitList(restaurants);
        });
    }

    @Override
    public void onEstablishmentClick(Restaurant restaurant, ImageView sharedElement) {
        Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
        intent.putExtra(Constants.RESTAURANTS_BUNDLE_KEY, restaurant);
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), sharedElement, getString(R.string.shared_element_transition_name)).toBundle();
        startActivity(intent, options);
    }

    @Override
    public void onEstablishmentMarkerClick(Restaurant restaurant) {
        LocationUtils.openGoogleMaps(getActivity(), Double.parseDouble(restaurant.getRestaurant().getLocation().getLatitude()), Double.parseDouble(restaurant.getRestaurant().getLocation().getLongitude()));
    }
}
