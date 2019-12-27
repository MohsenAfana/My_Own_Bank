package com.mohanad.myownbank.view.Fragments;


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

    private static final String TAG = "PayFragment";

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

    private void declaration(View view){

        com.balysv.materialripple.MaterialRippleLayout jawwal=view.findViewById(R.id.jawwal);
        jawwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Jawwal bill")
                        .setMessage("This service isn't available")
                        .show();
            }
        });

        com.balysv.materialripple.MaterialRippleLayout wataniya=view.findViewById(R.id.wataniya);
        wataniya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Wataniya Credit")
                        .setMessage("This service isn't available")
                        .show();
            }
        });
        com.balysv.materialripple.MaterialRippleLayout electricity=view.findViewById(R.id.electricity);
        electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Electricity Bill")
                        .setMessage("This service isn't available")
                        .show();
            }
        });

        com.balysv.materialripple.MaterialRippleLayout paltel=view.findViewById(R.id.paltel);
        paltel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Paltel Bill")
                        .setMessage("This service isn't available")
                        .show();
            }
        });

        com.balysv.materialripple.MaterialRippleLayout playstation=view.findViewById(R.id.playstation);
        playstation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Playstation Credit")
                        .setMessage("This service isn't available")
                        .show();
            }
        });
        com.balysv.materialripple.MaterialRippleLayout steam=view.findViewById(R.id.steam);
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

