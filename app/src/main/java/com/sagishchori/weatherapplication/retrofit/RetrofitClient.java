package com.sagishchori.weatherapplication.retrofit;

import android.content.Context;

import com.sagishchori.weatherapplication.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private static Retrofit retrofit = null;

    public static final String appId = "4c5124098060e4f137776d4c2ff13a4f";

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
