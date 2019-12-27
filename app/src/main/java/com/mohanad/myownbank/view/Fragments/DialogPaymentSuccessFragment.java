package com.mohanad.myownbank.view.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.mohanad.myownbank.R;

public class DialogPaymentSuccessFragment extends DialogFragment implements TransferFragment.sendReceiverData {

    private View root_view;
    private TextView to,id,amount,date1 ,time;
    private String receiverName;
   private String receiverId;
   private String amount1;
   private String date;
   private String time1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.dialog_payment_success, container, false);
        to=root_view.findViewById(R.id.receiver);
        id=root_view.findViewById(R.id.receiverID);
        amount=root_view.findViewById(R.id.tAmount);
        time=root_view.findViewById(R.id.transactionTime);
        date1=root_view.findViewById(R.id.transactionDate);

        to.setText(receiverName);
        id.setText(receiverId);
        date1.setText(date);
        this.amount.setText(amount1);
        time.setText(time1);

        root_view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home, new HomeFragment()).addToBackStack(null).commit();

            }
        });

        return root_view;
    }

    public DialogPaymentSuccessFragment() {
        TransferFragment.addListener(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void sendPosition(String receiverName, String receiverId, String amount,String date,String time1) {
        this.receiverName=receiverName;
        this.receiverId=receiverId;
        this.date=date;
        this.amount1=amount;
        this.time1=time1;
        System.out.println("MOHANAD SENT");
    }




    @Override
    public void onPause() {
        super.onPause();
        TransferFragment.deleteListener(this);

    }
}