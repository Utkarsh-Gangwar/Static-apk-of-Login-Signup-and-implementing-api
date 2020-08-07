package com.example.project_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    EditText firstname,lastname,phone,address,password,cpassword;
    AutoCompleteTextView email;
    Button button,logi;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                int oucrence=0;
                String enterstring=charSequence.toString();
                for (char c : enterstring.toCharArray())
                {
                    if (c == '@')
                    {
                        oucrence++;
                    }
                }
                if (oucrence == 1) {
                    String requiredString = enterstring.substring(0, enterstring.indexOf("@"));
                    String[] em=new String[]{requiredString + "@gmail.com",requiredString + "@hotmail.com",requiredString + "@yahoo.com"};
                    adapter = null;
                    adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,em);
                    email.showDropDown();
                    email.setThreshold(0);
                    email.setAdapter(adapter );
                } else if (oucrence == 0) {
                    email.dismissDropDown();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        phone = (EditText)findViewById(R.id.phone);
        address = (EditText)findViewById(R.id.address);
        password = (EditText)findViewById(R.id.password);
        cpassword = (EditText)findViewById(R.id.cpassword);
        button = (Button)findViewById(R.id.button);
        logi = (Button)findViewById(R.id.logi);
    }
    public void showAlert(View v)
    {
        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String mail = email.getText().toString();
        String phno = phone.getText().toString();
        String home = address.getText().toString();
        String pass = password.getText().toString();
        String cpass = cpassword.getText().toString();

        Boolean ph = phno.matches("[0-9]{10}");
        Boolean ma = mail.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");;
        Boolean pa = valpass(pass) && cpass.equals(pass);
        if(ph && ma && pa)
        {
            sqlite data = new sqlite(this);
            Boolean b = data.adddata(fname,lname,mail,phno,home,pass);
            if(b)
            {
                AlertDialog.Builder a = new AlertDialog.Builder(this);
                a.setMessage("Form Submit Successful")
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
                clear();
            }
            else
            {
                AlertDialog.Builder a = new AlertDialog.Builder(this);
                a.setMessage("you are already registered")
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
        else
        if(!ph)
        {
            AlertDialog.Builder a = new AlertDialog.Builder(this);
            a.setMessage("please enter proper number")
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
        else if (!ma)
        {
            AlertDialog.Builder a = new AlertDialog.Builder(this);
            a.setMessage("please enter proper Email")
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
        else
        {
            AlertDialog.Builder a = new AlertDialog.Builder(this);
            a.setMessage("Password and Confirm Password Don't match")
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
    public Boolean valpass(String pass)
    {
        int leng = pass.length();
        if(leng>5 && leng<32)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }
    public void lin(View view)
    {
        Intent intent = new Intent(MainActivity.this,login.class);
        startActivity(intent);
    }
    public void clear()
    {
        firstname.setText("");
        lastname.setText("");
        email.setText("");
        phone.setText("");
        address.setText("");
        password.setText("");
        cpassword.setText("");
    }
}
