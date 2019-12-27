package com.mohanad.myownbank.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mohanad.myownbank.R;

import com.mohanad.myownbank.view.Fragments.AccountsFragment;
import com.mohanad.myownbank.view.Fragments.HomeFragment;
import com.mohanad.myownbank.view.Fragments.PayFragment;
import com.mohanad.myownbank.view.Fragments.TransferFragment;
import com.mohanad.myownbank.viewmodel.MainViewModel;


public class MainActivity extends AppCompatActivity {

    private TextView holderName;
    private MainViewModel mainViewModel;
    private LottieAnimationView animationView;


    public static final long DISCONNECT_TIMEOUT = 60000; // 3 min = 3 * 60 * 1000 ms

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
        displayHolderName();
    }


    public void declaration() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_bar);

        MaterialToolbar toolbar = findViewById(R.id.toolBar);

        animationView = findViewById(R.id.hom_loading);
        mainViewModel = new MainViewModel();
        holderName = findViewById(R.id.holderName);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                OnOptions(item);
                return false;
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomListeners(item);
                return false;
            }
        });


    }

    public void OnOptions(MenuItem item) {
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
    }


    private void bottomListeners(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.home, new HomeFragment()).
                        addToBackStack(null).commit();
                break;

            case R.id.accounts:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.home, new AccountsFragment()).
                        addToBackStack(null).commit();

                break;
            case R.id.pay:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.home, new PayFragment()).
                        addToBackStack(null).commit();

                break;
            case R.id.transfer:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.home, new TransferFragment()).
                        addToBackStack(null).commit();

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
        mainViewModel.logout();
        openLoginActivity();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mainViewModel.logout();
        openLoginActivity();
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void displayHolderName() {

        LiveData<Task<DocumentSnapshot>> liveData = mainViewModel.getdataSnapshotLiveData();
        liveData.observe(this, new Observer<Task<DocumentSnapshot>>() {
            @Override
            public void onChanged(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    holderName.setText(documentSnapshot.get("fullName").toString());
                    animationView.setVisibility(View.GONE);
                }
            }
        });
    }


}
