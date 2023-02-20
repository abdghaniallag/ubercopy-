package com.dvls.ubercopy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dvls.ubercopy.databinding.ActivityDriverLoginBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginActivity extends AppCompatActivity {
 private EditText password, email;
 private Button login, registration;
 private FirebaseAuth auth;
 private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent errorActivity= new Intent( DriverLoginActivity.this, MapActivity.class);
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
                auth.createUserWithEmailAndPassword(userEmail,userPassword ).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userID=auth.getCurrentUser().getUid();
                            DatabaseReference currentUserDB= FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(userID);
                            currentUserDB.setValue(true);
                        }else {
                              Toast.makeText(DriverLoginActivity.this,"signup error",Toast.LENGTH_SHORT).show();
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
                auth.signInWithEmailAndPassword(userEmail,userPassword ).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent errorActivity= new Intent( DriverLoginActivity.this, MapActivity.class);
                            startActivity(errorActivity);
                            finish();
                        }else {
                            Toast.makeText(DriverLoginActivity.this,"sign up error",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }
}
