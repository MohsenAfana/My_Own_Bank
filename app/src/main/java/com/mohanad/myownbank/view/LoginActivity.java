package com.mohanad.myownbank.view;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mohanad.myownbank.R;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    TabLayout tabLayout;
    FirebaseAuth auth;
    FirebaseUser user;

    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        declaration();
    }

    public void declaration() {
        loading=findViewById(R.id.log_loading);

        tabLayout = findViewById(R.id.tab_layout);
        usernameEditText = findViewById(R.id.account_number);
        passwordEditText = findViewById(R.id.password);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if(user!=null){
           openMainActivity();
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                call_support();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                call_support();
            }
        });
    }

    public void login(View view) {
        final String accountNumber = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        if (accountNumber.isEmpty()) {
            show_email_required("Required");
            return;
        } else if (password.isEmpty()) {
            show_password_required("Required");
            return;
        }
        login(accountNumber,password);


    }
    public void login(String username,String password){
        loading.setVisibility(View.VISIBLE);
       auth.signInWithEmailAndPassword(username+"@bank.com",password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                openMainActivity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.setVisibility(View.INVISIBLE);
               Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }


    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void showErrorMessage(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }


    public void call_support() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:1700150150"));
        startActivity(intent);
        Toast.makeText(LoginActivity.this, "Clicked", Toast.LENGTH_LONG).show();
    }


    public void show_email_required(String message) {
        usernameEditText.setError(message);
    }

    public void show_password_required(String message) {
        passwordEditText.setError(message);

    }

}
