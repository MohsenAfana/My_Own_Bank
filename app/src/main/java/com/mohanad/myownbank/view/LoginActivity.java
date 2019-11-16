package com.mohanad.myownbank.view;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.presenter.ILoginPresenter;
import com.mohanad.myownbank.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {
    EditText usernameEditText, passwordEditText;
    TabLayout tabLayout;
    ILoginPresenter iloginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        decelerations();
    }

    public void decelerations() {
        tabLayout = findViewById(R.id.tab_layout);
        usernameEditText = findViewById(R.id.account_number);
        passwordEditText = findViewById(R.id.password);
        iloginPresenter = new LoginPresenter(this, this);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
              iloginPresenter.call_support();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                iloginPresenter.call_support();
            }
        });
    }

    public void login(View view) {
        String accountNumber = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (accountNumber.isEmpty()) {
            iloginPresenter.show_email_required("Required");
            return;
        } else if (password.isEmpty()) {
            iloginPresenter.show_password_required("Required");
            return;
        }
        iloginPresenter.login(accountNumber, password);
    }

    public void tab_run(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
               iloginPresenter.call_support();
                break;
       }
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void call_support() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:1700150150"));
        startActivity(intent);
        Toast.makeText(LoginActivity.this, "Clicked", Toast.LENGTH_LONG).show();
    }


    @Override
    public void show_email_required(String message) {
        usernameEditText.setError(message);
    }

    @Override
    public void show_password_required(String message) {
        passwordEditText.setError(message);

    }

}
