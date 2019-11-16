package com.mohanad.myownbank.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.presenter.IMainPresenter;
import com.mohanad.myownbank.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements IMainActivity {
    FrameLayout frameLayout;
    TextView holderName;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    IMainPresenter iMainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iMainPresenter=new MainPresenter(this,this);
        decelerations();



    }


    public void decelerations() {

        holderName=findViewById(R.id.holderName);
        frameLayout=findViewById(R.id.container);
        bottomNavigationView = findViewById(R.id.bottom_bar);
        toolbar = findViewById(R.id.toolBar);
        toolbar.inflateMenu(R.menu.tool_bar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                toolbarListeners(menuItem);

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

    private void toolbarListeners(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.logout:
            //LOGOUT
                iMainPresenter.logout();
                iMainPresenter.openLoginActivity();
                break;

        }
    }



    private void bottomListeners(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:

                getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.accounts:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new AccountsFragment()).addToBackStack(null).commit();
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.atm:
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();

                break;
            case R.id.transfer:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new TransferFragment()).addToBackStack(null).commit();
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();

                break;

        }
    }



    @Override
    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
