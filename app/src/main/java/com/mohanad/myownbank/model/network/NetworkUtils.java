package com.mohanad.myownbank.model.network;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private static NetworkUtils instance;
    private String BASE_URL="https://api.exchangeratesapi.io/";
    private String APP_ID="f6d385b85841be173e8492a616a94eb4";
    private  CurrencyApiInterface currencyApiInterface;
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


    public Map<String,String > getQueryMap() {
        Map<String,String> map = new HashMap<>();
        map.put("base","ILS");

        return map;
    }






}
