package com.mohanad.myownbank.model.entity;

public class Account {
    private  String account_number;
    private Transaction[] transactions;
    private float balance;
    private String account_name;

    public Account(String account_number, float balance,String account_name) {
        this.account_number = account_number;
        this.balance = balance;
        this.account_name=account_name;
    }

    public Account(String account_number, Transaction[] transactions, float balance) {
        this.account_number = account_number;
        this.transactions = transactions;
        this.balance = balance;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
}
