package com.example.project_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;

public class login extends AppCompatActivity
{
    EditText pass;
    AutoCompleteTextView email;
    Button b,b2;
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sqlite data = new sqlite(this);
        String[] auto=data.get();
        email=findViewById(R.id.emaillogin);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(login.this,android.R.layout.simple_list_item_1,auto);
            email.setThreshold(0);
        email.setAdapter(adapter);
    }
    public void verify(View view)
    {
        email=(AutoCompleteTextView)findViewById(R.id.emaillogin);
        pass=(EditText)findViewById(R.id.pass);
        b=(Button)findViewById(R.id.button3);
        sqlite data = new sqlite(this);
        mail=email.getText().toString();
        String pa=pass.getText().toString();
        Boolean vre=data.verifydata(mail,pa);
        if(vre)
        {
            Intent intent = new Intent(login.this, dashboard.class);
            intent.putExtra("m",mail);
            clear();
            startActivity(intent);
        }
        else
        {
            AlertDialog.Builder a = new AlertDialog.Builder(this);
            a.setMessage("Credentials not match")
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            dialogInterface.dismiss();
                        }
                    })
                    .create();
            a.show();
        }
    }
    public void reg(View view)
    {
        b2=(Button)findViewById(R.id.b2);
        Intent in = new Intent(login.this, MainActivity.class);
        startActivity(in);
    }
    public void clear()
    {
        email.setText("");
        pass.setText("");
    }
}