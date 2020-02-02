package com.sagishchori.weatherapplication.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sagishchori.weatherapplication.R;
import com.sagishchori.weatherapplication.databinding.CityWeatherForecastItemBinding;
import com.sagishchori.weatherapplication.databinding.CityWeatherForecastViewBinding;
import com.sagishchori.weatherapplication.interfaces.OnDataLoadedInterface;
import com.sagishchori.weatherapplication.models.forecast.ForecastResponse;
import com.sagishchori.weatherapplication.models.forecast.ListItem;

import java.util.ArrayList;

public class ForecastFragment extends Fragment implements OnDataLoadedInterface {

    public static final String TAG = ForecastFragment.class.getCanonicalName();

    public static final String CITY_NAME = "cityName";

    private CityWeatherForecastViewBinding binding;
    private MainViewModel viewModel;


    public static ForecastFragment newInstance(Bundle args) {
        ForecastFragment fragment = new ForecastFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.city_weather_forecast_view, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String cityName = null;
        if (getArguments() != null) {
            cityName = getArguments().getString(CITY_NAME);
        }
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.setOnDataLoadedInterface(this);
        viewModel.initCityForecast(cityName).observe(getActivity(), new Observer<ForecastResponse>() {
            @Override
            public void onChanged(ForecastResponse forecastResponse) {
                binding.setForecast(forecastResponse);

                setWeatherForecastDaysToView(forecastResponse);
            }
        });
    }

    private void setWeatherForecastDaysToView(ForecastResponse forecastResponse) {
        ArrayList<ListItem> listItems = setForecastItemsToList(forecastResponse);
        for (int i = 0; i < 5; i++) {
            CityWeatherForecastItemBinding forecastItemBinding = CityWeatherForecastItemBinding.inflate(getLayoutInflater());
            ListItem item = listItems.get(i);
            forecastItemBinding.setForecastWeather(item.getWeather().get(0));
            forecastItemBinding.setForecastMain(item.getMain());
            forecastItemBinding.setForecastItem(item);

            binding.weatherByDayLayout.addView(forecastItemBinding.getRoot());
        }
    }

    private ArrayList<ListItem> setForecastItemsToList(ForecastResponse forecastResponse) {
        ArrayList<ListItem> listItems = new ArrayList<>();
        for (int i = 0; i < forecastResponse.getList().size(); i++) {
            ListItem item = forecastResponse.getList().get(i);
            if (i == 0) {
                listItems.add(item);
            } else {
                if (item.getDtTxt().contains("00:00:00")) {
                    listItems.add(item);
                }
            }
        }
        return listItems;
    }

    @Override
    public void onDataLoaded() {

    }

    @Override
    public void onFailedToLoadData() {
        new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setMessage(getString(R.string.city_not_found_error))
                .setTitle(getString(R.string.no_results_found))
                .show();
    }

    @Override
    public void onGeneralError() {
        new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setMessage(getString(R.string.general_error_message))
                .setTitle(getString(R.string.general_error_title))
                .show();
    }
}
