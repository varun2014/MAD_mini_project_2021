package com.android.udacity_foodquest.ui.search;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.udacity_foodquest.databinding.SearchResultsItemBinding;
import com.android.udacity_foodquest.model.restaurant.search.Restaurant;

public class SearchResultsAdapter extends ListAdapter<Restaurant, SearchResultsAdapter.ViewHolder> {

    private SearchResultsCallback callback;

    protected SearchResultsAdapter(SearchResultsCallback callback) {
        super(SEARCH_DIFF_CALLBACK);
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

        SearchResultsItemBinding binding;

        public ViewHolder(SearchResultsItemBinding binding, SearchResultsCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onRestaurantClick(binding.getRestaurant(), binding.searchResultsThumb));
            binding.searchResultsMainMarker.setOnClickListener(v ->
                    callback.onRestaurantMarkerClick(binding.getRestaurant()));
        }

        public static ViewHolder create(LayoutInflater inflater, ViewGroup parent,SearchResultsCallback callback) {
            SearchResultsItemBinding searchResultsItemBinding = SearchResultsItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(searchResultsItemBinding, callback);
        }

        public void bind(Restaurant restaurant) {
            binding.setRestaurant(restaurant);
            binding.executePendingBindings();
        }
    }

    private static DiffUtil.ItemCallback<Restaurant> SEARCH_DIFF_CALLBACK = new DiffUtil.ItemCallback<Restaurant>() {

        @Override
        public boolean areItemsTheSame(Restaurant oldItem, Restaurant newItem) {
            return oldItem.getRestaurant().getId().equals(newItem.getRestaurant().getId());
        }

        @Override
        public boolean areContentsTheSame(Restaurant oldItem, Restaurant newItem) {
            return oldItem.getRestaurant().getName().equals(oldItem.getRestaurant().getName());
        }
    };
}
