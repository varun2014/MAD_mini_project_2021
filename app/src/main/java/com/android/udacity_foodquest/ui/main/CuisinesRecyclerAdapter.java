package com.android.udacity_foodquest.ui.main;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.udacity_foodquest.databinding.CuisinesMainItemBinding;
import com.android.udacity_foodquest.model.cuisines.Cuisine;

public class CuisinesRecyclerAdapter extends ListAdapter<Cuisine, CuisinesRecyclerAdapter.ViewHolder> {

    private final CuisinesCallback callback;

    public CuisinesRecyclerAdapter(CuisinesCallback callback) {
        super(CUISINES_DIFF_CALLBACK);
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(LayoutInflater.from(parent.getContext()), parent, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public static ViewHolder create(LayoutInflater inflater, ViewGroup parent, CuisinesCallback callback) {
            CuisinesMainItemBinding cuisinesMainItemBinding = CuisinesMainItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(cuisinesMainItemBinding, callback);
        }

        CuisinesMainItemBinding binding;

        public ViewHolder(CuisinesMainItemBinding binding, CuisinesCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onMainCuisineClick(binding.getCuisine()));
        }

        public void bind(Cuisine cuisine) {
            binding.setCuisine(cuisine);
            binding.executePendingBindings();
        }
    }

    private static DiffUtil.ItemCallback<Cuisine> CUISINES_DIFF_CALLBACK = new DiffUtil.ItemCallback<Cuisine>() {
        @Override
        public boolean areItemsTheSame(Cuisine oldItem, Cuisine newItem) {
            return oldItem.getCuisine().getCuisineId() == newItem.getCuisine().getCuisineId();
        }

        @Override
        public boolean areContentsTheSame(Cuisine oldItem, Cuisine newItem) {
            return oldItem.getCuisine().getCuisineName() == oldItem.getCuisine().getCuisineName();
        }
    };
}
