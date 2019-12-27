package com.mohanad.myownbank.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohanad.myownbank.model.entity.Currency;
import com.mohanad.myownbank.model.entity.CurrencyInfoObservable;


import com.mohanad.myownbank.model.network.FirestoreQueryLiveDataCollections;
import com.mohanad.myownbank.model.network.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private NetworkUtils mNetworkUtils;
    private CurrencyInfoObservable currencyInfoObservable;
    private final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();

    private String id = auth.getCurrentUser().getEmail().substring(0, 6);

    public HomeViewModel() {
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




    private final FirestoreQueryLiveDataCollections liveData = new FirestoreQueryLiveDataCollections(mDatabase.
            collection("User/").
            document(id).
            collection("Cards"));

    public LiveData<Task<QuerySnapshot>> getdataSnapshotLiveData() {

        return liveData;
    }


    private final FirestoreQueryLiveDataCollections liveDataAccounts = new FirestoreQueryLiveDataCollections(mDatabase.collection("User/").document(id).collection("Accounts"));

    public LiveData<Task<QuerySnapshot>> getdataSnapshotLiveDataAccounts() {

        return liveDataAccounts;
    }

}
