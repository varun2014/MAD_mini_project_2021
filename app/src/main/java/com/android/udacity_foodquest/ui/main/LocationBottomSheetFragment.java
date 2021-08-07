package com.android.udacity_foodquest.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.udacity_foodquest.R;

@SuppressLint("ValidFragment")
public class LocationBottomSheetFragment extends BottomSheetDialogFragment {

    private FoodQuestLocationCallback callback;

    public LocationBottomSheetFragment(FoodQuestLocationCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_bottom_sheet, container, false);

        view.findViewById(R.id.get_current_location_btn).setOnClickListener(v -> {
            if (callback != null) callback.onCurrentLocationClick();
        });

        view.findViewById(R.id.any_location_button).setOnClickListener(v -> {
            String editTextValue = ((EditText)(view.findViewById(R.id.any_location_edittext))).getText().toString();
            if (callback != null) callback.onSaveLocationClick(editTextValue);
        });

        return view;
    }
}
