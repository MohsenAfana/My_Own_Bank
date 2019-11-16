package com.mohanad.myownbank.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;

import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter {
    List<Account>accounts;

    public AccountsAdapter(List<Account> accounts) {
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_layout, parent, false);
        AccountsAdapter.AccountHolder accountHolder = new AccountsAdapter.AccountHolder(view);
        return accountHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AccountsAdapter.AccountHolder accountHolder = (AccountsAdapter.AccountHolder) holder;
        accountHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    class AccountHolder extends RecyclerView.ViewHolder{
        TextView account_name;
        TextView balance;
        TextView account_number;


        public AccountHolder(@NonNull View itemView) {
            super(itemView);

        account_name=itemView.findViewById(R.id.account_name_adapter);
        balance=itemView.findViewById(R.id.account_balance_adapter);
        account_number=itemView.findViewById(R.id.account_number_adapter);
        }


        public void bind(int position) {
            account_name.setText(accounts.get(position).getAccount_name());
            account_number.setText(accounts.get(position).getAccount_number());
            balance.setText(accounts.get(position).getBalance()+"$");
        }
    }
}
