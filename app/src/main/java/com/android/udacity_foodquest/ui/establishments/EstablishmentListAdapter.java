package com.android.udacity_foodquest.ui.establishments;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.udacity_foodquest.databinding.EstablishmentListItemBinding;
import com.android.udacity_foodquest.model.restaurant.search.Restaurant;

public class EstablishmentListAdapter extends ListAdapter<Restaurant, EstablishmentListAdapter.ViewHolder> {

    private EstablishmentCallback.RestaurantCallback callback;

    protected EstablishmentListAdapter(EstablishmentCallback.RestaurantCallback callback) {
        super(ESTABLISHMENT_LIST_DIFF_CALLBACK);
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

        EstablishmentListItemBinding binding;

        public ViewHolder(EstablishmentListItemBinding binding, EstablishmentCallback.RestaurantCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onEstablishmentClick(binding.getRestaurant(), binding.establishmentListThumb));
            binding.establishmentListMainMarker.setOnClickListener(v ->
                    callback.onEstablishmentMarkerClick(binding.getRestaurant()));
        }

        public static ViewHolder create(LayoutInflater inflater, ViewGroup parent, EstablishmentCallback.RestaurantCallback callback) {
            EstablishmentListItemBinding establishmentListItemBinding = EstablishmentListItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(establishmentListItemBinding, callback);
        }

        public void bind(Restaurant restaurant) {
            binding.setRestaurant(restaurant);
            binding.executePendingBindings();
        }
    }

    private static DiffUtil.ItemCallback<Restaurant> ESTABLISHMENT_LIST_DIFF_CALLBACK = new DiffUtil.ItemCallback<Restaurant>() {

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
