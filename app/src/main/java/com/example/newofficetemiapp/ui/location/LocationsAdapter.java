package com.example.newofficetemiapp.ui.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newofficetemiapp.R;

import java.util.ArrayList;
import java.util.List;

private static class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationViewHolder> {
    private List<String> locations = new ArrayList<>();
    private final OnLocationClickListener listener;

    public LocationsAdapter(OnLocationClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<String> newLocations) {
        this.locations = new ArrayList<>(newLocations);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        String location = locations.get(position);
        holder.bind(location, listener);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        private final TextView locationNameTextView;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            locationNameTextView = itemView.findViewById(R.id.locationNameTextView);
        }

        public void bind(String location, OnLocationClickListener listener) {
            locationNameTextView.setText(location);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLocationClick(location);
                }
            });
        }
    }

    interface OnLocationClickListener {
        void onLocationClick(String location);
    }
}
}
