package com.mohanad.myownbank.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Transactions;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter {
    List<Transactions> tranactions;

    public TransactionsAdapter(List<Transactions> tranactions) {
        this.tranactions = tranactions;
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

        public void bind(int position) {

            type.setText(tranactions.get(position).getType());
            desc.setText(tranactions.get(position).getDesc());
            amount.setText(tranactions.get(position).getAmount() + "$");
            date.setText(tranactions.get(position).getDate());
            to.setText(tranactions.get(position).getTo());
            from.setText(tranactions.get(position).getFrom());

        }
    }
}
