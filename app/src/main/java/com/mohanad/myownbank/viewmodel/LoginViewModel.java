package com.mohanad.myownbank.viewmodel;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mohanad.myownbank.model.network.NetworkLogin;

public class LoginViewModel {
    FirebaseAuth auth;
    NetworkLogin networkLogin;
    private static boolean loginState;
    private static boolean loginSuccess;


    public LoginViewModel(){
        networkLogin = NetworkLogin.getInstance();
        if (networkLogin.isLogin()==true){
            loginState=networkLogin.isLogin();
        }else{
            loginState=networkLogin.isLogin();
        }
    }




    public void login(String username,String password){

        networkLogin.getFirebaseAuth().signInWithEmailAndPassword(username+"@bank.com",password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                System.out.println("success");
                loginSuccess=true;
                networkLogin.setLogin(loginSuccess);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.getMessage();
                System.out.println("failed");
                loginSuccess=false;
                networkLogin.setLogin(loginSuccess);
            }
        });

    }

    public static boolean isLoginState() {
        return loginState;
    }
    public static boolean isLoginSuccess() {
        return loginSuccess;
    }
}
