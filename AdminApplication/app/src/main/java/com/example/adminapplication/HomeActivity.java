package com.example.adminapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    ImageView imageView;
    Button getmButtonManageProfile;
    Button btnManageUser,btnManageRoute, btnLogout,btnGenerateReport,btnViewFeedback,btnManageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Admin Application");
        btnManageUser = (Button)findViewById(R.id.btnManageUser);
        btnManageRoute = (Button)findViewById(R.id.btnManageRoute);
        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnGenerateReport = (Button)findViewById(R.id.btnGenerateReport);
        btnViewFeedback = (Button)findViewById(R.id.btnViewFeedback);
        btnManageProfile = (Button)findViewById(R.id.btnManageProfile);



        btnManageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this,ManageUser.class);
                startActivity(HomeIntent);
            }});

        btnManageRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this,ManageRouteActivity.class);
                startActivity(HomeIntent);
            }});

        btnGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this,GenerateReport.class);
                startActivity(HomeIntent);
            }});

        btnViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this,ViewFeedback.class);
                startActivity(HomeIntent);
            }});

        btnManageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(HomeActivity.this,ManageProfile.class);
                startActivity(HomeIntent);
            }});


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplication()).logout();
                Toast.makeText(HomeActivity.this, "Successfully Logout", Toast.LENGTH_SHORT).show();

                finish();
                Intent HomeIntent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(HomeIntent);

            }});
    }
}
