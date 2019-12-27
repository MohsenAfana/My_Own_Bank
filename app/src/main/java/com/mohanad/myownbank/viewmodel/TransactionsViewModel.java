package com.mohanad.myownbank.viewmodel;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohanad.myownbank.model.network.FirestoreQueryLiveDataCollections;
import com.mohanad.myownbank.view.Fragments.AccountsFragment;


public class TransactionsViewModel implements AccountsFragment.sendDataListener {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();

    private String id = auth.getCurrentUser().getEmail().substring(0, 6);
    private String account_id;

    public TransactionsViewModel(String account_id) {
        this.account_id = account_id;
    }

    public LiveData<Task<QuerySnapshot>> getdataSnapshotLiveData() {
        FirestoreQueryLiveDataCollections liveData = new FirestoreQueryLiveDataCollections(mDatabase.
                collection("User/").
                document(id).
                collection("Accounts").
                document(this.account_id).
                collection("Transactions").orderBy("date", Query.Direction.ASCENDING));

        return liveData;
    }


    @Override
    public void sendPosition(String account_id, String user_id) {
        this.account_id = account_id;
    }
}
