package com.mohanad.myownbank.viewmodel;

import com.google.firebase.auth.FirebaseAuth;


public class MainViewModel {
    public void logout(){
        FirebaseAuth.getInstance().signOut();

    }

}
