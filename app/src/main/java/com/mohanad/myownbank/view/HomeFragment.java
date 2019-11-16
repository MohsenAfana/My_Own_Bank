package com.mohanad.myownbank.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;
import com.mohanad.myownbank.model.entity.Currency;
import com.mohanad.myownbank.model.entity.CurrencyInfoObservable;
import com.mohanad.myownbank.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ViewPager campaign_pager,card_pager;
    MainViewModel mainViewModel = new MainViewModel();
    CurrencyInfoObservable currencyInfoObservable = mainViewModel.getCurrency();
    TextView current_currency;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        decelerations(view);

        return view;

    }

    private void decelerations(View view) {
        campaign_pager=view.findViewById(R.id.campaigns_pager);
        card_pager=view.findViewById(R.id.accounts_pager);
        current_currency=view.findViewById(R.id.current);
        int [] campaigns={R.drawable.ad1,R.drawable.ad2,R.drawable.ad3,R.drawable.ad4,R.drawable.ad5};
        List<Account> cards=new ArrayList<>();
        CampaignsAdapter campaignsAdapter=new CampaignsAdapter(campaigns,getContext());
        campaign_pager.setAdapter(campaignsAdapter);
        Account account1=new Account("452915",170,"ali");
        Account account2=new Account("452054",1000,"Mohamme");
        Account account3=new Account("452123",100,"omar");
        cards.add(account1);
        cards.add(account2);
        cards.add(account3);
        CardsAdapter cardsAdapter=new CardsAdapter(cards,getContext());
        card_pager.setAdapter(cardsAdapter);
        ///-------------------------------------------
        currencyInfoObservable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                CurrencyInfoObservable observable = (CurrencyInfoObservable) o;
                Currency currencyInfo = observable.getCurrency();
                if (currencyInfo != null) {
                    current_currency.setText(String.valueOf(currencyInfo.getRates().getILS()));

                } else {
                    Toast.makeText(getContext(), observable.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
