package com.example.driverapplication;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RatingBar;
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

public class BookingDetailsActivity extends AppCompatActivity {

    private String mGeolocationOrigin;
    private GeolocationPermissions.Callback mGeolocationCallback;
    private TextView txtRoute, txtBookingIDValue, txtPickupPointValue, txtDestinationtValue,txtPriceValue,txtStatusValue, txtDateTimeValue, txtPassengerNameValue, txtStudentIDValue, txtFacultyValue, txtContactNoValue, txtEmailValue;
    private Button my_button, btnCancelled,btnAccept,btnPayment;
    WebView myWebView;
    View myView;
    boolean isUp;
    RatingBar ratingbar;

    int booking_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Pending Booking");
        txtRoute = findViewById(R.id.txtRoute);
        myView = findViewById(R.id.my_view);
        myWebView = (WebView) findViewById(R.id.webview);
        my_button = findViewById(R.id.my_button);
        txtBookingIDValue = findViewById(R.id.txtBookingIDValue);
        txtPickupPointValue = findViewById(R.id.txtPickupPointValue);
        txtDestinationtValue = findViewById(R.id.txtDestinationtValue);
        txtPriceValue = findViewById(R.id.txtPriceValue);
        txtStatusValue = findViewById(R.id.txtStatusValue);
        txtDateTimeValue = findViewById(R.id.txtDateTimeValue);
        txtPassengerNameValue = findViewById(R.id.txtPassengerNameValue);
        txtStudentIDValue = findViewById(R.id.txtStudentIDValue);
        txtFacultyValue = findViewById(R.id.txtFacultyValue);
        txtContactNoValue = findViewById(R.id.txtContactNoValue);
        txtEmailValue = findViewById(R.id.txtEmailValue);
        btnCancelled = findViewById(R.id.btnCancelled);
        btnAccept = findViewById(R.id.btnAccept);
        btnPayment = findViewById(R.id.btnPayment);
        ratingbar = findViewById(R.id.ratingbar);

        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        myWebView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        Intent intent = getIntent();
        booking_id = intent.getIntExtra("booking_id", 0);

        if (SharedPrefManager.getInstance(BookingDetailsActivity.this).isBooked()){
            booking_id = sharedPrefManager.getBookingId();
        }


//        txtRoute.setText(String.valueOf(booking_id));

        final Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {

                loadBooking(booking_id);


                h.postDelayed(this, 1000);
            }
        });


//        loadBooking(booking_id);

        myView.setVisibility(View.INVISIBLE);
        isUp = false;

        btnCancelled.setVisibility(View.INVISIBLE);
        btnAccept.setVisibility(View.INVISIBLE);
        btnPayment.setVisibility(View.INVISIBLE);

         String route = sharedPrefManager.getURL();



        myWebView.loadUrl(route);




    }

    private void loadBooking(final int book_id)
    {
        final SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Constants.URL_GET_SELECTED_BOOKING + book_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                                JSONArray staffs =  new JSONArray(response);
                                JSONObject staffObject =  staffs.getJSONObject(0);

                                int booking_id = staffObject.getInt("booking_id");
                                String status = staffObject.getString("status");
                                String time_date = staffObject.getString("time_date");
                                String rating = staffObject.getString("rating");
                                String fullname = staffObject.getString("fullname");
                                String studentid = staffObject.getString("studentid");
                                String username = staffObject.getString("username");
                                String faculty = staffObject.getString("faculty");
                                String mobileNumber = staffObject.getString("mobileNumber");
                                String emailAddress = staffObject.getString("emailAddress");
                                String driver_fullname = staffObject.getString("driver_fullname");
                                String bikeModel = staffObject.getString("bikeModel");
                                String pickup_name = staffObject.getString("pickup_name");
                                String destination_name = staffObject.getString("destination_name");
                                String destination_point = staffObject.getString("destination_point");
                                String price = staffObject.getString("price");


                                txtRoute.setText(pickup_name + " To " + destination_name);
                                txtBookingIDValue.setText(String.valueOf(booking_id));
                                txtPickupPointValue.setText(pickup_name);
                                txtDestinationtValue.setText(destination_name);
                                txtPriceValue.setText("TK " + price);
                                txtStatusValue.setText(status);
                                txtDateTimeValue.setText(time_date);
                                txtPassengerNameValue.setText(fullname);
                                txtStudentIDValue.setText(studentid);
                                txtFacultyValue.setText(faculty);
                                txtContactNoValue.setText(mobileNumber);
                                txtEmailValue.setText(emailAddress);

                                SharedPrefManager.getInstance(getApplicationContext())
                                        .setURL(destination_point);

                                if(status.equals("pending")){
                                    btnAccept.setVisibility(View.VISIBLE);
                                    btnCancelled.setVisibility(View.INVISIBLE);
                                    btnPayment.setVisibility(View.INVISIBLE);

                                }else if(status.equals("accepted")){
                                    btnCancelled.setVisibility(View.VISIBLE);
                                    btnAccept.setVisibility(View.INVISIBLE);
                                    btnPayment.setVisibility(View.INVISIBLE);


                                }else if(status.equals("completed")){
                                    btnPayment.setVisibility(View.VISIBLE);
                                    btnCancelled.setVisibility(View.INVISIBLE);
                                    btnAccept.setVisibility(View.INVISIBLE);


                                }

                                else if(status.equals("Cancelled")){

                                    finish();
                                    sharedPrefManager.removeCurrentBooking();


                                }

                                if((rating.equals("-1"))){
                                    ratingbar.setVisibility(View.INVISIBLE);
                                }else{
                                    ratingbar.setRating(Float.parseFloat(rating));

                                    ratingbar.setVisibility(View.VISIBLE);


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

    // slide the view from below itself to the current position
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(myView);
            my_button.setText("Show Booking Details");
        } else {
            slideUp(myView);
            my_button.setText("Close");
        }
        isUp = !isUp;
    }

    private void updateBooking(final int booking_id, final String status, final String user_id) {




        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_BOOKING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject =  new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){

                                Toast.makeText(getApplicationContext(), "Booking " + status, Toast.LENGTH_LONG).show();

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
                params.put("user_id", String.valueOf(user_id));

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

    public void onAccept(View view){
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        final int user_id = sharedPrefManager.getUserId();
        updateBooking(booking_id, "accepted", String.valueOf(user_id));
        btnCancelled.setVisibility(View.VISIBLE);
        btnPayment.setVisibility(View.VISIBLE);

        SharedPrefManager.getInstance(getApplicationContext())
                .currentBooking(booking_id);

        txtStatusValue.setText("accepted");


    }

    public void onPayment(View view){
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        finish();
        sharedPrefManager.removeCurrentBooking();


    }

    public void onCancelled(View view){
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        updateBooking(booking_id, "pending", null);
        btnAccept.setVisibility(View.VISIBLE);
        sharedPrefManager.removeCurrentBooking();

        txtStatusValue.setText("pending");


    }


    public void onFeedback(View view){


        Intent mainPage = new Intent(BookingDetailsActivity.this, FeedbackActivity.class);
        mainPage.putExtra("booking_id", booking_id);
        startActivity(mainPage);



    }


}
