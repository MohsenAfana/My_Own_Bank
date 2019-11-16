package com.mohanad.myownbank.model.entity;

import com.mohanad.myownbank.model.entity.Currency;

import java.util.Observable;

public class CurrencyInfoObservable extends Observable {

    private Currency currency;
    private String errorMessage;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        setChanged();
        notifyObservers();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
