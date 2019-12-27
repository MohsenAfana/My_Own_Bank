package com.mohanad.myownbank.view.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.google.common.io.Resources;
import com.google.firebase.Timestamp;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Transactions;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionsAdapter extends RecyclerView.Adapter {
    List<Transactions> tranactions;
    Context context;

    public TransactionsAdapter(List<Transactions> tranactions ,Context context) {
        this.tranactions = tranactions;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_layout, parent, false);
        TransactionHolder transactionHolder = new TransactionHolder(view);
        return transactionHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TransactionHolder transactionHolder = (TransactionHolder) holder;
        transactionHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return tranactions.size();
    }

    class TransactionHolder extends RecyclerView.ViewHolder {

        TextView type, desc, amount, date,to,from;

        public TransactionHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.transaction_type);
            desc = itemView.findViewById(R.id.transaction_desc);
            amount = itemView.findViewById(R.id.transaction_amount);
            date = itemView.findViewById(R.id.transaction_date);
            to=itemView.findViewById(R.id.transaction_to);
            from=itemView.findViewById(R.id.transaction_from);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(int position) {

            if(tranactions.get(position).getType().equalsIgnoreCase("withdraw")){
                type.setText(tranactions.get(position).getType());
                type.setTextColor(context.getResources().getColor(R.color.redLight));
            }else{
                type.setText(tranactions.get(position).getType());
                type.setTextColor(context.getResources().getColor(R.color.colorAccent1));
            }

            desc.setText(tranactions.get(position).getDesc());
            amount.setText(tranactions.get(position).getAmount() + "$");
            Timestamp mDate= tranactions.get(position).getDate();
            long milli=mDate.getSeconds();

            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(milli * 1000L);
            String date2 = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();

            date.setText(date2);
            to.setText(tranactions.get(position).getTo());
            from.setText(tranactions.get(position).getFrom());

        }
    }

    public void setTranactions(List<Transactions> tranactions) {
        this.tranactions = tranactions;
    }
}
