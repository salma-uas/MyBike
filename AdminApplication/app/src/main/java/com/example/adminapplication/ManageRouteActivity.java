package com.example.adminapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageRouteActivity extends AppCompatActivity {

    Button btnAddRoute, btnViewRoute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_route);

        btnAddRoute = (Button)findViewById(R.id.btnAddRoute);
        btnViewRoute = (Button)findViewById(R.id.btnViewRoute);


        btnAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(ManageRouteActivity.this,AddRouteActivity.class);
                startActivity(HomeIntent);
            }});

        btnViewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent HomeIntent = new Intent(ManageRouteActivity.this,RouteListActivity.class);
                startActivity(HomeIntent);
            }});
    }
}
