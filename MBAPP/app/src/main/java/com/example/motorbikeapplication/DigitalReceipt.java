package com.example.motorbikeapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class DigitalReceipt extends AppCompatActivity {
    private TextView txtBookingID, txtPickupPoint, txtDestination, your_fare_cost,paid_by_cost,txtDate,txtTime,txtStatus, txtDriverName,txtBikeModel;
    private RatingBar ratingbar, ratingBar;
    private Button btnRating;

    int booking_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_receipt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Digital Receipt");
        txtBookingID = findViewById(R.id.txtBookingID);
        txtPickupPoint = findViewById(R.id.txtPickupPoint);
        txtDestination = findViewById(R.id.txtDestination);
        your_fare_cost = findViewById(R.id.your_fare_cost);
        paid_by_cost = findViewById(R.id.paid_by_cost);
        txtStatus = findViewById(R.id.txtStatus);
//        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtDriverName = findViewById(R.id.txtDriverName);
        txtBikeModel = findViewById(R.id.txtBikeModel);
        ratingbar = findViewById(R.id.ratingbar);
        btnRating = findViewById(R.id.btnRating);

        Intent intent = getIntent();
        booking_id = intent.getIntExtra("booking_id", 0);


        btnRating.setVisibility(View.VISIBLE);



        loadDigitalReceipt(booking_id);

    }


    public void onRating(View view){

        Intent intent = getIntent();
        booking_id = intent.getIntExtra("booking_id", 0);


        Intent mainPage = new Intent(DigitalReceipt.this, FeedbackActivity.class);
        mainPage.putExtra("booking_id", booking_id);
        startActivity(mainPage);

    }

    private void loadDigitalReceipt(final int book_id)
    {

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        final String ipaddress = sharedPrefManager.getIP();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Constants.URL_DIGITAL_RECEIPT + book_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray userReceipt =  new JSONArray(response);

                            for(int i=0; i<userReceipt.length(); i++) {
                                JSONObject userReceiptObject = userReceipt.getJSONObject(i);
                                int booking_id = userReceiptObject.getInt("booking_id");
                                String status = userReceiptObject.getString("status");
                                String time_date = userReceiptObject.getString("time_date");
                                String rating = userReceiptObject.getString("rating");
                                String fullname = userReceiptObject.getString("fullname");
                                String username = userReceiptObject.getString("username");
                                String faculty = userReceiptObject.getString("faculty");
                                String mobileNumber = userReceiptObject.getString("mobileNumber");
                                String emailAddress = userReceiptObject.getString("emailAddress");
                                String driver_fullname = userReceiptObject.getString("driver_fullname");
                                String plateno = userReceiptObject.getString("plateno");
                                String bikeModel = userReceiptObject.getString("bikeModel");
                                String pickup_name = userReceiptObject.getString("pickup_name");
                                String destination_name = userReceiptObject.getString("destination_name");
                                String price = userReceiptObject.getString("price");


                                txtBookingID.setText("Booking ID: #" + String.valueOf(booking_id));
                                txtPickupPoint.setText(pickup_name);
                                txtDestination.setText(destination_name);
                                txtStatus.setText("completed");
                                your_fare_cost.setText(price);
                                paid_by_cost.setText("Cash");
                                txtTime.setText(time_date);
                                txtDriverName.setText(driver_fullname);
                                txtBikeModel.setText(bikeModel);
//
//                                if(rating.equals("-1")){
//                                    btnRating.setVisibility(View.VISIBLE);
//
//                                }
//
                                ratingbar.setRating(Float.parseFloat(rating));




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

        public void onClose(View view){

        finish();

    }

//    public void onRating(View view){
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Rate this trip!");
//
//        final View customView = this.getLayoutInflater().inflate(R.layout.ratingbar_inflate, null);
//        ratingBar = (RatingBar)customView.findViewById(R.id.ratingBar);
//        // Set above view in alert dialog.
//        builder.setView(customView);
//
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//    giveRating(booking_id,  ratingBar.getRating());
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
//
//    }




//    private void giveRating(final int booking_id, final float rating) {
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GIVE_RATING,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject jsonObject =  new JSONObject(response);
//
//                            if(!jsonObject.getBoolean("error")){
//
//                                Toast.makeText(getApplicationContext(), "Thank you for your feedback" , Toast.LENGTH_LONG).show();
//
//
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//
//                            }
//
//                        } catch (JSONException e){
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //     Toast.makeText(getApplicationContext(), "E-mail: admin@andkl.com.my\nPassword: admin123", Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(), "Wrong IP entered", Toast.LENGTH_LONG).show();
//                        //      Toast.makeText(LoginActivity.this, "Failed to retrieve data", Toast.LENGTH_LONG).show();
//
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("booking_id", String.valueOf(booking_id));
//                params.put("rating", String.valueOf(rating));
//
//                return params;
//            }
//        };
//
//
//        Volley.newRequestQueue(this).add(stringRequest);
//    }


}
