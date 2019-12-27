package com.mohanad.myownbank.view.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;
import com.mohanad.myownbank.view.Listeners.onClickInterface;

import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter {
    private List<Account> accounts;
    private com.mohanad.myownbank.view.Listeners.onClickInterface onClickInterface;

    public AccountsAdapter(List<Account> accounts,onClickInterface onClickInterface) {
        this.accounts = accounts;
        this.onClickInterface=onClickInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_layout, parent, false);

        return new AccountsAdapter.AccountHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        AccountsAdapter.AccountHolder accountHolder = (AccountsAdapter.AccountHolder) holder;
        accountHolder.bind(position);

        accountHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInterface.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    class AccountHolder extends RecyclerView.ViewHolder {
        TextView account_name;
        TextView balance;
        TextView account_number;
        TextView ibanNumber;
        TextView fullName;
        TextView currency;
        FrameLayout frameLayout;


        private AccountHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout=itemView.findViewById(R.id.myAccount);
            account_name = itemView.findViewById(R.id.account_name_adapter);
            balance = itemView.findViewById(R.id.account_balance_adapter);
            account_number = itemView.findViewById(R.id.account_number_adapter);
            ibanNumber=itemView.findViewById(R.id.iban_number_adapter);
            fullName=itemView.findViewById(R.id.account_fullName_adapter);
            currency=itemView.findViewById(R.id.account_type_adapter);
        }


        private void bind(int position) {
            account_name.setText(accounts.get(position).getAccountType());
            account_number.setText( String.valueOf( accounts.get(position).getFullAccountNumber()));
            balance.setText(accounts.get(position).getBalance() +accounts.get(position).getCurrencyLabel());
            ibanNumber.setText(String.valueOf( accounts.get(position).getIBAN()) );
            fullName.setText(String.valueOf(accounts.get(position).getFullName()));
            currency.setText(String.valueOf(accounts.get(position).getAccountCurrency()));
        }
    }
}
