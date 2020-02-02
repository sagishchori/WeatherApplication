package com.sagishchori.weatherapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sagishchori.weatherapplication.databinding.CityWeatherRecyclerItemBinding;
import com.sagishchori.weatherapplication.interfaces.OnCityClickedListener;
import com.sagishchori.weatherapplication.models.current.CityWeatherItem;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityItem> {

    private List<CityWeatherItem> cityItems;
    private OnCityClickedListener listener;

    public CityAdapter(List<CityWeatherItem> cityItems, OnCityClickedListener listener) {
        this.cityItems = cityItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CityItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CityWeatherRecyclerItemBinding binding = CityWeatherRecyclerItemBinding.inflate(inflater, parent, false);
        return new CityItem(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CityItem holder, int position) {

        final CityWeatherItem item = cityItems.get(position);
        holder.bind(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCityClicked(item.getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cityItems != null) {
            return cityItems.size();
        }

        return 0;
    }

    public void setData(List<CityWeatherItem> cityWeatherItems) {
        if (cityWeatherItems == null || cityWeatherItems.size() == 0) {
            return;
        }

        if (cityItems == null) {
            cityItems = new ArrayList<>();
        } else {
            cityItems.clear();
        }
        cityItems.addAll(cityWeatherItems);

        notifyDataSetChanged();
    }

    public class CityItem extends RecyclerView.ViewHolder {

        private CityWeatherRecyclerItemBinding binding;

        public CityItem(@NonNull CityWeatherRecyclerItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(CityWeatherItem item) {
            binding.setCityData(item);
            binding.setWeatherItem(item.getWeather().get(0));
            binding.setWeatherMain(item.getMain());
        }
    }
}
