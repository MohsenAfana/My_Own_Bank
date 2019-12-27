package com.mohanad.myownbank.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mohanad.myownbank.model.network.FirestoreQueryLiveData;


public class MainViewModel {

    private FirebaseAuth auth=FirebaseAuth.getInstance();

    public void logout(){
        FirebaseAuth.getInstance().signOut();

    }

    private static final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();

    private String id=auth.getCurrentUser().getEmail().substring(0,6);

    private final FirestoreQueryLiveData liveData = new FirestoreQueryLiveData(mDatabase.collection("User/").document(id));




    @NonNull
    public LiveData<Task<DocumentSnapshot>> getdataSnapshotLiveData(){
        return liveData;
    }



}
