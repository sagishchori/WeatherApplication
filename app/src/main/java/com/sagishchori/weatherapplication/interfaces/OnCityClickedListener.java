package com.sagishchori.weatherapplication.interfaces;

public interface OnCityClickedListener {

    /**
     * Indicate that a city item of the main cities list was clicked.
     *
     * @param cityName      Pass the city name that was clicked
     */
    void onCityClicked(String cityName);
}
