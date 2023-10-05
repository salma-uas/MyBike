package com.example.motorbikeapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class BookingPageContinue extends AppCompatActivity {
    Button btnPayment, btnCancel, btnArrived, btnCompleted;

    Spinner spinner, spinner1;
    TextView txtPickupPointValue, txtDestinationtValue,txtPriceValue, txtStatus, txtBookingIDValue, txtTimer, txtStatusValue, txtNameValue,txtBikeModelValue,txtPlateNoValue,btnEmergency,btnDriverFeedback;
    ArrayAdapter<CharSequence> adapter;
    int destination,location, counter_min = 2 ,counter = 60, user_id;
    private boolean timerRunning;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page_continue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Make Booking");

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        int booking_id = sharedPrefManager.getBookingId();
        String location = sharedPrefManager.getLocations();
        String destination = sharedPrefManager.getDestination();
        String status = sharedPrefManager.getStatus();
        String price = sharedPrefManager.getPrice();

        txtPickupPointValue = findViewById(R.id.txtPickupPointValue);
        txtDestinationtValue = findViewById(R.id.txtDestinationtValue);
        txtPriceValue = findViewById(R.id.txtPriceValue);
        txtStatus = findViewById(R.id.txtStatus);
        txtBookingIDValue = findViewById(R.id.txtBookingIDValue);

        txtStatusValue = findViewById(R.id.txtStatusValue);
        txtNameValue = findViewById(R.id.txtNameValue);
        txtBikeModelValue = findViewById(R.id.txtBikeModelValue);
        txtPlateNoValue = findViewById(R.id.txtPlateNoValue);

        txtTimer = findViewById(R.id.txtTimer);

        if (SharedPrefManager.getInstance(BookingPageContinue.this).isTimer()){

            int min = sharedPrefManager.getMin();
            int sec = sharedPrefManager.getSec();

            counter_min = min;
            counter = sec;

        }

        countDownTimer  = new CountDownTimer(120000, 1000){
            @Override
            public void onTick(long l) {

                txtTimer.setText(String.valueOf("You can only cancel this booking within\n" + counter_min + ":" +counter));
//                updateTimer();

                if(counter == 0 && counter_min == 0){
                    counter_min = 0;
                    counter = 0;
                }
                else if( counter == 0)
                {
                    counter_min--;
                    counter = 60;

                }
                counter--;

                SharedPrefManager.getInstance(getApplicationContext()).setTimer(0,0);
//
//                counter = 1;
//                updateTimer();

            }

            @Override
            public void onFinish() {

                txtTimer.setText("You cannot cancel this booking");
                btnCancel.setVisibility(View.INVISIBLE);
                btnEmergency.setVisibility(View.VISIBLE);



            }
        }.start();
//
//        timerRunning = true;

//        txtBookingIDValue.setText(String.valueOf(booking_id));
//        txtPickupPointValue.setText(location);
//        txtDestinationtValue.setText(destination);
//        txtPriceValue.setText(price);

        btnPayment = (Button) findViewById(R.id.btnPayment);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnArrived = (Button) findViewById(R.id.btnArrived);
        btnCompleted = (Button) findViewById(R.id.btnCompleted);
        btnEmergency = (Button) findViewById(R.id.btnEmergency);
        btnDriverFeedback = (Button) findViewById(R.id.btnDriverFeedback);


        btnPayment.setVisibility(View.INVISIBLE);
        btnCompleted.setVisibility(View.INVISIBLE);
        btnArrived.setVisibility(View.INVISIBLE);
        btnEmergency.setVisibility(View.INVISIBLE);
        btnDriverFeedback.setVisibility(View.INVISIBLE);


        final Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {

                loadCurrentBooking();


                h.postDelayed(this, 1000);
            }
        });

