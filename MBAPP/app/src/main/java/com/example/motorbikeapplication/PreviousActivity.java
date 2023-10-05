package com.example.motorbikeapplication;

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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PreviousActivity extends AppCompatActivity {


    List<Previous> previousList;
    RecyclerView recyclerView;
    PreviousTripAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Previous Trip");
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        previousList = new ArrayList<>();

        int user_id = sharedPrefManager.getID();

        loadProfile(user_id);
    }

    private void loadProfile(final int user_id)
    {

        // StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_USERS,
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_PREVIOUS_TRIP + user_id,
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

                                Previous previousSet = new Previous(booking_id, time_date,  price, status, location, destination,   rating);
                                previousList.add(previousSet);
                            }

                            //creating recyclerview adapter
                            adapter = new PreviousTripAdapter(PreviousActivity.this, previousList);

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
