package com.example.adminapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DriverUserInfoActivity extends AppCompatActivity {

    ImageView imageUser,imageUserBike,imageUserLicense;
    TextView editName, editEmail,editHpno,txtProfession, txtHpno,txtPlateNo,txtBikeModel,txtGender;
    Button btnApproved, btnReject;

    int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_user_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Manage Profile");
        editName = findViewById(R.id.editName);
        editEmail =findViewById(R.id.editEmail);
        editHpno= findViewById(R.id.editHpno);
        txtProfession = findViewById(R.id.txtProfession);
        txtHpno = findViewById(R.id.txtHpno);
        txtPlateNo = findViewById(R.id.txtPlateNo);
        txtBikeModel = findViewById(R.id.txtBikeModel);
        txtGender = findViewById(R.id.txtGender);
        imageUser = findViewById(R.id.imageUser);
        imageUserBike = findViewById(R.id.imageUserBike);
        imageUserLicense = findViewById(R.id.imageUserLicense);

        btnApproved = findViewById(R.id.btnApproved);
        btnReject = findViewById(R.id.btnReject);


        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);

        loadProfile(user_id);


        btnApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(user_id, "approved");
                showToast("approved");

            }});


        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(user_id, "rejected");
                showToast("rejected");

            }});
    }


    private void loadProfile(final int user_id)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_MANAGE_DRIVER_VIEW + user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray staffs =  new JSONArray(response);
                            JSONObject staffObject =  staffs.getJSONObject(0);

                            int id = staffObject.getInt("id");
                            String fullname = staffObject.getString("fullname");
                            String mobileNumber = staffObject.getString("mobileNumber");
                            String gender = staffObject.getString("gender");
                            String email = staffObject.getString("email");
                            String profession = staffObject.getString("profession");
                            String plateno = staffObject.getString("plateno");
                            String bikeModel = staffObject.getString("bikeModel");
                            String user_attachment = staffObject.getString("user_attachment");
                            String bike_attachment = staffObject.getString("bike_attachment");
                            String license_attachment = staffObject.getString("license_attachment");

                            editName.setText(fullname);
                            editEmail.setText(email);
                            editHpno.setText(mobileNumber);
                            txtProfession.setText(profession);
                            txtPlateNo.setText(plateno);
                            txtBikeModel.setText(bikeModel);
                            txtGender.setText(gender);

//                            editPassword.setText(password);
                            Glide.with(DriverUserInfoActivity.this)
                                    .load(Constants.ROOT_URL_IMAGE_DRIVER +"avatar/" + user_attachment)
                                    .into(imageUser);

                            Glide.with(DriverUserInfoActivity.this)
                                    .load(Constants.ROOT_URL_IMAGE_DRIVER +"bike/"+ bike_attachment)
                                    .into(imageUserBike);

                            Glide.with(DriverUserInfoActivity.this)
                                    .load(Constants.ROOT_URL_IMAGE_DRIVER +"license/"+ license_attachment)
                                    .into(imageUserLicense);


                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void updateUser(final int user_id, final String status) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_MANAGE_USER_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject =  new JSONObject(response);

//                            showToast(status);


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

                params.put("user_id", String.valueOf(user_id));
                params.put("status", status);
                params.put("type", "driver");


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


    private void showToast(String status) {

        Toast.makeText(DriverUserInfoActivity.this, "User " + status, Toast.LENGTH_SHORT).show();
        finish();
    }

}
