package com.mohanad.myownbank.model.entity;

public class Transaction {
   private String Type;
   private String desc;
   private String date;
   private double amount;

    public Transaction() {
    }

    public Transaction(String type, String date, double amount, String desc) {
        Type = type;
        this.date = date;
        this.amount = amount;
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {

        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
