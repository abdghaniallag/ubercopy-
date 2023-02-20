package com.dvls.ubercopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
  private Button mDriver, mCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomer=(Button) findViewById(R.id.customers);
        mDriver= (Button)findViewById(R.id.drivers);

        mDriver.setOnClickListener(v -> {
            Intent startDriverApp= new Intent( MainActivity.this, DriverLoginActivity.class);
            startActivity(startDriverApp);
            finish();
        });
        mCustomer.setOnClickListener(v -> {
            Intent startCustomerApp= new Intent( MainActivity.this, CustomerLoginActivity.class);
            startActivity(startCustomerApp);
            finish();
        });

    }
}