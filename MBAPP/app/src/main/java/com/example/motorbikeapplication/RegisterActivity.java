package com.example.motorbikeapplication;

import android.app.AuthenticationRequiredException;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity{
    ImageView imageUser;
    EditText editName, editEmail,editHpno,editUserID, editPassword;
    RadioGroup radioGender;
    RadioButton radioFemale, radioMale, radioButton;
    TextView txtUsername;

    Spinner editFaculty;

    private String imgUrl = Constants.ROOT_URL_IMAGE;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Register");
        editName = (EditText)findViewById(R.id.editName);
        editEmail =(EditText)findViewById(R.id.editEmail);
        editHpno= (EditText)findViewById(R.id.editHpno);
        editUserID = (EditText)findViewById(R.id.editUserID);
        editFaculty = findViewById(R.id.editFaculty);
        editPassword = (EditText)findViewById(R.id.editPassword);
        txtUsername = findViewById(R.id.txtUsername);
        imageUser = findViewById(R.id.imageUser);

        String[] facultys = getApplicationContext().getResources().getStringArray(R.array.faculty);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,facultys
        );

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        editFaculty.setAdapter(spinnerArrayAdapter);

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


    }
    private void registerUser() {



        // get selected radio button from radioGroup
        int selectedId = radioGender.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        final String fullname = editName.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        final String gender = radioButton.getText().toString().trim();
        final String hpno = editHpno.getText().toString().trim();
        final String user_id = editUserID.getText().toString().trim();
        final String faculty = editFaculty.getSelectedItem().toString();
        final String username = txtUsername.getText().toString().trim();


        final Bitmap image=((BitmapDrawable)imageUser.getDrawable()).getBitmap();


        VolleyMultipartRequest stringRequest = new VolleyMultipartRequest(Request.Method.POST,Constants.URL_REGISTER ,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        try {


                            JSONObject jsonObject =  new JSONObject(new String(response.data));



                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

//                            error = jsonObject.getString("message");




//                            finish();


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
                params.put("studentid", user_id);
                params.put("username", username);
                params.put("faculty", faculty);
                params.put("mobileNumber", hpno);
                params.put("emailAddress", email);
                params.put("password", password);
                params.put("gender", gender);

                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(image)));
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

    public void onSignUp(View view) {

        checkInput();
//        finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                Bitmap.createScaledBitmap(bitmap, (int) 80, (int) 80, true);

                //displaying selected image to imageview
                imageUser.setImageBitmap(bitmap);


                //calling the method uploadBitmap to upload image
                // registerUser(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * The method is taking Bitmap as an argument
     * then it will return the byte[] array for the given bitmap
     * and we will send this array to the server
     * here we are using PNG Compression with 80% quality
     * you can give quality between 0 to 100
     * 0 means worse quality
     * 100 means best quality
     * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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


    private void checkInput() {

        String password = editPassword.getText().toString();

        if (!editEmail.getText().toString().trim().matches(emailPattern)) {
            editEmail.setError("Invalid Email");
            editPassword.requestFocus();
            return;
        }



        //note

        else if (editHpno.getText().toString().length() < 11) {
            editHpno.setError(" Enter valid phone number");
            editHpno.requestFocus();

        }
        else if (editUserID.getText().toString().length() < 13) {
            editUserID.setError(" Enter PUC Student_ID");
            editUserID.requestFocus();

        }









        else if (editPassword.getText().toString().trim().isEmpty()) {
            editPassword.setError("Password is required for login");
            editPassword.requestFocus();
            return;
        }
        else if (editPassword.getText().toString().length() < 6) {
            editPassword.setError("6 characters minimum!");
            editPassword.requestFocus();

        }


        else {

            registerUser();
            Toast.makeText(getApplicationContext(), "Account Successfully created!", Toast.LENGTH_LONG).show();
            finish();



        }
    }

}
