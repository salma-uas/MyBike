package com.example.adminapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddRouteActivity extends AppCompatActivity {

    EditText editPickupPoint,editPickupCoordinate,editDestinationPoint,editDestinationCoordinate,editPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Route");

        editPickupPoint = findViewById(R.id.editPickupPoint);
        editDestinationPoint = findViewById(R.id.editDestinationPoint);
        editDestinationCoordinate = findViewById(R.id.editDestinationCoordinate);
        editPrice = findViewById(R.id.editPrice);

    }

    private void addRoute() {

        final String pickup_point = editPickupPoint.getText().toString().trim();
        final String destination_point = editDestinationPoint.getText().toString().trim();
        final String destination_coordinate = editDestinationCoordinate.getText().toString().trim();
        final String price = editPrice.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_ROUTE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (!jsonObject.getBoolean("error")) {

                                Toast.makeText(getApplicationContext(), "Route has been added", Toast.LENGTH_LONG).show();
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

                params.put("pickup_point", pickup_point);
                params.put("pickup_coordinate", "0");
                params.put("destination_point", destination_point);
                params.put("destination_coordinate",destination_coordinate);
                params.put("price", price);

                return params;
            }
        };


        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void onAddRoute(View view){

        addRoute();

    }
}
