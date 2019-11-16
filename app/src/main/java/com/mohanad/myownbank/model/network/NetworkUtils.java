package com.mohanad.myownbank.model.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private static NetworkUtils instance;
    private String BASE_URL="https://api.ratesapi.io/api/";
    CurrencyApiInterface currencyApiInterface;
    private NetworkUtils() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        currencyApiInterface = retrofit.create(CurrencyApiInterface.class);
    }
    public CurrencyApiInterface getWeatherApiInterface() {
        return currencyApiInterface;
    }


    public static NetworkUtils getInstance()
    {
        if (instance==null){
            instance = new NetworkUtils();
        }

        return instance;
    }



}
