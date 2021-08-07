package com.android.udacity_foodquest.ui.detail;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.common.BaseFragment;
import com.android.udacity_foodquest.databinding.FragmentRestaurantReviewsBinding;
import com.android.udacity_foodquest.data.local.entity.CommonRestaurant;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantReviewsFragment extends BaseFragment<RestaurantDetailViewModel, FragmentRestaurantReviewsBinding> {

    private static final String REVIEWS_KEY = "reviews_key";

    private ReviewsAdapter adapter;

    public RestaurantReviewsFragment() {
        // Required empty public constructor
    }

    public static RestaurantReviewsFragment newInstance(CommonRestaurant commonRestaurant) {

        Bundle args = new Bundle();
        args.putSerializable(REVIEWS_KEY, commonRestaurant);

        RestaurantReviewsFragment fragment = new RestaurantReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Class<RestaurantDetailViewModel> getViewModel() {
        return RestaurantDetailViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_restaurant_reviews;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        adapter = new ReviewsAdapter();
        dataBinding.reviewsRecyclerview.setAdapter(adapter);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CommonRestaurant restaurant = (CommonRestaurant) getArguments().getSerializable(REVIEWS_KEY);
        int restaurantId = Integer.parseInt(restaurant.getId());

        Timber.e("res id = %s", restaurant.getId());

        viewModel.getReviews(restaurantId).observe(this, userReviews -> {
            dataBinding.setListSize(userReviews.size());
            adapter.submitList(userReviews);
        });
    }
}
