package com.mohanad.myownbank.view;


import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Card;
import com.mohanad.myownbank.model.entity.Currency;
import com.mohanad.myownbank.model.entity.CurrencyInfoObservable;
import com.mohanad.myownbank.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;




/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ViewPager campaign_pager ;
    private RecyclerView cardsRecycler;
    private List<Card> cards;
    private LinearLayoutManager horizontal;
    private HomeViewModel homeViewModel = new HomeViewModel();
    private CurrencyInfoObservable currencyInfoObservable = homeViewModel.getCurrency();
    private TextView usd_rate, euro_rate, tr_rate, total;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private ProgressBar progressBar;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        declaration(view);

        displayTotalBalance();
        return view;

    }

    private void declaration(View view) {
        cardsRecycler=view.findViewById(R.id.cards_recycler);
        horizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cardsRecycler.setLayoutManager(horizontal);
        total = view.findViewById(R.id.total_balance);
        progressBar=view.findViewById(R.id.progress);
        campaign_pager = view.findViewById(R.id.campaigns_pager);

        usd_rate = view.findViewById(R.id.usd);
        euro_rate = view.findViewById(R.id.euro);
        tr_rate = view.findViewById(R.id.turkishLira);
        int[] campaigns = {R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4, R.drawable.ad5};

        CampaignsAdapter campaignsAdapter = new CampaignsAdapter(campaigns, getContext());
        campaign_pager.setAdapter(campaignsAdapter);




        ///-------------------------------------------
        showCurrency();


    }

    private void showCurrency() {
        currencyInfoObservable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                CurrencyInfoObservable observable = (CurrencyInfoObservable) o;
                Currency currencyInfo = observable.getCurrency();
                if (currencyInfo != null) {

                    double usd = (int) (currencyInfo.getRates().getUSD() * 1000);
                    usd_rate.setText(String.valueOf(usd / 1000));
                    double euro = (int) (currencyInfo.getRates().getGBP() * 1000);
                    euro_rate.setText(String.valueOf(euro / 1000));
                    double tR = (int) (currencyInfo.getRates().getTRY() * 1000);
                    tr_rate.setText(String.valueOf(tR / 1000));

                } else {
                    Toast.makeText(getContext(), observable.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void displayTotalBalance() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String temp=user.getEmail();
        String id=temp.substring(0,6);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(id);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total.setText(dataSnapshot.child("balance").getValue(double.class)+"");
                progressBar.setProgress(dataSnapshot.child("balance").getValue(int.class));
                 cards=new ArrayList<>();
                if(dataSnapshot.child("cards").getChildren()==null){
                    System.out.println("Null");
                }
                for (DataSnapshot traSnapshot: dataSnapshot.child("cards").getChildren()) {
                    Card t = traSnapshot.getValue(Card.class);
                    cards.add(t);
                    System.out.println("Here");
                }
                CardsAdapter cardsAdapter = new CardsAdapter(cards);
                System.out.println("Adapter create");
                cardsRecycler.setAdapter(cardsAdapter);
                System.out.println("Adapter sit");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addValueEventListener(postListener);


    }


}
