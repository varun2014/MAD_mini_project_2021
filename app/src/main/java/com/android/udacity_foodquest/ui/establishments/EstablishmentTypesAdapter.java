package com.android.udacity_foodquest.ui.establishments;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.udacity_foodquest.databinding.EstablishmentTypesItemBinding;
import com.android.udacity_foodquest.model.establishments.Establishment;

public class EstablishmentTypesAdapter extends ListAdapter<Establishment, EstablishmentTypesAdapter.ViewHolder> {

    private final EstablishmentCallback.TypesCalback callback;

    protected EstablishmentTypesAdapter(@NonNull DiffUtil.ItemCallback<Establishment> diffCallback, EstablishmentCallback.TypesCalback callback) {
        super(diffCallback);
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(LayoutInflater.from(parent.getContext()),parent, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        EstablishmentTypesItemBinding binding;

        public ViewHolder(EstablishmentTypesItemBinding binding, EstablishmentCallback.TypesCalback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onEstablishmentTypesClick(binding.getEstablishment()));
        }

        public static ViewHolder create(LayoutInflater inflater, ViewGroup parent, EstablishmentCallback.TypesCalback callback) {
            EstablishmentTypesItemBinding establishmentTypesItemBinding = EstablishmentTypesItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(establishmentTypesItemBinding, callback);
        }

        public void bind(Establishment establishment)  {
            binding.setEstablishment(establishment);
            binding.executePendingBindings();
        }
    }

    static class EstablishmentTypesDiffCallback extends DiffUtil.ItemCallback<Establishment> {

        @Override
        public boolean areItemsTheSame(Establishment oldItem, Establishment newItem) {
            return oldItem.getEstablishment().getId() == newItem.getEstablishment().getId();
        }

        @Override
        public boolean areContentsTheSame(Establishment oldItem, Establishment newItem) {
            return oldItem.getEstablishment().getName().equals(newItem.getEstablishment().getName());
        }
    }
}
