package com.mohanad.myownbank.model.network;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NetworkLogin {

    private static NetworkLogin instance;
    private   FirebaseAuth firebaseAuth;
    private static boolean login;



    private FirebaseUser currentUser;

    public static NetworkLogin getInstance(){
        if(instance==null){
            instance=new NetworkLogin();
        }
        return instance;
    }

    private NetworkLogin(){
        firebaseAuth=FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            login =true;
        }else {
            login = false;
        }

    }
    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public static boolean isLogin() {
        return login;
    }

    public static void setLogin(boolean login) {
        NetworkLogin.login = login;
    }
}
