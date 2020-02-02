package com.sagishchori.weatherapplication.interfaces;

public interface OnDataLoadedInterface {

    /**
     * Indicates that the data has loaded
     */
    void onDataLoaded();

    /**
     * Indicates that the requested data failed to load
     */
    void onFailedToLoadData();

    /**
     * Indicated an unknown error
     */
    void onGeneralError();
}
