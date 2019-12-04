package com.mohanad.myownbank.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohanad.myownbank.R;

import com.mohanad.myownbank.model.entity.Transactions;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends Fragment {
    EditText from, to, amount, desc;
    TextView sender_name, receiver_name;
    Button send, confirm;
    String senderName;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference2;
    private double senderBalance;
    private double receiverBalance;
    private String receiver;
    private int flag = 0;
    private double money;
    private String amount1;
    private String explain;
    private String sender;
    private int TIME_OUT=2000;
    private LottieAnimationView animationView;

    public TransferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transfer, container, false);

        declaration(view);
        dealingWithDataBase();
        return view;


    }

    public void declaration(View view) {
        animationView=view.findViewById(R.id.trans_loading);
        from = view.findViewById(R.id.from_account_number);
        sender_name=view.findViewById(R.id.from_account_name);
        receiver_name=view.findViewById(R.id.to_account_name);

        to = view.findViewById(R.id.to_account_number);
        amount = view.findViewById(R.id.amount);
        desc = view.findViewById(R.id.desc);
        send = view.findViewById(R.id.send);
        confirm = view.findViewById(R.id.confirm);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmData();
            }
        });


    }

    private void confirmData() {
        System.out.println(senderBalance + "sender");
        sender = from.getText().toString();
        receiver = to.getText().toString();
        amount1 = amount.getText().toString();
        explain = desc.getText().toString();
        animationView.setVisibility(View.VISIBLE);
           if(checkTextFields(sender, receiver, amount1, explain) == 1)
           {
               Toast.makeText(getContext(), "an field is empty", Toast.LENGTH_LONG).show();
               animationView.setVisibility(View.INVISIBLE);
               return;

           }else if (checkTextFields(sender, receiver, amount1, explain) == 0) {
               databaseReference2 = FirebaseDatabase.getInstance().getReference().child("User").child(receiver);
               sender_name.setText(senderName);
               money = Double.valueOf(amount1);

           }

        if (checkSenderBalance() == 1) {
            Toast.makeText(getContext(), "Your balance not enough to ensure this Operation",
                    Toast.LENGTH_LONG).show();
            flag = 0;
        } else {
            flag = 1;//all information are true
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    receiverBalance = (dataSnapshot.child("balance").getValue(double.class));
                    receiver_name.setText(dataSnapshot.child("fullName").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    animationView.setVisibility(View.INVISIBLE);

                }
            },TIME_OUT);
            confirm.setVisibility(View.GONE);
            send.setVisibility(View.VISIBLE);
        }


    }

    public void send() {
        if (flag == 1) {
            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            String date = today.monthDay + "/" + today.month + "/" + today.year + "--" + today.format("%k:%M:%S");
            String key = databaseReference2.push().getKey();
            String key2 = mDatabaseReference.push().getKey();
            //---------------------------------------- Set transaction for Receiver
            Transactions transactionForReceiver = new Transactions();
            transactionForReceiver.setAmount(money);
            transactionForReceiver.setDesc(explain);
            transactionForReceiver.setFrom(sender);
            transactionForReceiver.setTo(receiver);
            transactionForReceiver.setDate(date);
            transactionForReceiver.setType("deposit");

            //---------------------------------------- Set transaction for Sender
            Transactions transactionForSender = new Transactions();
            transactionForSender.setAmount(money);
            transactionForSender.setDesc(explain);
            transactionForSender.setFrom(sender);
            transactionForSender.setTo(receiver);
            transactionForSender.setType("withdraw");
            transactionForSender.setDate(date);
            //------------------------------------------Set Sender Balance After Transaction
            senderBalance -= money;
            mDatabaseReference.child("transactions").child(key2).setValue(transactionForSender);
            mDatabaseReference.child("balance").setValue(senderBalance);
            //------------------------------------------Set Receiver Balance After Transaction
            receiverBalance += money;
            databaseReference2.child("transactions").child(key).setValue(transactionForReceiver);
            databaseReference2.child("balance").setValue(receiverBalance);

            Toast.makeText(getContext(), "Transaction Succeeded", Toast.LENGTH_LONG).show();
            clearFields();
            send.setVisibility(View.GONE);
            confirm.setVisibility(View.VISIBLE);
            flag = 0;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).addToBackStack(null).commit();

        }

}

    public void dealingWithDataBase() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String temp = user.getEmail();
        String id = temp.substring(0, 6);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(id);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                senderBalance = dataSnapshot.child("balance").getValue(double.class);
                senderName=dataSnapshot.child("fullName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public int checkTextFields(String sender, String receiver, String amount1, String explain) {
        int result = 0;
        if (sender.isEmpty()) {
            from.setError("Required");
            result = 1;
        } else if (receiver.isEmpty()) {
            to.setError("Required");
            result = 1;
        } else if (amount1.isEmpty()) {
            amount.setError("Required");
            result = 1;
        } else if (explain.isEmpty()) {
            desc.setError("Required");
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    public int checkSenderBalance() {
        if (senderBalance <= 0 && money > senderBalance) {
            return 1;
        } else
            return 0;
    }

    public void clearFields() {
        from.setText("");
        to.setText("");
        amount.setText("");
        desc.setText("");
        sender_name.setText("Select Account");
        receiver_name.setText("Select Account");
    }



}
