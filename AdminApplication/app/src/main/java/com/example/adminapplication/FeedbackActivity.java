package com.example.adminapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText editFeedback;

    int booking_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Feedback by passenger");

        ratingBar = findViewById(R.id.ratingbar);
        editFeedback = findViewById(R.id.editFeedback);

        Intent intent = getIntent();
        booking_id = intent.getIntExtra("booking_id", 0);

        loadFeedback(booking_id);
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
                                    editFeedback.setText("No feedback given");

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
