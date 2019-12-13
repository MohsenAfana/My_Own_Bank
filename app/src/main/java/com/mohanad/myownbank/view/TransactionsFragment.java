package com.mohanad.myownbank.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;
import com.mohanad.myownbank.model.entity.Transactions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionsFragment extends Fragment implements AccountsFragment.sendDataListener {

    static int index;
    private static String TAG="TransactionsFragment";
    RecyclerView transactionsRecycler;
    LinearLayoutManager linearLayoutManager2;
    static List<Transactions> transactions;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        transactions=new ArrayList<>();

    }

    @Override
    public void sendPosition(String account_id, String user_id) {

        db.collection("/User/"+user_id+"/Accounts/"+account_id+"/Transactions/")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    final List<Transactions> transactions = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Transactions t = new Transactions();
                        t = document.toObject(Transactions.class);
                        transactions.add(t);
                        System.out.println("Here");
                    }
                    TransactionsAdapter transactionsAdapter = new TransactionsAdapter(transactions);
                    transactionsRecycler.setLayoutManager(linearLayoutManager2);
                    transactionsRecycler.setAdapter(transactionsAdapter);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }


            }
        });}

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
