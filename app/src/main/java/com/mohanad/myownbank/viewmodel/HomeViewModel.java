package com.mohanad.myownbank.viewmodel;

import androidx.annotation.NonNull;

import com.mohanad.myownbank.model.entity.Currency;
import com.mohanad.myownbank.model.entity.CurrencyInfoObservable;
import com.mohanad.myownbank.model.network.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel {
    private NetworkUtils mNetworkUtils;
    private CurrencyInfoObservable currencyInfoObservable;

    public HomeViewModel(){
        mNetworkUtils = NetworkUtils.getInstance();
        currencyInfoObservable = new CurrencyInfoObservable();
    }

    public CurrencyInfoObservable getCurrency() {
        Call<Currency> call = mNetworkUtils.getWeatherApiInterface().getCurrencyInfo(mNetworkUtils.getQueryMap());
        call.enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(@NonNull Call<Currency> call, @NonNull Response<Currency> response) {
                System.out.println("Received!");
                Currency currency = response.body();

                currencyInfoObservable.setCurrency(currency);
            }


            @Override
            public void onFailure(@NonNull Call<Currency> call, @NonNull Throwable t) {
                System.out.println("ali");
                currencyInfoObservable.setErrorMessage(t.getMessage());
            }
        });
        return currencyInfoObservable;
    }

    public void logout(){

    }
}
