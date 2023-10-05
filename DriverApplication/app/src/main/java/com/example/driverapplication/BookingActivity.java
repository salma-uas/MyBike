package com.example.driverapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class BookingActivity extends AppCompatActivity {

    List<Booking> bookingList;
    RecyclerView recyclerView;
    BookingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);


        Intent intent = getIntent();
        String status = intent.getStringExtra("status");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Pending Booking");

         if (SharedPrefManager.getInstance(BookingActivity.this).isBooked() && status.equals("pending")){
            finish();
            startActivity(new Intent(BookingActivity.this, BookingDetailsActivity.class));
        }


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookingList = new ArrayList<>();


        loadBooking(status);
    }


    private void loadBooking(final String status)
    {

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        String gender = sharedPrefManager.getGender();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_BOOKING + status + "&gender=" + gender,
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

                                Booking bookingSet = new Booking(booking_id, time_date,  price, status, location, destination,   rating);
                                bookingList.add(bookingSet);
                            }

                            //creating recyclerview adapter
                            adapter = new BookingAdapter(BookingActivity.this, bookingList);

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
