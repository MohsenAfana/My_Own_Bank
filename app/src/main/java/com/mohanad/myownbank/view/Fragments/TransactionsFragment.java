package com.mohanad.myownbank.view.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohanad.myownbank.R;

import com.mohanad.myownbank.model.entity.Transactions;
import com.mohanad.myownbank.view.Adapters.TransactionsAdapter;
import com.mohanad.myownbank.view.Fragments.AccountsFragment;
import com.mohanad.myownbank.viewmodel.TransactionsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionsFragment extends Fragment implements AccountsFragment.sendDataListener {

    private static String TAG = "TransactionsFragment";
    private RecyclerView transactionsRecycler;
    private LinearLayoutManager linearLayoutManager2;
    private static List<Transactions> transactions;

    public TransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);


        declarations(view);

        return view;
    }

    private void declarations(View view) {

        transactionsRecycler = view.findViewById(R.id.recycler_view_TransAdapter);

        linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        transactionsRecycler.setLayoutManager(linearLayoutManager2);
        transactions = new ArrayList<>();

    }

    @Override
    public void sendPosition(String account_id, String user_id) {
        TransactionsViewModel transactionsVM = new TransactionsViewModel(account_id);
        LiveData<Task<QuerySnapshot>> liveData = transactionsVM.getdataSnapshotLiveData();

        liveData.observe(this, new androidx.lifecycle.Observer<Task<QuerySnapshot>>() {
            @Override
            public void onChanged(Task<QuerySnapshot> querySnapshotTask) {
                if (querySnapshotTask.isSuccessful()) {

                    if (querySnapshotTask.getResult() != null) {
                        for (QueryDocumentSnapshot document : querySnapshotTask.getResult()) {
                            Transactions t;
                            t = document.toObject(Transactions.class);
                            transactions.add(t);

                        }
                        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(transactions, getContext());
                        transactionsRecycler.setLayoutManager(linearLayoutManager2);
                        transactionsRecycler.setAdapter(transactionsAdapter);
                        Log.d(TAG, "Success");
                    }

                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onPause() {
        super.onPause();

        AccountsFragment.deleteListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        AccountsFragment.addListener(this);
    }
}
