package com.mohanad.myownbank.view.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;


import com.mohanad.myownbank.view.Adapters.AccountsAdapter;
import com.mohanad.myownbank.view.Listeners.onClickInterface;
import com.mohanad.myownbank.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountsFragment extends Fragment {


    private static final String TAG = "AccountsFragment";
    private HomeViewModel homeViewModel = new HomeViewModel();
    private RecyclerView accounts_recycler;
    private static List<Account> accounts;
    private LinearLayoutManager linearLayoutManager1;
    private FrameLayout frameLayout;
    private onClickInterface onclickInterface;
    private String user_id;
    private static List<sendDataListener> dataChangeListeners = new ArrayList<>();

    public static void addListener(sendDataListener changeListener) {
        dataChangeListeners.add(changeListener);
    }

    public static void deleteListener(sendDataListener changeListener) {
        dataChangeListeners.remove(changeListener);
    }

    public static void notifyDataChange(String account_id, String user_id) {
        for (sendDataListener changeListener : dataChangeListeners)
            changeListener.sendPosition(account_id, user_id);

    }

    public interface sendDataListener {
        void sendPosition(String account_id, String user_id);

    }

    public AccountsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        decelerations(view);
        displayAccounts();
        getActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.transactionsContainer, new TransactionsFragment()).
                addToBackStack(null).commit();


        return view;
    }

    private void displayAccounts() {
        LiveData<Task<QuerySnapshot>> liveDataAccounts = homeViewModel.getdataSnapshotLiveDataAccounts();
        liveDataAccounts.observe(this, new androidx.lifecycle.Observer<Task<QuerySnapshot>>() {
            @Override
            public void onChanged(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null)
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Account account;
                            account = document.toObject(Account.class);
                            account.setACCOUNT_ID(document.getId());
                            accounts.add(account);

                        }
                    AccountsAdapter accountsAdapter = new AccountsAdapter(accounts, onclickInterface);
                    accounts_recycler.setLayoutManager(linearLayoutManager1);
                    accounts_recycler.setAdapter(accountsAdapter);
                    Log.d(TAG, "Success");
                }

            }
        });

    }

    private void decelerations(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String temp = user.getEmail();
        user_id = temp.substring(0, 6);
        accounts = new ArrayList<>();
        accounts_recycler = view.findViewById(R.id.accounts_recycler);
        linearLayoutManager1 = new LinearLayoutManager(getContext());
        frameLayout = view.findViewById(R.id.transactionsContainer);
        onclickInterface = new onClickInterface() {
            @Override
            public void onClick(int i) {
                String id = accounts.get(i).getACCOUNT_ID();
                notifyDataChange(id, user_id);
                frameLayout.setVisibility(View.VISIBLE);


            }
        };

    }


}
