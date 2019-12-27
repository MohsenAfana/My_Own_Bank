package com.mohanad.myownbank.view.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mohanad.myownbank.R;


import com.mohanad.myownbank.model.entity.Account;
import com.mohanad.myownbank.model.entity.Transactions;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends Fragment {


    private static List<sendReceiverData> dataChangeListeners = new ArrayList<>();

    public static void addListener(sendReceiverData changeListener) {
        dataChangeListeners.add(changeListener);
    }

    public static void deleteListener(sendReceiverData changeListener) {
        dataChangeListeners.remove(changeListener);
    }

    public static void notifyDataChange(String receiverName, String receiverId, String amount, String date,String time) {
        for (sendReceiverData changeListener : dataChangeListeners)
            changeListener.sendPosition(receiverName, receiverId, amount, date,time);

    }

    public interface sendReceiverData {
        void sendPosition(String receiverName, String receiverId, String amount, String date,String time);

    }


    private static final String TAG = "TransferFragment";

    private EditText from, to, amount, desc;
    private TextView sender_name, receiver_name;
    private String receiverName;
    private String senderName, senderNumber;
    private String receiverNumber;
    private String id;
    private static double sender_total_balance;
    private static double receiver_total_balance;

    private double senderBalance;
    private double receiverBalance;
    private static String receiver;
    private int flag = 0;
    private double money;

    private String explain;
    private String sender;

    private LottieAnimationView animationView;
    private static String[] numbers;

    private static List<Account> accounts;
    private static List<Account> receiverAccounts;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


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

    private void declaration(View view) {

        MaterialCardView cardView = view.findViewById(R.id.from);

        accounts = new ArrayList<>();
        receiverAccounts = new ArrayList<>();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialAlertDialogBuilder(getContext())
                        .setTitle(getResources().getString(R.string.select_an_account))
                        .setSingleChoiceItems(numbers, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                senderBalance = accounts.get(i).getBalance();
                                senderName = accounts.get(i).getFullName();
                                senderNumber = accounts.get(i).getFullAccountNumber();
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sender_name.setText(senderName);
                                from.setText(senderNumber);
                            }
                        })
                        .show();
            }
        });
        animationView = view.findViewById(R.id.trans_loading);
        from = view.findViewById(R.id.from_account_number);
        sender_name = view.findViewById(R.id.from_account_name);
        receiver_name = view.findViewById(R.id.to_account_name);

        to = view.findViewById(R.id.to_account_number);
        amount = view.findViewById(R.id.amount);
        desc = view.findViewById(R.id.desc);
        Button confirm;
        confirm = view.findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmData();
            }
        });


    }

    private void confirmData() {


        sender = from.getText().toString();
        receiver = to.getText().toString();
        final String amount1 = amount.getText().toString();
        explain = desc.getText().toString();
        animationView.setVisibility(View.VISIBLE);
        if (checkTextFields(sender, receiver, amount1, explain) == 1) {
            Toast.makeText(getContext(), getResources().getString(R.string.empty), Toast.LENGTH_LONG).show();
            animationView.setVisibility(View.INVISIBLE);
            return;

        } else if (checkTextFields(sender, receiver, amount1, explain) == 0) {

            db.document("/User/" + receiver + "/").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    try {
                        receiver_total_balance = documentSnapshot.getDouble("totalBalance");
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();

                    }

                }
            });


            db.collection("/User/" + receiver + "/Accounts/")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null)
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Account t = document.toObject(Account.class);
                                receiverAccounts.add(t);

                            }
                        for (int i = 0; i < receiverAccounts.size(); i++) {
                            if (receiver.equalsIgnoreCase(receiverAccounts.get(i).getFullAccountNumber())) {
                                receiverBalance = receiverAccounts.get(i).getBalance();
                                receiverName = receiverAccounts.get(i).getFullName();
                                receiver_name.setText(receiverName);
                                receiverNumber = receiverAccounts.get(i).getFullAccountNumber();

                                break;
                            }
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }

            });

            money = Double.valueOf(amount1);
        }

        if (checkSenderBalance() == 1) {
            Toast.makeText(getContext(), getResources().getString(R.string.not_enough),
                    Toast.LENGTH_LONG).show();
            flag = 0;
        } else {
            flag = 1;//all information are true
            int TIME_OUT = 2000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    animationView.setVisibility(View.INVISIBLE);

                }
            }, TIME_OUT);

            new MaterialAlertDialogBuilder(getContext())
                    .setTitle(getResources().getString(R.string.send))
                    .setMessage(getResources().getString(R.string.sure))
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (flag == 1) {
                                final Date date = new Date();
                                final Timestamp tsTemp = new Timestamp(date);

                                Transactions transactionForReceiver = new Transactions();
                                transactionForReceiver.setAmount(money);
                                transactionForReceiver.setDesc(explain);
                                transactionForReceiver.setFrom(sender);
                                transactionForReceiver.setTo(receiver);
                                transactionForReceiver.setDate(tsTemp);
                                transactionForReceiver.setType(getResources().getString(R.string.deposit));
                                final Transactions transactionForSender = new Transactions();
                                transactionForSender.setAmount(money);
                                transactionForSender.setDesc(explain);
                                transactionForSender.setFrom(sender);
                                transactionForSender.setTo(receiver);
                                transactionForSender.setType(getResources().getString(R.string.withdraw));
                                transactionForSender.setDate(tsTemp);

                                senderBalance -= money;
                                sender_total_balance -= money;
                                try {
                                    receiverBalance += money;
                                    receiver_total_balance += money;

                                    db.collection("User").document(receiver).collection("Accounts").document(receiverNumber).collection("Transactions").document().set(transactionForReceiver);
                                    db.document("/User/" + receiver + "/Accounts/" + receiverNumber + "/").update("balance", receiverBalance);
                                    db.document("/User/" + receiver + "/").update("totalBalance", receiver_total_balance).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            db.collection("User").document(id).collection("Accounts").document(senderNumber).collection("Transactions").document().set(transactionForSender);
                                            db.document("/User/" + id + "/Accounts/" + senderNumber + "/").update("balance", senderBalance);
                                            db.document("/User/" + id + "/").update("totalBalance", sender_total_balance).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                                                    Toast.makeText(getContext(), "Transaction Succeeded", Toast.LENGTH_LONG).show();
                                                    clearFields();
                                                    flag = 0;
                                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                    DialogPaymentSuccessFragment newFragment = new DialogPaymentSuccessFragment();
                                                    String pattern = "MM-dd-yyyy";
                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                                                    String pattern2 = "HH:mm:ss";
                                                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
                                                    notifyDataChange(receiverNumber, receiverName, amount1, simpleDateFormat.format(date),simpleDateFormat2.format(date));
                                                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                                    transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

                                                }
                                            });
                                        }
                                    });
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_LONG).show();
                                    clearFields();

                                    flag = 0;
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home, new HomeFragment()).addToBackStack(null).commit();
                                }


                            }
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), null)
                    .show();
        }


    }


    private void dealingWithDataBase() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        String temp = user.getEmail();
        id = temp.substring(0, 6);

        db.document("/User/" + id + "/").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                sender_total_balance = documentSnapshot.getDouble("totalBalance");
            }
        });

        db.collection("/User/" + id + "/Accounts/")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null)
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Account t = document.toObject(Account.class);
                            accounts.add(t);

                        }
                    numbers = new String[accounts.size()];
                    for (int i = 0; i < accounts.size(); i++) {
                        numbers[i] = accounts.get(i).toString();
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }

        });

    }

    private int checkTextFields(String sender, String receiver, String amount1, String explain) {
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

    private int checkSenderBalance() {
        if (senderBalance <= 0 && money > senderBalance) {
            return 1;
        } else
            return 0;
    }

    private void clearFields() {
        from.setText("");
        to.setText("");
        amount.setText("");
        desc.setText("");
        sender_name.setText(getResources().getString(R.string.select_an_account));
        receiver_name.setText(getResources().getString(R.string.select_an_account));
    }


}
