package com.mohanad.myownbank.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;

import com.mohanad.myownbank.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    TextView holderName;
    BottomNavigationView bottomNavigationView;
    MaterialToolbar toolbar;
    MainViewModel mainViewModel;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private LottieAnimationView animationView;
    private int TIME_OUT = 5000;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id;
    List<Account> accounts;



    public static final long DISCONNECT_TIMEOUT = 180000; // 3 min = 3 * 60 * 1000 ms


    private Handler disconnectHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return true;
        }
    });

    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            mainViewModel.logout();

            openLoginActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        declaration();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animationView.setVisibility(View.GONE);

            }
        }, TIME_OUT);
        displayHolderName();

    }


    public void declaration() {


        animationView = findViewById(R.id.hom_loading);
        accounts = new ArrayList<>();
        mainViewModel = new MainViewModel();
        holderName = findViewById(R.id.holderName);
        frameLayout = findViewById(R.id.container);
        bottomNavigationView = findViewById(R.id.bottom_bar);
        toolbar = findViewById(R.id.toolBar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        new MaterialAlertDialogBuilder(MainActivity.this)
                                .setTitle(getResources().getString(R.string.logout))
                                .setMessage(getResources().getString(R.string.are_y_sure))
                                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mainViewModel.logout();
                                        openLoginActivity();
                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.no), null)
                                .show();

                        break;
                    case 1:

                        break;
                }
                return false;
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomListeners(item);
                return false;
            }
        });


    }


    private void bottomListeners(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:

                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.accounts:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AccountsFragment()).addToBackStack(null).commit();
                break;
            case R.id.atm:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new PayFragment()).addToBackStack(null).commit();


                break;
            case R.id.transfer:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new TransferFragment()).addToBackStack(null).commit();

                break;

        }
    }


    public void resetDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction() {
        resetDisconnectTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();

    }


    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void displayHolderName() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String temp = user.getEmail();
        id = temp.substring(0, 6);
        DocumentReference docRef = db.document("/User/" + id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        holderName.setText(document.get("fullName").toString());

                    } else {

                    }
                } else {

                }
            }
        });


    }


}
