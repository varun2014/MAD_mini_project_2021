package com.android.udacity_foodquest.ui.main;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.udacity_foodquest.databinding.NearbyRestaurantsMainItemBinding;
import com.android.udacity_foodquest.model.geocode.NearbyRestaurant;

public class NearbyRestaurantsMainAdapter extends ListAdapter<NearbyRestaurant, NearbyRestaurantsMainAdapter.ViewHolder> {

    private final NearbyRestaurantsMainCallback callback;

    protected NearbyRestaurantsMainAdapter(NearbyRestaurantsMainCallback callback) {
        super(NEARBY_MAIN_DIFF_CALLBACK);
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

        NearbyRestaurantsMainItemBinding binding;

        public ViewHolder(NearbyRestaurantsMainItemBinding binding, NearbyRestaurantsMainCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onMainNearbyRestaurantsClick(binding.getRestaurant(), binding.nearbyRestaurantsMainThumb));
            binding.nearbyRestaurantsMainMarker.setOnClickListener(v ->
                    callback.onMainNearbyRestaurantMarkerClick(binding.getRestaurant()));
        }

        public static ViewHolder create(LayoutInflater inflater, ViewGroup parent, NearbyRestaurantsMainCallback callback) {
            NearbyRestaurantsMainItemBinding nearbyRestaurantsMainItemBinding = NearbyRestaurantsMainItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(nearbyRestaurantsMainItemBinding, callback);
        }

        public void bind(NearbyRestaurant restaurant) {
            binding.setRestaurant(restaurant);
            binding.executePendingBindings();
        }
    }

    private static DiffUtil.ItemCallback<NearbyRestaurant> NEARBY_MAIN_DIFF_CALLBACK = new DiffUtil.ItemCallback<NearbyRestaurant>() {
        @Override
        public boolean areItemsTheSame(NearbyRestaurant oldItem, NearbyRestaurant newItem) {
            return oldItem.getRestaurant().getId().equals(newItem.getRestaurant().getId());
        }

        @Override
        public boolean areContentsTheSame(NearbyRestaurant oldItem, NearbyRestaurant newItem) {
            return oldItem.getRestaurant().getName().equals(newItem.getRestaurant().getName());
        }
    };
}
