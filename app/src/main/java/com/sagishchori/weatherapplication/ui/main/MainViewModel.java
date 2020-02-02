package com.sagishchori.weatherapplication.ui.main;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sagishchori.weatherapplication.interfaces.OnDataLoadedInterface;
import com.sagishchori.weatherapplication.models.current.CityWeatherItem;
import com.sagishchori.weatherapplication.models.forecast.ForecastResponse;
import com.sagishchori.weatherapplication.repositories.CityWeatherItemRepository;
import com.sagishchori.weatherapplication.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sagishchori.weatherapplication.MainActivity.METRIC;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<CityWeatherItem>> cityList;
    private MutableLiveData<ForecastResponse> forecast;
    private MutableLiveData<String> format;
    private CityWeatherItemRepository repository;
    private OnDataLoadedInterface anInterface;

    public void init(Context context) {
        repository = new CityWeatherItemRepository(context);
        setFormat(METRIC);
    }

    public void setOnDataLoadedInterface(OnDataLoadedInterface anInterface) {
        this.anInterface = anInterface;
    }

    public MutableLiveData<List<CityWeatherItem>> initCityList() {
        if (cityList == null) {
            getListFromDB();
        }

        return cityList;
    }

    /**
     * This is a demo method to mimic loading data from local DB such as ROOM.
     * I am not using it in this project due to time constrains.
     */
    private void getListFromDB() {
        cityList = new MutableLiveData<>();
        cityList.setValue(new ArrayList<CityWeatherItem>());
    }

    public void queryCityDataFromWeb(String cityName) {
        repository.getCityWeatherFromWeb(cityName, format.getValue(), new Callback<CityWeatherItem>() {
            @Override
            public void onResponse(Call<CityWeatherItem> call, Response<CityWeatherItem> response) {
                if (response.isSuccessful()) {
                    List<CityWeatherItem> items = cityList.getValue();
                    CityWeatherItem item = response.body();
                    for (int i = 0; i < cityList.getValue().size(); i++) {
                        if (item != null && item.getName().equals(items.get(i).getName())) {
                            return;
                        }
                    }
                    items.add(item);
                    cityList.postValue(items);
                } else {
                    if (anInterface != null) {
                        anInterface.onFailedToLoadData();
                    }
                }
            }

            @Override
            public void onFailure(Call<CityWeatherItem> call, Throwable t) {
                if (anInterface != null) {
                    anInterface.onGeneralError();
                }
            }
        });
    }

    public MutableLiveData<ForecastResponse> initCityForecast(String cityName) {
        if (StringUtils.isEmpty(cityName)) {
            if (anInterface != null) {
                anInterface.onFailedToLoadData();
            }

            return null;
        }
        if (forecast == null) {
            forecast = new MutableLiveData<>();
            loadForecast(cityName);
        }

        return forecast;
    }

    private void loadForecast(String cityName) {
        repository.getCityWeatherForecastFromWeb(cityName, format.getValue(), new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful()) {
                    forecast.postValue(response.body());
                } else {
                    if (anInterface != null) {
                        anInterface.onFailedToLoadData();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                if (anInterface != null) {
                    anInterface.onGeneralError();
                }
            }
        });
    }

    public void setFormat(String format) {
        if (this.format == null) {
            this.format = new MutableLiveData<>();
        }
        this.format.setValue(format);
    }
}
