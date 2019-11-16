package com.mohanad.myownbank.presenter;

public interface ILoginPresenter {
    void login(String username,String password);
    void call_support();
    void show_email_required(String message);
    void show_password_required(String message);
}
