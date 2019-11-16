package com.mohanad.myownbank.presenter;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mohanad.myownbank.view.IMainActivity;

public class MainPresenter implements IMainPresenter {
    FirebaseAuth auth;
    FirebaseUser currentUser;
    IMainActivity iMainActivity;
    Context context;

    public MainPresenter(Context context ,IMainActivity iMainActivity) {
        this.context = context;
        this.iMainActivity = iMainActivity;
    }

    @Override
    public void logout() {
        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();
        FirebaseAuth.getInstance().signOut();

    }

    @Override
    public void openLoginActivity() {
        iMainActivity.openLoginActivity();
    }
}
