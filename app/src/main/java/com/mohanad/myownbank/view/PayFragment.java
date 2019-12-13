package com.mohanad.myownbank.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mohanad.myownbank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayFragment extends Fragment {

    MaterialCardView jawwal,wataniya;
    MaterialCardView electricity,paltel;
    MaterialCardView playstation,steam;

    public PayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay, container, false);
        declaration(view);


        return view;

    }

    public void declaration(View view){
        jawwal=view.findViewById(R.id.jawwal);
        wataniya=view.findViewById(R.id.wataniya);
        electricity=view.findViewById(R.id.electricity);
        paltel=view.findViewById(R.id.paltel);
        playstation=view.findViewById(R.id.playstation);
        steam=view.findViewById(R.id.steam);

        jawwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Jawwal Credit")
                        .setMessage("This service isn't available")
                        .show();
            }
        });

        wataniya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Wataniya Credit")
                        .setMessage("This service isn't available")
                        .show();
            }
        });
        electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Electricity Bill")
                        .setMessage("This service isn't available")
                        .show();
            }
        });
        paltel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Paltel Bill")
                        .setMessage("This service isn't available")
                        .show();
            }
        });
        playstation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Playstation Credit")
                        .setMessage("This service isn't available")
                        .show();
            }
        });
        steam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Steam Credit")
                        .setMessage("This service isn't available")
                        .show();
            }
        });


    }

}

