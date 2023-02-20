package com.dvls.ubercopy;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerLoginActivity extends AppCompatActivity {
    private EditText password, email;
    private Button login, registration;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_login);
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent errorActivity= new Intent( CustomerLoginActivity.this, MapActivity.class);
                    startActivity(errorActivity);
                    finish();
                }

            }
        };

        email = (EditText) findViewById(R.id.driverEmail);
        password = (EditText)findViewById(R.id.driverPassword);
        login =(Button) findViewById(R.id.driverLogin);
        registration = (Button)findViewById(R.id.driverRegistration);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String userEmail= email.getText().toString();
                final  String userPassword= password.getText().toString();
                auth.createUserWithEmailAndPassword(userEmail,userPassword ).addOnCompleteListener(CustomerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userID=auth.getCurrentUser().getUid();
                            DatabaseReference currentUserDB= FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userID);
                            currentUserDB.setValue(true);
                        }else {
                            Toast.makeText(CustomerLoginActivity.this,"sign up error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String userEmail= email.getText().toString();
                final  String userPassword= password.getText().toString();
                auth.signInWithEmailAndPassword(userEmail,userPassword ).addOnCompleteListener(CustomerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent errorActivity= new Intent( CustomerLoginActivity.this, MapActivity.class);
                            startActivity(errorActivity);
                            finish();
                        }else {
                            Toast.makeText(CustomerLoginActivity.this,"sign up error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }


}