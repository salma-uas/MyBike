package com.example.motorbikeapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class FeedbackActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText editFeedback;
    Button btnSubmit;

    int booking_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Feedback");
        ratingBar = findViewById(R.id.ratingbar);
        editFeedback = findViewById(R.id.editFeedback);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setVisibility(View.INVISIBLE);




        Intent intent = getIntent();
        booking_id = intent.getIntExtra("booking_id", 0);

        loadFeedback(booking_id);
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

    public void onFeedback(View view){

        Intent intent = getIntent();
        booking_id = intent.getIntExtra("booking_id", 0);

        giveRating(booking_id);

    }



    private void giveRating(final int booking_id) {

        final float ratingbar = ratingBar.getRating();
        final String feedback = editFeedback.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GIVE_RATING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject =  new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){

                                Toast.makeText(getApplicationContext(), "Thank you for your feedback" , Toast.LENGTH_LONG).show();
                                finish();

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
                params.put("rating", String.valueOf(ratingbar));
                params.put("feedback", feedback);

                return params;
            }
        };


        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadFeedback(final int book_id)
    {

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Constants.URL_FEEDBACK + book_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray userReceipt =  new JSONArray(response);

                            for(int i=0; i<userReceipt.length(); i++) {
                                JSONObject userReceiptObject = userReceipt.getJSONObject(i);
                                int booking_id = userReceiptObject.getInt("booking_id");
                                String rating = userReceiptObject.getString("rating");
                                String feedback = userReceiptObject.getString("feedback");


                                ratingBar.setRating(Float.parseFloat(rating));
                                editFeedback.setText(feedback);


                                if(!(rating.equals("-1"))){
                                    ratingBar.setIsIndicator(true);
                                }

                                if(feedback.equals("null") || feedback.isEmpty()){
                                    editFeedback.setText("");
                                    editFeedback.setFocusable(true);
                                    editFeedback.setEnabled(true);
                                    editFeedback.setCursorVisible(true);
                                    editFeedback.setBackgroundColor(Color.WHITE);
                                    btnSubmit.setVisibility(View.VISIBLE);
                                    editFeedback.setFocusableInTouchMode(true);

                                }else{
//                                    editFeedback.setEnabled(false);
                                    editFeedback.setFocusable(false);
                                    editFeedback.setCursorVisible(false);
                                    editFeedback.setFocusableInTouchMode(false);

                                }


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
