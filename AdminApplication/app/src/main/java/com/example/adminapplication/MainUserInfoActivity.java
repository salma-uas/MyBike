package com.example.adminapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class MainUserInfoActivity extends AppCompatActivity {

    ImageView imageUser;
    TextView editName, editEmail,editHpno,editUserID, editFaculty,txtGender,txtUsername;
    Button btnApproved, btnReject;

    private String imgUrl = Constants.ROOT_URL_IMAGE;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Manage Profile");
        editName = findViewById(R.id.editName);
        editEmail =findViewById(R.id.editEmail);
        editHpno= findViewById(R.id.editHpno);
        editUserID = findViewById(R.id.editUserID);
        editFaculty = findViewById(R.id.editFaculty);
        txtUsername = findViewById(R.id.txtUsername);
        txtGender = findViewById(R.id.txtGender);
        imageUser = findViewById(R.id.imageUser);
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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_MANAGE_USER_VIEW + user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray staffs =  new JSONArray(response);
                            JSONObject staffObject =  staffs.getJSONObject(0);

                            int id = staffObject.getInt("id");
                            String fullname = staffObject.getString("fullname");
                            String studentid = staffObject.getString("studentid");
                            String username = staffObject.getString("username");
                            String faculty = staffObject.getString("faculty");
                            String mobileNumber = staffObject.getString("mobileNumber");
                            String emailAddress = staffObject.getString("emailAddress");
                            String password = staffObject.getString("password");
                            String pic = staffObject.getString("pic");
                            String gender = staffObject.getString("gender");

                            editName.setText(fullname);
                            editEmail.setText(emailAddress);
                            editHpno.setText(mobileNumber);
                            editUserID.setText(studentid);
                            editFaculty.setText(faculty);
                            txtUsername.setText(username);
                            txtGender.setText(gender);

//                            editPassword.setText(password);
                            Glide.with(MainUserInfoActivity.this)
                                    .load(imgUrl + pic)
                                    .into(imageUser);


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

                                Toast.makeText(getApplicationContext(), "User " + status, Toast.LENGTH_LONG).show();


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
                params.put("type", "passenger");


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

        Toast.makeText(MainUserInfoActivity.this, "User " + status, Toast.LENGTH_SHORT).show();
        finish();
    }


}
