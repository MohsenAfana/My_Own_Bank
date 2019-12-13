package com.mohanad.myownbank.view;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mohanad.myownbank.MapsActivity;
import com.mohanad.myownbank.R;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    TabLayout tabLayout;
    FirebaseAuth auth;
    FirebaseUser user;
    private ImageButton register,language,about;

    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        declaration();

    }

    public void declaration() {
        register=findViewById(R.id.registerBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        language=findViewById(R.id.language);
        about=findViewById(R.id.about);
        loading=findViewById(R.id.log_loading);

        tabLayout = findViewById(R.id.tab_layout);
        usernameEditText = findViewById(R.id.account_number);
        passwordEditText = findViewById(R.id.password);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if(user!=null){
           openMainActivity();
        }
        registerForContextMenu(language);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        call_support();
                        break;
                    case 1:
                        openMap();
                        break;
                }
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

    private void openMap() {
        Intent intent   =new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


    public void login(View view) {
        final String accountNumber = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        if (accountNumber.isEmpty()) {
            show_email_required(getResources().getString(R.string.required));
            return;
        } else if (password.isEmpty()) {
            show_password_required(getResources().getString(R.string.required));
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




    public void call_support() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:1700150150"));
        startActivity(intent);

    }


    public void show_email_required(String message) {
        usernameEditText.setError(message);
    }

    public void show_password_required(String message) {
        passwordEditText.setError(message);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(getResources().getString(R.string.SelectLanguage));
        menu.add(0, v.getId(), 0, "English");
        menu.add(0, v.getId(), 0, "العربية");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getTitle()== "English") {
            Locale locale= new Locale("en");
            Locale.setDefault(locale);

            Configuration configuration= LoginActivity.this.getResources().getConfiguration();
            configuration.setLocale(locale);
            LoginActivity.this.getResources().updateConfiguration(configuration,LoginActivity.this.getResources().getDisplayMetrics());

            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        else if(item.getTitle()== "العربية"){
            Locale locale= new Locale("ar");
            Locale.setDefault(locale);

            Configuration configuration= LoginActivity.this.getResources().getConfiguration();
            configuration.setLocale(locale);
            LoginActivity.this.getResources().updateConfiguration(configuration,LoginActivity.this.getResources().getDisplayMetrics());

            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }else
            return false;
        return true;


    }
}
