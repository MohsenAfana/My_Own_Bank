package com.mohanad.myownbank.model.entity;



public class Card {


    private String cardHolder;
    private String cardNomber;
    private String validate;
    private String ccv;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNomber() {
        return cardNomber;
    }

    public void setCardNomber(String cardNumber) {
        this.cardNomber = cardNumber;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }
}