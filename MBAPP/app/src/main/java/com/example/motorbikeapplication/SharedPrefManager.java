package com.example.motorbikeapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_ID = "id";
    private static final String KEY_BOOKED = "booked";

    private SharedPrefManager(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String email, String gender){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID, id);
        editor.putString(KEY_EMAIL, email);
        editor.putString("gender", gender);


        editor.apply();

        return  true;

    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.getString("gender", null) != null){
            return true;
        }

        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;


    }

    public boolean removeCurrentBooking(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("booking_id");
        editor.remove("location");
        editor.remove("destination");
        editor.remove("status");
        editor.remove("passenger_id");
        editor.remove("price");
//        editor.remove("min");
//        editor.remove("sec");
        editor.remove(KEY_BOOKED);



        editor.commit();

        return true;

    }

    public boolean removeTimer(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("min");
        editor.remove("sec");


        editor.commit();

        return true;

    }



    public void setTimer(int min, int sec){
        SharedPreferences.Editor sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
        sharedPreferences.putInt("min", min);
        sharedPreferences.putInt("sec", sec);

        sharedPreferences.apply();

    }

    public int getMin(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("min", 0);

    }

    public int getSec(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("sec", 0);

    }

    public boolean isTimer(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.getInt("min", 0) != 0 && sharedPreferences.getInt("sec", 0) != 0){
            return true;
        }

        return false;
    }


    public String getIP(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("ip", null);

    }

    public int getID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", 0);

    }


    public void currentBooking(int booking_id, String location, String destination, String status, int passenger_id, int rider_id, String price){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("booking_id", booking_id);
        editor.putString("location", location);
        editor.putString("destination", destination);
        editor.putString("status", status);
        editor.putInt("passenger_id", passenger_id);
        editor.putString("price", price);
        editor.putString(KEY_BOOKED, "booked");


        editor.apply();

    }

    public boolean isBooked(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.getString(KEY_BOOKED, null) != null){
            return true;
        }

        return false;
    }


    public String getLocations(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("location", null);

    }


    public String getDestination(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("destination", null);

    }

    public String getStatus(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("status", null);

    }

    public int getPassenger_id(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("passenger_id", 0);

    }

    public int getRider_id(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("rider_id", 0);

    }


    public int getBookingId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("booking_id", 0);

    }



    public String getPrice(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("price", null);

    }

    public void setIP(String m_Text){
        SharedPreferences.Editor sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();

        sharedPreferences.putString("ip", m_Text);

        sharedPreferences.apply();

    }






}
