package com.example.motorbikeapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DriverRatingActivity extends AppCompatActivity {

    List<DriverRating> previousList;
    RecyclerView recyclerView;
    DriverRatingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Driver Feedback");
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        previousList = new ArrayList<>();
//
//        int user_id = sharedPrefManager.getID();

        Intent intent = getIntent();
        int user_id = intent.getIntExtra("user_id", 0);


        loadProfile(user_id);
    }

    private void loadProfile(final int user_id)
    {

        // StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_USERS,
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_DRIVER_FEEDBACK + user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray previous =  new JSONArray(response);

                            for(int i=0; i<previous.length(); i++){
                                JSONObject previousObbject =  previous.getJSONObject(i);

                                int booking_id = previousObbject.getInt("booking_id");
                                String location = previousObbject.getString("location");
                                String destination = previousObbject.getString("destination");
                                String status = previousObbject.getString("status");
                                String price = previousObbject.getString("price");
                                String time_date = previousObbject.getString("time_date");
                                String rating = previousObbject.getString("rating");
                                String feedback = previousObbject.getString("feedback");

                                DriverRating driverRating = new DriverRating(booking_id, time_date,  price, status, location, destination,rating,feedback);
                                previousList.add(driverRating);
                            }

                            //creating recyclerview adapter
                            adapter = new DriverRatingAdapter(DriverRatingActivity.this, previousList);

                            //setting adapter to recyclerview
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Wrong IP Entered", Toast.LENGTH_LONG).show();

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
