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
    String ipaddress = "192.168.0.112";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Motoborike Application");

        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mButtonRegister = (Button)findViewById(R.id.button_Register);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent registerIntent = new Intent (LoginActivity.this,RegisterActivity.class
               );
               startActivity(registerIntent);

            }
        });


        if (SharedPrefManager.getInstance(LoginActivity.this).isLoggedIn()){
            finish();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }

    }

    private void userLogin()
    {
        final String username = mTextUsername.getText().toString().trim();
        final String pass = mTextPassword.getText().toString().trim();

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

         String ip = sharedPrefManager.getIP();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject =  new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){


                                SharedPrefManager.getInstance(getApplicationContext())
                                        .setIP((ipaddress));


                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                jsonObject.getInt("id"),
                                                jsonObject.getString("fullname"),
                                                jsonObject.getString("gender")


                                        );

                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                                finish();
                                Intent mainPage = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(mainPage);

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
                params.put("access", "passenger");

                return params;
            }
        };


        Volley.newRequestQueue(this).add(stringRequest);

    }

    /** Button clicked for user to set an Local IP Address **/
    public void onClickIP(View view){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your IP Address");

        final EditText input = new EditText(this);
        input.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setMessage(Html.fromHtml("Enter your IP Address from your PC\n" + "<b>" + "Example: 192.168.x.x" + "<b>"));

        builder.setView(input);



        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ipaddress = input.getText().toString();

            }
        });

        builder.setNegativeButton("Reset IP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    }
