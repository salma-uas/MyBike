package com.example.driverapplication;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GenerateReport extends AppCompatActivity {

    private TextView txtCompletedBooking,txtProfit;
    int total_completed = 0, total_profit = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Generate Report");

        loadReport();

    }

    private void loadReport()
    {
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        int user_id = sharedPrefManager.getUserId();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, Constants.URL_GET_REPORT + user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        BarChart chart = findViewById(R.id.barchart);
                        txtCompletedBooking = findViewById(R.id.txtCompletedBooking);
                        txtProfit = findViewById(R.id.txtProfit);

                        try {
                            JSONArray previous =  new JSONArray(response);
                            List<BarEntry> entries = new ArrayList<>();

                            for(int i=0; i<previous.length(); i++){
                                JSONObject previousObbject =  previous.getJSONObject(i);

                                int completed = previousObbject.getInt("completed");
                                int month = previousObbject.getInt("month");
                                int total = previousObbject.getInt("total");



                                entries.add(new BarEntry(0, 0));
                                entries.add(new BarEntry(1, 0));
                                entries.add(new BarEntry(2, 0));
                                entries.add(new BarEntry(3, 0));
                                entries.add(new BarEntry(4, 0));
                                entries.add(new BarEntry(5, 0));
                                entries.add(new BarEntry(6, 0));
                                entries.add(new BarEntry(7, 0));
                                entries.add(new BarEntry(8, 0));
                                entries.add(new BarEntry(9, 0));
                                entries.add(new BarEntry(10, 0));
                                entries.add(new BarEntry(11, 0));


                                entries.add(new BarEntry(month-1, total));

                                total_completed += completed;
                                total_profit += total;

                            }

                            txtCompletedBooking.setText(String.valueOf(total_completed));
                            txtProfit.setText("TK" +String.valueOf(total_profit));

                            // Look-up table
                            final String[] weekdays = {"Jan", "Feb", "Mar", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

                            XAxis xAxis = chart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(weekdays));

                            BarDataSet set = new BarDataSet(entries, "Months");

                            chart.getDescription().setText("Profit collected within a months");

                            chart.getDescription().setTextSize(16f);

                            chart.getDescription().setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_dark)); //the color of the font

                            chart.getLegend().setTextSize(25f);
                            set.setColors(new int[] {Color.RED, Color.GREEN, Color.GRAY, Color.BLACK, Color.BLUE});


                            BarData data = new BarData(set);
                            chart.animateY(1400, Easing.EaseInOutQuad);
                            chart.animateXY(3000, 3000);
                            data.setBarWidth(0.9f); // set custom bar width
                            chart.setData(data);
                            chart.setFitBars(true); // make the x-axis fit exactly all bars
                            chart.invalidate(); // refresh

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