//        loadCurrentBooking();
    }

    public void onDriver(View view){

        Intent mainPage = new Intent(BookingPageContinue.this, DriverRatingActivity.class);
        mainPage.putExtra("user_id", user_id);
        startActivity(mainPage);


    }




    public void onCancelBooking(View view){

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        int booking_id = sharedPrefManager.getBookingId();

        updateBooking(booking_id, "Cancelled");

        sharedPrefManager.removeCurrentBooking();
        SharedPrefManager.getInstance(getApplicationContext()).setTimer(0,0);


        finish();


    }

    public void onArrived(View view){

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        int booking_id = sharedPrefManager.getBookingId();

        updateBooking(booking_id, "in-progress");

        txtStatus.setText("Heading to your destination");

        btnCompleted.setVisibility(View.VISIBLE);


        btnCancel.setVisibility(View.INVISIBLE);
        btnArrived.setVisibility(View.INVISIBLE);


    }

    public void onPayment(View view){

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        int booking_id = sharedPrefManager.getBookingId();

        Intent mainPage = new Intent(BookingPageContinue.this, DigitalReceipt.class);
        mainPage.putExtra("booking_id", booking_id);
        startActivity(mainPage);
    }


    public void onCompleted(View view){

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        int booking_id = sharedPrefManager.getBookingId();

        updateBooking(booking_id, "completed");

        txtStatus.setText("Safely arrived at your destination");



        btnCompleted.setVisibility(View.INVISIBLE);

        btnPayment.setVisibility(View.INVISIBLE);


        Intent mainPage = new Intent(BookingPageContinue.this, DigitalReceipt.class);
        mainPage.putExtra("booking_id", booking_id);
        startActivity(mainPage);

        finish();
        sharedPrefManager.removeCurrentBooking();



    }








    private void updateBooking(final int booking_id, final String status) {
       final SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_BOOKING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject =  new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){

                                Toast.makeText(getApplicationContext(), "Booking " + status, Toast.LENGTH_LONG).show();

                                if(status.equals("cancelled")){
                                    finish();

                                    Intent mainPage = new Intent(BookingPageContinue.this, BookingPage.class);
                                    startActivity(mainPage);

                                }
//                                else if(status.equals("completed")){
//                                    Intent mainPage = new Intent(BookingPageContinue.this, DigitalReceipt.class);
//                                    mainPage.putExtra("booking_id", booking_id);
//                                    startActivity(mainPage);
//
//                                    finish();
//                                    sharedPrefManager.removeCurrentBooking();
//
//                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //     Toast.makeText(getApplicationContext(), "E-mail: admin@andkl.com.my\nPassword: admin123", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Wrong IP entered", Toast.LENGTH_LONG).show();
                        //      Toast.makeText(LoginActivity.this, "Failed to retrieve data", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("booking_id", String.valueOf(booking_id));
                params.put("status", status);
//                params.put("access", "user");

                return params;
            }
        };


        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void loadCurrentBooking()
    {

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        int booking_id = sharedPrefManager.getBookingId();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Constants.URL_DIGITAL_RECEIPT + booking_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray groupRecords =  new JSONArray(response);

                            JSONObject groupRecordsObject = groupRecords.getJSONObject(0);



                            int booking_id = groupRecordsObject.getInt("booking_id");
                            String status = groupRecordsObject.getString("status");
                            String time_date = groupRecordsObject.getString("time_date");
                            String rating = groupRecordsObject.getString("rating");
                            String fullname = groupRecordsObject.getString("fullname");
                            String driver_id = groupRecordsObject.getString("username");
                            String faculty = groupRecordsObject.getString("faculty");
                            String mobileNumber = groupRecordsObject.getString("mobileNumber");
                            String emailAddress = groupRecordsObject.getString("emailAddress");
                            String driver_fullname = groupRecordsObject.getString("driver_fullname");
                            String plateno = groupRecordsObject.getString("plateno");
                            String bikeModel = groupRecordsObject.getString("bikeModel");
                            String pickup_name = groupRecordsObject.getString("pickup_name");
                            String destination_name = groupRecordsObject.getString("destination_name");
                            String price = groupRecordsObject.getString("price");


                            txtBookingIDValue.setText(String.valueOf(booking_id));
                            txtPickupPointValue.setText(pickup_name);
                            txtDestinationtValue.setText(destination_name);
                            txtPriceValue.setText("TK " + price);
                            txtStatusValue.setText(status);

                            txtNameValue.setText(driver_fullname);
                            txtBikeModelValue.setText(bikeModel);
                            txtPlateNoValue.setText(plateno);

                            if(!(driver_id.equals("null"))){
                                user_id = Integer.valueOf(driver_id);
                                btnDriverFeedback.setVisibility(View.VISIBLE);


                            }

                            if(status.equals("pending")){
                                txtStatus.setText("Waiting for driver...");

                            }else if(status.equals("accepted")) {
                                txtStatus.setText("Driver on the way. Please wait.");
                                btnArrived.setVisibility(View.VISIBLE);
                                btnCompleted.setVisibility(View.INVISIBLE);

                            }
                            else if(status.equals("in-progress")) {
                                txtStatus.setText("Heading to your destination");
                                btnArrived.setVisibility(View.INVISIBLE);
                                btnCompleted.setVisibility(View.VISIBLE);
                                btnCancel.setVisibility(View.INVISIBLE);


                            }

                            if(!(driver_fullname.equals("null")) && (status.equals("accepted") || status.equals("in-progress"))){

                            }

                            else if(!(driver_fullname.equals("null"))){
                                btnArrived.setVisibility(View.VISIBLE);
                            }else  if(driver_fullname.equals("null")){
                                btnArrived.setVisibility(View.INVISIBLE);
                            }




                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Wrong IP entered", Toast.LENGTH_LONG).show();

                    }
                });



        Volley.newRequestQueue(this).add(stringRequest);
    }
}
