package com.sagishchori.weatherapplication.repositories;

import android.content.Context;

import com.sagishchori.weatherapplication.models.current.CityWeatherItem;
import com.sagishchori.weatherapplication.models.forecast.ForecastResponse;
import com.sagishchori.weatherapplication.retrofit.RetrofitClient;
import com.sagishchori.weatherapplication.retrofit.WeatherAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class CityWeatherItemRepository {

    private Retrofit retrofitClient;
    private WeatherAPIService weatherAPIService;


    public CityWeatherItemRepository(Context context) {
        retrofitClient = RetrofitClient.getClient(context);
        weatherAPIService = retrofitClient.create(WeatherAPIService.class);
    }


    public void getCityWeatherFromWeb(String cityName, String format, Callback<CityWeatherItem> callback) {
        Call<CityWeatherItem> call = weatherAPIService.getCityWeatherItem(cityName, RetrofitClient.appId);
        call.enqueue(callback);
    }

    public void getCityWeatherForecastFromWeb(String cityName, String format, Callback<ForecastResponse> callback) {
        Call<ForecastResponse> call = weatherAPIService.getCityWeatherForecast(cityName, RetrofitClient.appId);
        call.enqueue(callback);
    }
}
