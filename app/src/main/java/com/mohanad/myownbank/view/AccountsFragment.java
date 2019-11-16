package com.mohanad.myownbank.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;
import com.mohanad.myownbank.model.entity.Transaction;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountsFragment extends Fragment {
    RecyclerView transactions_recycler;
    List<Transaction>transactions;
    RecyclerView accounts_recycler;
    List<Account>accounts;
    LinearLayoutManager  linearLayoutManager1;
    LinearLayoutManager  linearLayoutManager2;

    public AccountsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_accounts, container, false);
        decelerations(view);
        showAccounts();
        showTransactions();
        return view;
    }

    private void decelerations(View view) {
        accounts_recycler=view.findViewById(R.id.accounts_recycler);
        transactions_recycler =view.findViewById(R.id.recycler_view_TransAdapter);
        linearLayoutManager1=new LinearLayoutManager(getContext());
        linearLayoutManager2=new LinearLayoutManager(getContext());


    }

    private void showAccounts(){
        accounts_recycler.setLayoutManager(linearLayoutManager1);
        accounts=new ArrayList<>();
        AccountsAdapter accountsAdapter=new AccountsAdapter(accounts);
        accounts_recycler.setAdapter(accountsAdapter);
    }
    private  void  showTransactions(){
        transactions_recycler.setLayoutManager(linearLayoutManager2);
        transactions=new ArrayList<>();
        TransactionsAdapter transactionsAdapter =new TransactionsAdapter(transactions);
        transactions_recycler.setAdapter(transactionsAdapter);
    }



}
