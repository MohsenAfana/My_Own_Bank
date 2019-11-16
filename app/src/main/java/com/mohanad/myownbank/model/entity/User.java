package com.mohanad.myownbank.model.entity;

import com.mohanad.myownbank.model.entity.Account;

public class User {
    private String name;
    private  String email;
    private  String password;
    private Account[] accounts;
    private double total_balance;
    private String account_Number;

    public User(String name, String email, String password, Account[] accounts,double total_balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accounts = accounts;
        this.total_balance=total_balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public void setAccounts(Account[] accounts) {
        this.accounts = accounts;
    }

    public double getTotal_balance() {
        return total_balance;
    }

    public void setTotal_balance(double total_balance) {
        this.total_balance = total_balance;
    }
}
