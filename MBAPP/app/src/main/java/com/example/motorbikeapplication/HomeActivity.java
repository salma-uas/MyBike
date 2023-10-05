package com.example.motorbikeapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
     Button getmButtonBooking;
     Button getmButtonCancel;
     Button btnLogOut;
     Button getmButtonManageProfile;
     Button button_History;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Motoborike Application");

        getmButtonBooking = (Button) findViewById(R.id.button_Booking);
        getmButtonManageProfile = (Button) findViewById(R.id.button_ManageProfile);
        button_History = (Button) findViewById(R.id.button_History);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);




//        getmButtonCancel= (Button) findViewById(R.id.button_Cancel);
//        getmButtonGiveFeedBack= (Button) findViewById(R.id.button_GiveFeedBack);
//        getmButtonManageProfile = (Button) findViewById(R.id.button_ManageProfile);
//        getmButtonHome = (Button) findViewById(R.id.button_ManageProfile);
//
        getmButtonManageProfile.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View v) {
               Intent HomeIntent = new Intent(HomeActivity.this,ManageProfile.class);
               startActivity(HomeIntent);
           }
       });

        getmButtonBooking.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View v) {
               Intent HomeIntent = new Intent(HomeActivity.this,BookingPage.class);
               startActivity(HomeIntent);
           }
       });


        button_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this,PreviousActivity.class);
                startActivity(HomeIntent);
            }
        });


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
