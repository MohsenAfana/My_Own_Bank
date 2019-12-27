package com.mohanad.myownbank.model.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


public class FirestoreQueryLiveDataCollections extends LiveData<Task<QuerySnapshot>> {
    private static final String Log_tag = "FirebaseQueryLive";
    private final Query documentReference;
    private final FirestoreQueryLiveDataCollections.MyValueEventListner listner = new FirestoreQueryLiveDataCollections
            .MyValueEventListner();

    public FirestoreQueryLiveDataCollections(Query ref) {
        this.documentReference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(Log_tag, "onActive");
        documentReference.get().addOnCompleteListener(listner);
    }

    @Override
    protected void onInactive() {
        Log.d(Log_tag, "onInactive");
        documentReference.get().addOnCompleteListener(listner);
    }

    private class MyValueEventListner implements OnCompleteListener<QuerySnapshot> {

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {

                if (task.getResult() != null) {
                    Log.d(Log_tag, "Success");

                }
                setValue(task);
            }
        }
    }
}
