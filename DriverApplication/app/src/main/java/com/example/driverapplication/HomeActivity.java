package com.example.driverapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button btnManageBooking, btnBookingHistory, btnManageProfile,btnGenerateReport, btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Driver Application");

        btnManageBooking = (Button)findViewById(R.id.btnManageBooking);
        btnBookingHistory = (Button)findViewById(R.id.btnBookingHistory);
        btnManageProfile = (Button)findViewById(R.id.btnManageProfile);
        btnGenerateReport = (Button)findViewById(R.id.btnGenerateReport);
        btnLogOut = (Button)findViewById(R.id.btnLogout);


        btnManageBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this, BookingActivity.class);
                HomeIntent.putExtra("status", "pending");
                startActivity(HomeIntent);
            }});


        btnBookingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this, Booking_List.class);
                HomeIntent.putExtra("status", "completed");
                startActivity(HomeIntent);
            }});

        btnManageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this, ManageProfileActivity.class);
                startActivity(HomeIntent);
            }});

        btnGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this, GenerateReport.class);
                startActivity(HomeIntent);
            }});

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(HomeActivity.this).logout();
                finish();
                Intent HomeIntent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(HomeIntent);

            }
        });

    }
}
