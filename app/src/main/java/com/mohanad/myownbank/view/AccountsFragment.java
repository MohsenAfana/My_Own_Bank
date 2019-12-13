package com.mohanad.myownbank.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
public class AccountsFragment extends Fragment {

    private static final String TAG = "AccountsFragment";
    private RecyclerView accounts_recycler;
    private static List<Account> accounts;
    private LinearLayoutManager linearLayoutManager1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FrameLayout frameLayout;
    private onClickInterface onclickInterface;
    private String user_id;
    static List<sendDataListener> dataChangeListeners = new ArrayList<>();

    public static void addListener(sendDataListener changeListener){
        dataChangeListeners.add(changeListener);
    }

    public static void deleteListener(sendDataListener changeListener){
        dataChangeListeners.remove(changeListener);
    }

    public static void notifyDataChange(String account_id,String user_id){
        for(sendDataListener changeListener : dataChangeListeners)
            changeListener.sendPosition(account_id ,user_id);

    }




    public interface sendDataListener {
        void sendPosition(String account_id,String user_id);

    }

    public AccountsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        decelerations(view);
        displayAccountsBalance();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.transactionsContainer, new TransactionsFragment()).addToBackStack(null).commit();


        return view;
    }

    private void decelerations(View view) {
        accounts_recycler = view.findViewById(R.id.accounts_recycler);
        linearLayoutManager1 = new LinearLayoutManager(getContext());
        frameLayout=view.findViewById(R.id.transactionsContainer);
        onclickInterface =new onClickInterface() {
            @Override
            public void onClick(int i) {
              String id=  accounts.get(i).getACCOUNT_ID();
                notifyDataChange(id,user_id);
                frameLayout.setVisibility(View.VISIBLE);


            }
        };

    }

    public void displayAccountsBalance() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String temp=user.getEmail();
        user_id=temp.substring(0,6);
        accounts = new ArrayList<>();

        db.collection("/User/"+user_id+"/Accounts/")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    final List<Transactions> transactions = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Account account=new Account();
                       account=document.toObject(Account.class);
                       account.setACCOUNT_ID(document.getId());
                        accounts.add(account);
                    System.out.println("Here");
                }  AccountsAdapter accountsAdapter = new AccountsAdapter(accounts,onclickInterface);
                    accounts_recycler.setLayoutManager(linearLayoutManager1);
                    accounts_recycler.setAdapter(accountsAdapter);
                }else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
