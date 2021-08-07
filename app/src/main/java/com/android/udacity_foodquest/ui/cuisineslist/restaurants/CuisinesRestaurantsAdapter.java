package com.android.udacity_foodquest.ui.cuisineslist.restaurants;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.udacity_foodquest.databinding.CuisinesRestaurantItemBinding;
import com.android.udacity_foodquest.model.restaurant.search.Restaurant;

public class CuisinesRestaurantsAdapter extends ListAdapter<Restaurant, CuisinesRestaurantsAdapter.ViewHolder> {

    private CuisinesRestaurantsCallback callback;

    public CuisinesRestaurantsAdapter(CuisinesRestaurantsCallback callback) {
        super(CUISINES_RESTAU_DIFF_CALLBACK);
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

        CuisinesRestaurantItemBinding binding;

        public ViewHolder(CuisinesRestaurantItemBinding binding, CuisinesRestaurantsCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onRestaurantClick(binding.getRestaurant(), binding.cuisinesRestauThumb));
            binding.cuisinesRestauMainMarker.setOnClickListener(v ->
                    callback.onRestaurantMarkerClick(binding.getRestaurant()));
        }

        public static ViewHolder create(LayoutInflater inflater, ViewGroup parent, CuisinesRestaurantsCallback callback) {
            CuisinesRestaurantItemBinding cuisinesRestaurantItemBinding = CuisinesRestaurantItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(cuisinesRestaurantItemBinding, callback);
        }

        public void bind(Restaurant restaurant) {
            binding.setRestaurant(restaurant);
            binding.executePendingBindings();
        }
    }

    private static DiffUtil.ItemCallback<Restaurant> CUISINES_RESTAU_DIFF_CALLBACK = new DiffUtil.ItemCallback<Restaurant>() {

        @Override
        public boolean areItemsTheSame(Restaurant oldItem, Restaurant newItem) {
            return oldItem.getRestaurant().getId().equals(newItem.getRestaurant().getId());
        }

        @Override
        public boolean areContentsTheSame(Restaurant oldItem, Restaurant newItem) {
            return oldItem.getRestaurant().getName().equals(newItem.getRestaurant().getName());
        }
    };
}
