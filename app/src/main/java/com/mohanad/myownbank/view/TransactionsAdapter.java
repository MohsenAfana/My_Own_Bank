package com.mohanad.myownbank.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Transaction;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter {
    List<Transaction> transactions;

    public TransactionsAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
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
        return transactions.size();
    }

    class TransactionHolder extends RecyclerView.ViewHolder {

        TextView type, desc, amount, date;

        public TransactionHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.transaction_type);
            desc = itemView.findViewById(R.id.transaction_desc);
            amount = itemView.findViewById(R.id.transaction_amount);
            date = itemView.findViewById(R.id.transaction_date);
        }

        public void bind(int position) {

            type.setText(transactions.get(position).getType());
            desc.setText(transactions.get(position).getDesc());
            amount.setText(transactions.get(position).getAmount() + "$");
            date.setText(transactions.get(position).getDate());

        }
    }
}
