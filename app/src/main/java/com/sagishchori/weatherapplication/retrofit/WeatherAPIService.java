package com.sagishchori.weatherapplication.retrofit;

import com.sagishchori.weatherapplication.models.current.CityWeatherItem;
import com.sagishchori.weatherapplication.models.forecast.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPIService {

    @GET("weather?")
    Call<CityWeatherItem> getCityWeatherItem(@Query("q") String cityName, @Query("appId") String appId);

    @GET("forecast?")
    Call<ForecastResponse> getCityWeatherForecast(@Query("q") String cityName, @Query("appId") String appId);
}
