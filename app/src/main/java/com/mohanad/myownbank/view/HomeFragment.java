package com.mohanad.myownbank.view;


import android.graphics.Color;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;
import com.mohanad.myownbank.model.entity.Card;
import com.mohanad.myownbank.model.entity.Currency;
import com.mohanad.myownbank.model.entity.CurrencyInfoObservable;
import com.mohanad.myownbank.viewmodel.HomeViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    private static final String TAG = "HomeFragment";
    private PieChartView pieChartView;
    private RecyclerView cardsRecycler;
    private List<Card> cards;

    private HomeViewModel homeViewModel = new HomeViewModel();
    private CurrencyInfoObservable currencyInfoObservable = homeViewModel.getCurrency();

    private PieChartData pieChartData = new PieChartData();

    private TextView usd_rate, euro_rate, tr_rate;
    private double fUSD, fEUR, fTR;
    private  double total_balance;
    private String id;


    private List<Account> accounts;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


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
        TabLayout tabLayout;
        LinearLayoutManager horizontal;
        ViewPager campaign_pager;
        tabLayout = view.findViewById(R.id.currency);
        pieChartView = view.findViewById(R.id.chart);
        accounts = new ArrayList<>();
        cardsRecycler = view.findViewById(R.id.cards_recycler);
        horizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cardsRecycler.setLayoutManager(horizontal);
        campaign_pager = view.findViewById(R.id.campaigns_pager);
        cards = new ArrayList<>();
        usd_rate = view.findViewById(R.id.usd);
        euro_rate = view.findViewById(R.id.euro);
        tr_rate = view.findViewById(R.id.turkishLira);
        int[] campaigns = {R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4, R.drawable.ad5};

        CampaignsAdapter campaignsAdapter = new CampaignsAdapter(campaigns, getContext());
        campaign_pager.setAdapter(campaignsAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        viewILS();

                        break;
                    case 1:
                        viewUSD();

                        break;
                    case 2:
                        viewEUR();

                        break;
                    case 3:System.out.println("Clicked");
                        viewTUR();

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ///-------------------------------------------
        showCurrency();


    }

    private void viewUSD() {
        double temp=total_balance;
        temp/=fUSD;
        updateUi(temp,"USD");
    }

    private void viewILS() {
        double temp=total_balance;

    updateUi(temp,"ILS");
    }


    private void viewEUR() {
        double temp=total_balance;
         temp/=fEUR;

        updateUi(temp,"EUR");
    }


    private void viewTUR() {
        double temp=total_balance;
        temp/=fTR;

        updateUi(temp,"TR");
    }




    private void showCurrency() {
        currencyInfoObservable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                CurrencyInfoObservable observable = (CurrencyInfoObservable) o;
                Currency currencyInfo = observable.getCurrency();
                if (currencyInfo != null) {

                    double usd = (int) (currencyInfo.getRates().getUSD() * 1000);
                    double usdf = (int) usd;
                    double usdd = (int) ((1 / (usdf / 1000)) * 1000);

                    usd_rate.setText(String.valueOf((usdd / 1000)));

                    fUSD = Double.valueOf(usd_rate.getText().toString());

                    double euro = (int) (currencyInfo.getRates().getGBP() * 1000);
                    double eurof = (int) euro;
                    double eurod = (int) ((1 / (eurof / 1000)) * 1000);
                    euro_rate.setText(String.valueOf((eurod / 1000)));
                    fEUR = Double.valueOf(euro_rate.getText().toString());
                    double tR = (int) (currencyInfo.getRates().getTRY() * 1000);
                    double tRf = (int) tR;
                    double tRd = (int) ((1 / (tRf / 1000)) * 1000);
                    tr_rate.setText(String.valueOf(tRd / 1000));
                    fTR = Double.valueOf(tr_rate.getText().toString());

                } else {
                    Toast.makeText(getContext(), observable.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayTotalBalance() {
        FirebaseUser user;
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        try {
            if(user!=null){
                String temp = user.getEmail();
                if(temp !=null)
                id = temp.substring(0, 6);
            }

        }catch (Exception e){
            Toast.makeText(getContext(),"An error occured",Toast.LENGTH_LONG).show();
        }



     /*   try{

        }catch (Exception e){
            Toast.makeText(getContext(),"An error occured",Toast.LENGTH_LONG).show();
        }*/

        db.collection("/User/" + id + "/Cards/")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    try{
                        if(task.getResult() !=null){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Card t = document.toObject(Card.class);
                                cards.add(t);
                            }
                            CardsAdapter cardsAdapter = new CardsAdapter(cards);
                            cardsRecycler.setAdapter(cardsAdapter);
                        }

                    }catch (Exception e){
                        Toast.makeText(getContext(),"An error occured",Toast.LENGTH_LONG).show();
                    }


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }

        });


        db.collection("/User/" + id + "/Accounts/")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    try{
                        if(task.getResult() != null){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Account account ;
                                account = document.toObject(Account.class);
                                account.setACCOUNT_ID(document.getId());
                                accounts.add(account);

                                if (account.getAccountCurrency().equalsIgnoreCase("USD")) {
                                    total_balance += (account.getBalance() * fUSD);

                                } else if (account.getAccountCurrency().equalsIgnoreCase("ILS")) {
                                    total_balance += account.getBalance();
                                } else if (account.getAccountCurrency().equalsIgnoreCase("TR")) {
                                    total_balance += (account.getBalance() * fTR);
                                } else if (account.getAccountCurrency().equalsIgnoreCase("EUR")) {
                                    total_balance += (account.getBalance() * fEUR);
                                }
                                db.collection("Users").document(id).update("totalBalance",total_balance);

                            }
                        }

                    }catch (Exception e){
                        Toast.makeText(getContext(),"An error occured",Toast.LENGTH_LONG).show();
                    }

                    chart();

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });


    }

    private void chart() {
        int[] colors = {getResources().getColor(R.color.colorAccent2), getResources().getColor(R.color.colorPrimaryDark2), getResources().getColor(R.color.primaryTextColor), getResources().getColor(R.color.colorAccent)};
        List<SliceValue> pieData = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            pieData.add(new SliceValue((float) accounts.get(i).getBalance(), colors[i]).setLabel(accounts.get(i).getCurrencyLabel()));

        }
        double toBeTruncated = total_balance;

        double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();


        pieChartData.setValues(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1(truncatedDouble + " ILS").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartData.setCenterText1FontSize(14);
        pieChartView.setPieChartData(pieChartData);

    }

    private void updateUi(double x ,String s){


        double truncatedDouble = BigDecimal.valueOf(x)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1(truncatedDouble + " "+s).setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartData.setCenterText1FontSize(14);
        pieChartView.setPieChartData(pieChartData);
    }




}
