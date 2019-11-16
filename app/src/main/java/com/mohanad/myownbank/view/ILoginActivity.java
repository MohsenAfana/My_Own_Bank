package com.mohanad.myownbank.view;

public interface ILoginActivity {
    void openMainActivity();
    void showErrorMessage(String message);
    void call_support();
    void show_email_required(String message);
    void show_password_required(String message);
}
