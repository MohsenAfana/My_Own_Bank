package com.mohanad.myownbank.view.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mohanad.myownbank.R;

public class CampaignsAdapter extends PagerAdapter {
    int [] campaigns;
    Context context;

    public CampaignsAdapter(int[] campaigns, Context context) {
        this.context=context;
        this.campaigns = campaigns;
    }

    @Override
    public int getCount() {
        return campaigns.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView= (ImageView) LayoutInflater.from(this.context).inflate(R.layout.campaigns_layout,container,false);
        imageView.setImageResource(campaigns[position]);
        //    FrameLayout frameLayout =(FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.card_layout,container,false);
        //  TextView textView=frameLayout.findViewById(R.id.balanceAd);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
