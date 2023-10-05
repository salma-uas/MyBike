package com.example.driverapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ManageProfileActivity extends AppCompatActivity {

    EditText input_fullname,input_hpno,input_email, input_password, input_plateNo,  input_bikeModel;
    RadioGroup radioGender;
    RadioButton radioFemale, radioMale, radioButton;
    ImageView imageUser, imageBikeUser, imageLicenseUser;

    Spinner input_profession;

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
        input_profession = findViewById(R.id.input_profession);
        input_hpno= (EditText)findViewById(R.id.input_hpno);
        input_email = (EditText)findViewById(R.id.input_email);
        input_password = (EditText)findViewById(R.id.input_password);
        input_plateNo = (EditText)findViewById(R.id.input_plateNo);
        input_bikeModel = (EditText)findViewById(R.id.input_bikeModel);
        imageUser = findViewById(R.id.imageUser);
        imageBikeUser = findViewById(R.id.imageBikeUser);
        imageLicenseUser = findViewById(R.id.imageLicenseUser);

        //Radio Group
        radioGender = findViewById(R.id.radioGroupGender);

        //Radio Gender
        radioFemale = findViewById(R.id.radioFemale);
        radioMale = findViewById(R.id.radioMale);


        //adding click listener to button
        findViewById(R.id.buttonUploadUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //if everything is ok we will open image chooser
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        //adding click listener to button
        findViewById(R.id.buttonUploadBike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //if everything is ok we will open image chooser
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 110);
            }
        });


        //adding click listener to button
        findViewById(R.id.buttonUploadLicense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //if everything is ok we will open image chooser
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 120);
            }
        });


        loadProfile();

    }


    private void loadProfile()
    {
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        int user_id = sharedPrefManager.getUserId();


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

                            input_fullname.setText(fullname);
                            input_email.setText(email);
                            input_hpno.setText(mobileNumber);
                            for (int i=0;i<input_profession.getCount();i++){
                                if (input_profession.getItemAtPosition(i).toString().equals(profession)){
                                    input_profession.setSelection(i);
                                }
                            }
                            input_plateNo.setText(plateno);
                            input_bikeModel.setText(bikeModel);

                            if (gender.equals("Male")){
                                radioMale.setChecked(true);
                            }
                            else if (gender.equals("Female")){
                                radioFemale.setChecked(true);
                            }


//                            editPassword.setText(password);
                            Glide.with(ManageProfileActivity.this)
                                    .load(Constants.ROOT_URL_IMAGE_DRIVER +"avatar/" + user_attachment)
                                    .into(imageUser);

                            Glide.with(ManageProfileActivity.this)
                                    .load(Constants.ROOT_URL_IMAGE_DRIVER +"bike/"+ bike_attachment)
                                    .into(imageBikeUser);

                            Glide.with(ManageProfileActivity.this)
                                    .load(Constants.ROOT_URL_IMAGE_DRIVER +"license/"+ license_attachment)
                                    .into(imageLicenseUser);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 100 || requestCode == 110 || requestCode == 120) && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                Bitmap.createScaledBitmap(bitmap, (int) 80, (int) 80, true);

                //displaying selected image to imageview
                if(requestCode == 100 ){
                    imageUser.setImageBitmap(bitmap);

                }

                if(requestCode == 110 ){
                    imageBikeUser.setImageBitmap(bitmap);

                }

                if(requestCode == 120 ){
                    imageLicenseUser.setImageBitmap(bitmap);

                }


                //calling the method uploadBitmap to upload image
                // registerUser(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void updateUser() {

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        final int id = sharedPrefManager.getUserId();

        // get selected radio button from radioGroup
        int selectedId = radioGender.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        final String fullname = input_fullname.getText().toString().trim();
        final String profession = input_profession.getSelectedItem().toString();
        final String gender = radioButton.getText().toString().trim();
        final String hpno = input_hpno.getText().toString().trim();
        final String email = input_email.getText().toString().trim();
        final String password = input_password.getText().toString().trim();
        final String plateNo = input_plateNo.getText().toString().trim();
        final String bikeModel = input_bikeModel.getText().toString().trim();
        final Bitmap image=((BitmapDrawable)imageUser.getDrawable()).getBitmap();
        final Bitmap imageBike=((BitmapDrawable)imageBikeUser.getDrawable()).getBitmap();
        final Bitmap imageLicense=((BitmapDrawable)imageLicenseUser.getDrawable()).getBitmap();

//
//        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
//
//        String ipaddress = sharedPrefManager.getIP();

        VolleyMultipartRequest stringRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.URL_UPDATE_PROFILE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
//                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));


                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            finish();



                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(id));
                params.put("fullname", fullname);
                params.put("gender", gender);
                params.put("mobilenumber", hpno);
                params.put("email", email);
                params.put("password", password);
                params.put("profession", profession);
                params.put("plateno", plateNo);
                params.put("bikeModel", bikeModel);
                params.put("status", "approved");

                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("avatar", new DataPart(imagename + ".png", getFileDataFromDrawable(image)));
                params.put("bike", new DataPart(imagename + ".png", getFileDataFromDrawable(imageBike)));
                params.put("license", new DataPart(imagename + ".png", getFileDataFromDrawable(imageLicense)));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onUpdate(View view) {

        checkInput();

    }


    private void checkInput() {

        String password = input_password.getText().toString();
        if (!input_email.getText().toString().trim().matches(emailPattern)) {
            input_email.setError("Invalid Email");
            input_password.requestFocus();
            return;
        }
        else if (input_fullname.getText().toString().trim().isEmpty()) {
            input_fullname.setError("Fullame is required");
            input_fullname.requestFocus();
            return;
        }
        else if (input_hpno.getText().toString().trim().isEmpty()) {
            input_hpno.setError("Contact No. is required");
            input_hpno.requestFocus();
            return;
        }
        else if (input_plateNo.getText().toString().trim().isEmpty()) {
            input_plateNo.setError("Plate No. is required");
            input_plateNo.requestFocus();
            return;
        }
        else if (input_bikeModel.getText().toString().trim().isEmpty()) {
            input_bikeModel.setError("Bike Model is required");
            input_bikeModel.requestFocus();
            return;
        }
        else if(!input_password.getText().toString().trim().isEmpty()) {

            if (input_password.getText().toString().length() < 6) {
                input_password.setError("6 characters minimum!");
                input_password.requestFocus();

            }
            else {

                updateUser();
            }
        }

        else {

            updateUser();
        }
    }



}
