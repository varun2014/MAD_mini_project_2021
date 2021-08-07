package com.android.udacity_foodquest.ui.favorites;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.udacity_foodquest.data.local.entity.CommonRestaurant;
import com.android.udacity_foodquest.databinding.FavoritesItemBinding;

public class FavoritesAdapter extends ListAdapter<CommonRestaurant, FavoritesAdapter.ViewHolder> {

    private FavoritesCallback callback;

    protected FavoritesAdapter(FavoritesCallback callback) {
        super(FAVORITES_DIFF_CALLBACK);
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

        FavoritesItemBinding binding;

        public ViewHolder(FavoritesItemBinding binding, FavoritesCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onFavoriteRestaurantClick(binding.getRestaurant(), binding.favoritesThumb));
            binding.favoritesMainMarker.setOnClickListener(v ->
                    callback.onFavoriteRestaurantMarkerClick(binding.getRestaurant()));
        }

        public static ViewHolder create(LayoutInflater inflater, ViewGroup parent, FavoritesCallback callback) {
            FavoritesItemBinding favoritesItemBinding = FavoritesItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(favoritesItemBinding, callback);
        }

        public void bind(CommonRestaurant restaurant) {
            binding.setRestaurant(restaurant);
            binding.executePendingBindings();
        }
    }

    private static DiffUtil.ItemCallback<CommonRestaurant> FAVORITES_DIFF_CALLBACK = new DiffUtil.ItemCallback<CommonRestaurant>() {
        @Override
        public boolean areItemsTheSame(CommonRestaurant oldItem, CommonRestaurant newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(CommonRestaurant oldItem, CommonRestaurant newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };
}
