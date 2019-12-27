package com.mohanad.myownbank.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mohanad.myownbank.R;
import com.mohanad.myownbank.model.entity.Account;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullName, accountNo, password, mobileNo, email, birthday,iban;
    private Button registerBtn;
    private String name,account_number, passWord,mobile_number,email_text,mBirthday,mIban;
    private CheckBox agree;
    private FirebaseAuth auth;
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        declaration();
    }

    public void declaration(){
        fullName = findViewById(R.id.fullNAME);
        accountNo = findViewById(R.id.accountNumber);
        password = findViewById(R.id.passWord);
        mobileNo = findViewById(R.id.mobileNumber);
        email = findViewById(R.id.email);
        birthday = findViewById(R.id.birthday);
        registerBtn=findViewById(R.id.register);
        agree=findViewById(R.id.acceptance);
        iban=findViewById(R.id.ibanNo);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register(){

        auth=FirebaseAuth.getInstance();


        if(checkFields()==0){
            name=fullName.getText().toString();
            account_number=accountNo.getText().toString();
            passWord = password.getText().toString();
            mobile_number=mobileNo.getText().toString();
            email_text=email.getText().toString();
            mBirthday=birthday.getText().toString();
            mIban=iban.getText().toString();
            auth.createUserWithEmailAndPassword(account_number+"@bank.com", passWord).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    HashMap<String,String> data=new HashMap<>();
                    data.put("fullName",name);
                    data.put("mobileNo",mobile_number);
                    data.put("email",email_text);
                    data.put("birthday",mBirthday);

                    Account account=new Account();
                    account.setFullAccountNumber(account_number);
                    account.setFullName(name);
                    account.setBalance(0);
                    account.setCurrencyLabel("$");
                    account.setAccountCurrency("USD");
                    account.setIBAN(mIban);


                    db.collection("User").document(account_number).set(data);

                    db.collection("User").document(account_number).collection("Accounts").document(account_number).set(account);


                    auth.signOut();
                    clear();
                    openLogin();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });






        }else{
            Toast.makeText(RegisterActivity.this,"an field is empty",Toast.LENGTH_LONG).show();
        }


    }

    private void clear() {
        fullName.setText("");
        accountNo.setText("");
        password.setText("");
        mobileNo.setText("");
        email.setText("");
        birthday.setText("");

        agree.setChecked(false);
        iban.setText("");
    }

    private void openLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public int checkFields(){
        int f=0;
        name=fullName.getText().toString();
        if(name.isEmpty()){
            fullName.setError("Required");
            f=1;
        }
        account_number=accountNo.getText().toString();
        if(account_number.isEmpty()){
            accountNo.setError("Required");
            f=1;
        }
        passWord = password.getText().toString();
        if(passWord.isEmpty()){
            password.setError("Required");
            f=1;
        }
        mobile_number=mobileNo.getText().toString();
        if(mobile_number.isEmpty()){
            mobileNo.setError("Required");
            f=1;
        }
        email_text=email.getText().toString();
        if(email_text.isEmpty()){
            email.setError("Required");
            f=1;
        }
        mBirthday=birthday.getText().toString();
        if(mBirthday.isEmpty()){
            birthday.setError("Required");
            f=1;
        }
        if(agree.isChecked()==false){
            agree.setError("Required");
            f=1;
        }
        mIban=iban.getText().toString();
        if(mIban.isEmpty()){

            iban.setError("Required");
            f=1;
        }

        return f;

    }
}
