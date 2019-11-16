package com.mohanad.myownbank.model.network;

import com.mohanad.myownbank.model.entity.Currency;

import retrofit2.Call;
import retrofit2.http.GET;


public interface CurrencyApiInterface {
    @GET("latest")
    Call<Currency> getCurrencyInfo();

}
