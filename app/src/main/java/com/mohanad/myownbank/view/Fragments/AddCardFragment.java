package com.mohanad.myownbank.view.Fragments;


import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Card;
import com.mohanad.myownbank.view.Tools;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCardFragment extends Fragment {
    private TextView card_number;
    private TextView card_expire;
    private TextView card_cvv;
    private TextView card_name;
    private Button add;
    private TextInputEditText et_card_number;
    private TextInputEditText et_expire;
    private TextInputEditText et_cvv;
    private TextInputEditText et_name;
    private RadioGroup rg;

    public AddCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_payment_card_details, container, false);
        declarations(view);
        return view;
    }

    private void declarations(View view){
        final ImageView type=view.findViewById(R.id.cardExpandType);
        rg=view.findViewById(R.id.rg);
        RadioButton visa=view.findViewById(R.id.visa);
        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type.setImageDrawable(getResources().getDrawable(R.drawable.ic_visa));
                type.setImageTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.whiteCardColor)));

            }
        });

        RadioButton master=view.findViewById(R.id.master);
        master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type.setImageDrawable(getResources().getDrawable(R.drawable.master));
                type.setImageTintList( null);

            }
        });
        card_number =view.findViewById(R.id.card_number);
        card_expire =  view.findViewById(R.id.card_expire);
        card_cvv =  view.findViewById(R.id.card_cvv);
        card_name =  view.findViewById(R.id.card_name);
        add=view.findViewById(R.id.add_card);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCard();
            }
        });
        et_card_number =  view.findViewById(R.id.et_card_number);
        et_expire =  view.findViewById(R.id.et_expire);
        et_cvv =  view.findViewById(R.id.et_cvv);
        et_name =  view.findViewById(R.id.et_name);

        et_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_number.setText("**** **** **** ****");
                } else {
                    String number = Tools.insertPeriodically(charSequence.toString().trim(), " ", 4);
                    card_number.setText(number);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_expire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_expire.setText("MM/YY");
                } else {
                    String exp = Tools.insertPeriodically(charSequence.toString().trim(), "/", 2);
                    card_expire.setText(exp);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_cvv.setText("***");
                } else {
                    card_cvv.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_name.setText("Your Name");
                } else {
                    card_name.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void addNewCard() {
        String id="";
       FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user;
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        try {
            if (user != null) {
                String temp = user.getEmail();
                if (temp != null)
                    id = temp.substring(0, 6);
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
        }


        int f=1;
        String cardNumber=et_card_number.getText().toString();
        if(cardNumber.isEmpty()){
            et_card_number.setError(getResources().getString(R.string.required));
            f=1;

        }else{
            f=0;
        }

        String cardExpire=et_expire.getText().toString();
        if(cardExpire.isEmpty()){
            et_expire.setError(getResources().getString(R.string.required)); f=1;

        }else{
            f=0;
        }

        String cardCVV=et_cvv.getText().toString();
        if(cardCVV.isEmpty()){
            et_cvv.setError(getResources().getString(R.string.required));
            f=1;

        }else{
            f=0;

        }
        String cardName=et_name.getText().toString();
        if(cardName.isEmpty()){
            et_name.setError(getResources().getString(R.string.required));
            f=1;

        }else{
            f=0;

        }
        if(rg.isSelected()){
            f=1;
        }else{
            f=0;
        }


        if(f==0){
            Card t=new Card();
            t.setCardHolder(cardName);
            t.setCardNomber(cardNumber);
            t.setCcv(cardCVV);
            switch(rg.getCheckedRadioButtonId()){
                case R.id.visa:
                    t.setType("visa");
                    break;
                case R.id.master:
                    t.setType("master");
                    break;

            }
            t.setValidate(cardExpire);


            db.collection("User").document(id).collection("Cards").document().set(t);
        }





    }


}
