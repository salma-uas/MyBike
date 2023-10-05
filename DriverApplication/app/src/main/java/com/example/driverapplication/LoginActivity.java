package com.example.driverapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class LoginActivity extends AppCompatActivity {
    ImageView imageView;
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    Button mButtonRegister;
    Button mButtonHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonRegister = (Button)findViewById(R.id.button_Register);

        if (SharedPrefManager.getInstance(LoginActivity.this).isLoggedIn()){
            finish();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent (LoginActivity.this,RegisterActivity.class
                );
                startActivity(registerIntent);
            }
        });
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLogin();

            }
        });
    }

    private void userLogin()
    {
        final String username = mTextUsername.getText().toString().trim();
        final String pass = mTextPassword.getText().toString().trim();

        //SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        // String ip = sharedPrefManager.getIP();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject =  new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){



                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                jsonObject.getInt("id"),
                                                jsonObject.getString("fullname"),
                                                jsonObject.getString("gender")


                                        );

                                Intent homeIntent = new Intent (LoginActivity.this, HomeActivity.class
                                );
                                startActivity(homeIntent);

                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();


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

                params.put("username", username);
                params.put("password", pass);
                params.put("access", "driver");

                return params;
            }
        };


        Volley.newRequestQueue(this).add(stringRequest);

    }
}
