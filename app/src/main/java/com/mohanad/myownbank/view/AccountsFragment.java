package com.mohanad.myownbank.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;
import com.mohanad.myownbank.model.entity.Transactions;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountsFragment extends Fragment {
    private RecyclerView transactions_recycler;

    private RecyclerView accounts_recycler;
    private List<Account> accounts;
    private LinearLayoutManager linearLayoutManager1;
    private LinearLayoutManager linearLayoutManager2;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser user;
    private FirebaseAuth auth;

    public AccountsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        decelerations(view);
        displayAccountsBalance();


        return view;
    }

    private void decelerations(View view) {
        accounts_recycler = view.findViewById(R.id.accounts_recycler);
        transactions_recycler = view.findViewById(R.id.recycler_view_TransAdapter);
        linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);

    }

    public void displayAccountsBalance() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String temp=user.getEmail();
        String id=temp.substring(0,6);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(id);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                accounts_recycler.setLayoutManager(linearLayoutManager1);
                accounts = new ArrayList<>();
                Account account = new Account();
                account.setFullAccountNumber(dataSnapshot.child("fullAccountNumber").getValue(String.class));
                account.setAccountType(dataSnapshot.child("accountType").getValue(String.class));
                account.setBalance(dataSnapshot.child("balance").getValue(double.class));
                List<Transactions> transactions = new ArrayList<>();
                if(dataSnapshot.child("transactions").getChildren()==null){

                }
                for (DataSnapshot traSnapshot: dataSnapshot.child("transactions").getChildren()) {
                    Transactions t = traSnapshot.getValue(Transactions.class);
                    transactions.add(t);
                }
                accounts.add(account);
                AccountsAdapter accountsAdapter = new AccountsAdapter(accounts);
                accounts_recycler.setAdapter(accountsAdapter);
                transactions_recycler.setLayoutManager(linearLayoutManager2);
                TransactionsAdapter transactionsAdapter = new TransactionsAdapter(transactions);
                transactions_recycler.setAdapter(transactionsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addValueEventListener(postListener);
    }


}
