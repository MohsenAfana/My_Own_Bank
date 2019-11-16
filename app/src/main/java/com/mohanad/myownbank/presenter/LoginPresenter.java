package com.mohanad.myownbank.presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mohanad.myownbank.view.ILoginActivity;
import com.mohanad.myownbank.view.LoginActivity;

public class LoginPresenter implements ILoginPresenter {
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    Context context ;
    ILoginActivity iLoginActivity;

    public LoginPresenter(Context context, ILoginActivity iLoginActivity) {
        this.context = context;
        this.iLoginActivity = iLoginActivity;
        firebaseAuth=FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            iLoginActivity.openMainActivity();
        }
    }

    @Override
    public void login(String username, String password) {
        firebaseAuth.signInWithEmailAndPassword(username+"@bank.com",password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                iLoginActivity.openMainActivity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.getMessage();
                iLoginActivity.showErrorMessage(message);
            }
        });
    }

    @Override
    public void call_support() {
        iLoginActivity.call_support();
    }



    @Override
    public void show_email_required(String message) {

        iLoginActivity.show_email_required(message);
    }

    @Override
    public void show_password_required(String message) {
        iLoginActivity.show_password_required(message);
    }
}
