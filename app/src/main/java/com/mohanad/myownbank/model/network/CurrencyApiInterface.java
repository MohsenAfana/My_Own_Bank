package com.mohanad.myownbank.model.network;

import com.mohanad.myownbank.model.entity.Currency;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface CurrencyApiInterface {
    @GET("latest")
    Call<Currency> getCurrencyInfo(@QueryMap Map<String, String> params);

}
