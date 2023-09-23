package com.example.adminapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class ManageProfile extends AppCompatActivity {
    EditText input_fullname, input_email, input_password;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Manage Profile");
        input_fullname = (EditText)findViewById(R.id.input_fullname);
        input_email = (EditText)findViewById(R.id.input_email);
        input_password = (EditText)findViewById(R.id.input_password);

        loadProfile();
    }

    private void loadProfile()
    {

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        int user_id = sharedPrefManager.getUserId();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Constants.URL_GET_PROFILE + user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray staffs =  new JSONArray(response);
                            JSONObject staffObject =  staffs.getJSONObject(0);

                            int id = staffObject.getInt("id");
                            String fullname = staffObject.getString("fullname");
                            String email = staffObject.getString("email");


                            input_fullname.setText(fullname);
                            input_email.setText(email);


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

    private void updateUser() {

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        final int user_id = sharedPrefManager.getID();
        final String fullname = input_fullname.getText().toString().trim();
        final String email = input_email.getText().toString().trim();
        final String password = input_password.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (!jsonObject.getBoolean("error")) {



                                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();
                                finish();


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

                params.put("id", String.valueOf(user_id));
                params.put("email", email);
                params.put("password", password);
                params.put("fullname", fullname);

                return params;
            }
        };


        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void checkInput() {

        String password = input_password.getText().toString();
        if (!input_email.getText().toString().trim().matches(emailPattern)) {
            input_email.setError("Invalid Email");
            input_password.requestFocus();
            return;
        }
        else if (input_password.getText().toString().trim().isEmpty()) {
            input_password.setError("Password is required for login");
            input_password.requestFocus();
            return;
        }
        else if (input_password.getText().toString().length() < 4) {
            input_password.setError("6 characters minimum!");
        }


        else {

            updateUser();
        }
    }

    public void onUpdate(View view) {

        checkInput();

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
