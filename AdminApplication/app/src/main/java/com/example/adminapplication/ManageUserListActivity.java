package com.example.adminapplication;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

public class ManageUserListActivity extends AppCompatActivity {

    List<Manage_User> manage_users;
    RecyclerView recyclerView;
    ManageUserAdapter adapter;

    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Manage User");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        manage_users = new ArrayList<>();


        Intent intent = getIntent();
        type = intent.getStringExtra("type");


        loadUsers(type);



    }
    private void loadUsers(final String type)
    {

        // StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_USERS,
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_MANAGE_USER_LIST + type,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray previous =  new JSONArray(response);

                            for(int i=0; i<previous.length(); i++){
                                JSONObject previousObbject =  previous.getJSONObject(i);

                                int id = previousObbject.getInt("id");
                                String fullname = previousObbject.getString("fullname");
                                String mobileNumber = previousObbject.getString("mobileNumber");
                                String emailaddress = previousObbject.getString("emailaddress");
                                String pic = previousObbject.getString("pic");
                                String gender = previousObbject.getString("gender");

                                Manage_User manageUsers = new Manage_User(id, fullname, emailaddress, mobileNumber, gender, pic, type);
                                manage_users.add(manageUsers);


                            }

                            //creating recyclerview adapter
                            adapter = new ManageUserAdapter(ManageUserListActivity.this, manage_users);

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
