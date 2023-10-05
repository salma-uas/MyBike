package com.example.motorbikeapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingPage extends AppCompatActivity {





    Button mConfirm,btnCancel, btnArrived;
    Spinner tripSpinner;
    TextView price, text;
    ArrayAdapter<CharSequence> adapter;
    List<Trips> trips;
    ArrayList<String> cList=new ArrayList<String>();











    int destination,location;
    private ArrayList<String> bookingTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Make Booking");















        if (SharedPrefManager.getInstance(BookingPage.this).isBooked()){
            finish();
            startActivity(new Intent(BookingPage.this, BookingPageContinue.class));
        }

        price = findViewById(R.id.price);

        trips = new ArrayList<>();

        mConfirm = (Button) findViewById(R.id.btnConfirm);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnArrived = (Button) findViewById(R.id.btnArrived);
        text = findViewById(R.id.text);

//        cList.add("Pickup Point > Destination");




        tripSpinner = findViewById(R.id.tripSpinner);

        loadBookingRoute();

//        Trips tripsTest = new Trips(1, "34","fgvd", "dsfsds");
//        trips.add(tripsTest);









        tripSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Trips route = (Trips) parentView.getSelectedItem();

//                String prices =route.getPrice();

//                Testing(route);


//                Trips route = parentView.getPosition(position).toString();


               price.setText("TK " + route.getPrice());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }


    private void loadBookingRoute()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_BOOKING_TRIP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray trip =  new JSONArray(response);

                            for(int i=0; i<trip.length(); i++){
                                JSONObject tripJSONObject =  trip.getJSONObject(i);

                                int location_id = tripJSONObject.getInt("location_id");
                                String pickup_name = tripJSONObject.getString("pickup_name");
                                String pickup_point = tripJSONObject.getString("pickup_point");
                                String destination_name = tripJSONObject.getString("destination_name");
                                String destination_point = tripJSONObject.getString("destination_point");
                                String prices = tripJSONObject.getString("price");

                                Trips tripsTest = new Trips(location_id, pickup_name,destination_name, prices);
                                trips.add(tripsTest);

                            }

                            for (int i = 0; i < trips.size(); i++){
                                cList.add(trips.get(i).getPrice().toString());
                            }

                            ArrayAdapter<Trips> dataAdapter = new ArrayAdapter<Trips>
                                    (BookingPage.this, android.R.layout.simple_spinner_item,trips );

                            dataAdapter.setDropDownViewResource
                                    (android.R.layout.simple_spinner_dropdown_item);
                            tripSpinner.setAdapter(dataAdapter);



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

    public void onConfirmBooking(View view){
        Trips route = (Trips) tripSpinner.getSelectedItem();

//        Testing(route);
        makeBooking(route);
    }

//    private void Testing(Trips route){
//        String price =route.getPrice();
//
//        Toast.makeText(this, price,Toast.LENGTH_LONG).show();
//    }

    private void makeBooking(Trips route) {

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        final int user_id = sharedPrefManager.getID();

//        Trips route = trips.get(tripSpinner.getSelectedItemPosition());
        final int location_id = route.getLocation_id();
        final String location_value = route.getPickup_name();
        final String destination_value = route.getDestination_name();

//        final String location_value = spinner1.getSelectedItem().toString();
//        final String destination_value = spinner.getSelectedItem().toString();
//        final String destination_value = routetripSpinner.getSelectedItemPosition();

        final String price_value = route.getPrice();

//        progressDialog.setMessage("Registering user......");
//        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_MAKE_BOOKING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (!jsonObject.getBoolean("error")) {

                                SharedPrefManager.getInstance(getApplicationContext())
                                        .currentBooking(jsonObject.getInt("booking_id"),location_value,destination_value,"pending", user_id, 0,price_value );

                                SharedPrefManager.getInstance(getApplicationContext()).setTimer(0,0);

                                Toast.makeText(getApplicationContext(), "Booking Successful", Toast.LENGTH_LONG).show();
                                finish();
                                Intent mainPage = new Intent(BookingPage.this, BookingPageContinue.class);
                                startActivity(mainPage);

                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Wrong IP entered", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("location_id", String.valueOf(location_id));
                params.put("status", "pending");
                params.put("passenger_id", String.valueOf(user_id));

                return params;
            }
        };


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