package com.mohanad.myownbank.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;

import java.util.List;

public class CardsAdapter extends PagerAdapter {
    List<Account> cards;
    Context context;
    public CardsAdapter( List<Account> cards,Context context) {
        this.context=context;
        this.cards = cards;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //  ImageView imageView= (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.campaigns_layout,container,false);
        //  imageView.setImageResource(cards[position]);
        FrameLayout frameLayout =(FrameLayout) LayoutInflater.from(this.context).inflate(R.layout.card_layout,container,false);
      //  TextView balance=frameLayout.findViewById(R.id.balanceAd);
       // balance.setText("$ "+cards.get(position).getBalance());
      //  TextView accountNo=frameLayout.findViewById(R.id.accountNumberAd);
      //  accountNo.setText(cards.get(position).getAccount_number());
        container.addView(frameLayout);

        return frameLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.45f;
    }
}
