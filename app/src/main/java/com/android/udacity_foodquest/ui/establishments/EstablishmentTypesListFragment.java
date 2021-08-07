package com.android.udacity_foodquest.ui.establishments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.udacity_foodquest.R;
import com.android.udacity_foodquest.common.BaseFragment;
import com.android.udacity_foodquest.databinding.FragmentEstablishmentTypesListBinding;
import com.android.udacity_foodquest.model.establishments.Establishment;
import com.android.udacity_foodquest.util.Constants;
import com.android.udacity_foodquest.util.SPUtils;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstablishmentTypesListFragment extends BaseFragment<EstablishmentsViewModel, FragmentEstablishmentTypesListBinding>
        implements EstablishmentCallback.TypesCalback {

    private EstablishmentTypesAdapter adapter;

    @Inject
    SharedPreferences preferences;

    public static EstablishmentTypesListFragment newInstance() {

        Bundle args = new Bundle();

        EstablishmentTypesListFragment fragment = new EstablishmentTypesListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EstablishmentTypesListFragment() {
        // Required empty public constructor
    }

    @Override
    public Class<EstablishmentsViewModel> getViewModel() {
        return EstablishmentsViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_establishment_types_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        adapter = new EstablishmentTypesAdapter(new EstablishmentTypesAdapter.EstablishmentTypesDiffCallback(), this);
        dataBinding.establishmentTypesRecyclerview.setAdapter(adapter);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int cityId = SPUtils.getIntegerPreference(preferences, Constants.CITY_ID, 0);
        viewModel.getEstablishmentTypes(cityId, null, null).observe(this, establishments -> {
            dataBinding.setListSize(establishments.size());
            adapter.submitList(establishments);
        });
    }

    @Override
    public void onEstablishmentTypesClick(Establishment establishment) {
        ((EstablishmentsActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.establishments_container, EstablishmentListFragment.newInstance(establishment))
                .addToBackStack(null)
                .commit();
    }
}
