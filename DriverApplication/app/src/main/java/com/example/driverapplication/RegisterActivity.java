package com.example.driverapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText input_fullname,input_hpno,input_email, input_password, input_plateNo,  input_bikeModel;
    RadioGroup radioGender;
    RadioButton radioFemale, radioMale, radioButton;
    ImageView imageUser, imageBikeUser, imageLicenseUser;

    Spinner input_profession;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Register Account");
        input_fullname = (EditText)findViewById(R.id.input_fullname);
        input_profession = findViewById(R.id.input_profession);
        input_hpno= (EditText)findViewById(R.id.input_hpno);
        input_email = (EditText)findViewById(R.id.input_email);
        input_password = (EditText)findViewById(R.id.input_password);
        input_plateNo = (EditText)findViewById(R.id.input_plateNo);
        input_bikeModel = (EditText)findViewById(R.id.input_bikeModel);
        //initializing views
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

    private void registerUser() {

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

        VolleyMultipartRequest stringRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", fullname);
                params.put("gender", gender);
                params.put("mobilenumber", hpno);
                params.put("email", email);
                params.put("password", password);
                params.put("profession", profession);
                params.put("plateno", plateNo);
                params.put("bikeModel", bikeModel);
                params.put("status", "pending");

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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /** Button clicked for user to set an Local IP Address **/
    public void onClickRegister(View view){


        checkInput();



    }

    private void showToast() {

        Toast.makeText(RegisterActivity.this, "Account Successfully requested. Please wait for approval", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void checkInput() {

        String password = input_password.getText().toString();




            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Term & Conditions");

            builder.setMessage(Html.fromHtml("" +
                    "This site is provided as a service to our visitors and may be used for informational purposes only. Because the Terms and Conditions contain legal obligations, please read them carefully." +
                    "<br><br>" + "<b>" + "1. YOUR AGREEMENT" + "</b>" +
                    "<br>"  + "PLEASE NOTE: We reserve the right, at our sole discretion, to change, modify or otherwise alter these Terms and Conditions at any time. Unless otherwise indicated, amendments will become effective immediately. Please review these Terms and Conditions periodically. Your continued use of the Site following the posting of changes and/or modifications will constitute your acceptance of the revised Terms and Conditions and the reasonableness of these standards for notice of changes. For your information, this page was last updated as of the date at the top of these terms and conditions." +
                    "<br><br>" + "<b>" + "2. PRIVACY" + "</b>" +
                    "<br>"  + "Please review our Privacy Policy, which also governs your visit to this Site, to understand our practices." +
                    "<br><br>" + "<b>" + "3. LINKED SITES" + "</b>" +
                    "<br>"  + "SOME JURISDICTIONS DO NOT ALLOW THE EXCLUSION OF CERTAIN WARRANTIES OR THE LIMITATION OR EXCLUSION OF LIABILITY FOR INCIDENTAL OR CONSEQUENTIAL DAMAGES. ACCORDINGLY, OUR LIABILITY IN SUCH JURISDICTION SHALL BE LIMITED TO THE MAXIMUM EXTENT PERMITTED BY LAW."

            ));



            builder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

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
                    else if (input_password.getText().toString().length() < 6) {
                        input_password.setError("6 characters minimum!");
                        input_password.requestFocus();

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
                    else{
                        registerUser();
                        showToast();
                    }



                }
            });

            builder.setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();


    }




}
