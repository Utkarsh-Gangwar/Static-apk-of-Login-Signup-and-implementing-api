package com.example.project_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class dashboard extends AppCompatActivity
{
    Runnable runnable;
    private long l=5*1000;
    TextView tv;
    EditText ed;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sqlite sq=new sqlite(this);
        tv=(TextView)findViewById(R.id.wel);
        ed=(EditText)findViewById(R.id.emaillogin);
        String st=sq.data(getIntent().getStringExtra("m"));
        tv.setText("welcome "+st+" to hey!foodie");
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
}