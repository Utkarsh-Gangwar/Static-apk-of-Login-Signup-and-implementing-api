package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class dashboard extends AppCompatActivity
{
    String key="";
    double lat,lon;
    String url="";
    Button db,gloc;
    TextView tv;
    Runnable runnable;
    private long l=5*1000;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sqlite sq=new sqlite(this);
        tv=(TextView)findViewById(R.id.wel);
        String st=sq.data(getIntent().getStringExtra("m"));
        tv.setText("welcome "+st+"\nLets find out today's weather");
        handler = new Handler();
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(dashboard.this,"you were inactive",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(dashboard.this,login.class);
                startActivity(intent);
            }
        };
        srhand();
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        sphand();
        srhand();
    }
    public void sphand()
    {
        handler.removeCallbacks(runnable);
    }
    public void srhand()
    {
        handler.postDelayed(runnable,l);
    }
    public void wether(View view) {
        EditText det = (EditText) findViewById(R.id.wet);
        db = (Button) findViewById(R.id.b);
        key = det.getText().toString();
        url = "https://api.openweathermap.org/data/2.5/weather?q=" + key + "&appid=557fadcd9ec98ee9fc4181cfc83a0481";
        search_wether();
    }
    public void search_wether() {
        tv =(TextView)findViewById(R.id.textView);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject main=response.getJSONObject("main");
                            String city=response.getString("name");
                            float temprature= (float) ((main.getDouble("temp"))-273.15);
                            float temprature_min= (float) ((main.getDouble("temp_min"))-273.15);
                            float temprature_max= (float) ((main.getDouble("temp_max"))-273.15);
                            float feels_like=(float)(main.getDouble("feels_like")-273.15);
                            System.out.println(url);
                            tv.setText("temprature        :- "+temprature+
                                    "\nfeels like         :-"+feels_like+
                                    "\nmin Temprature :-"+temprature_min+
                                    "\nmax Temprature :-"+temprature_max+
                                    "\nCity               :-"+city);
                            System.out.println("temprature :- "+temprature+
                                    "\nfeels like :-"+feels_like+
                                    "\nCity     :-"+city);
                        } catch (JSONException e) {
                            System.out.println("this1");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    tv.setText("Enter correct city name or Search by location");
                    error.printStackTrace();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(dashboard.this);
        requestQueue.add(jsonObjectRequest);
    }
    public void getloc(View view) {
        gloc=(Button) findViewById(R.id.loc);
        ActivityCompat.requestPermissions(dashboard.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        gpstrack g=new gpstrack(getApplicationContext());
        Location l=g.getlocation();
        if (l!=null)
        {
            lat=l.getLatitude();
            lon=l.getLongitude();
            System.out.println("getLatitude:--"+lat+"\ngetLongitude"+lon);
            url="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=557fadcd9ec98ee9fc4181cfc83a0481";
            search_wether();
        }
    }
}