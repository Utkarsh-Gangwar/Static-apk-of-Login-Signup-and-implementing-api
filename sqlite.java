package com.example.project_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class sqlite<cursor> extends SQLiteOpenHelper
{
    SQLiteDatabase db;
    public sqlite(@Nullable Context context) {
        super(context, "employee.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table emp (firstname TEXT, lastname TEXT, email TEXT NOT NULL PRIMARY KEY, phone TEXT, address TEXT, pasword TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    public Boolean adddata(String fname,String lname, String email,String phoneno,String address, String pass) {
        db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("firstname",fname);
        value.put("lastname",lname);
        value.put("email",email);
        value.put("phone",phoneno);
        value.put("address",address);
        pass=md5(pass);
        value.put("pasword",pass);
        long l =db.insert("emp",null,value);
        if(l==-1)
            return Boolean.FALSE;
        else
            return Boolean.TRUE;
    }
    public Boolean verifydata(String mail,String pass) {
        db = this.getReadableDatabase();
        String project[]={"fname","lname","email","pass"};
        pass=md5(pass);
        Cursor cursor=db.rawQuery("SELECT  * FROM emp WHERE email=? AND pasword=?",new String[] {mail,pass});
        if(cursor.getCount()>0)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }
    public String data(String s)
    {
        db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT  * FROM emp WHERE email=?",new String[]{s});
        cursor.moveToFirst();
        return (cursor.getString(0)+" "+cursor.getString(1));
    }
    public static final String md5(final String s)
    {
        try
        {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexstr = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xff & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexstr.append(h);
            }
            return hexstr.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    public String[] get()
    {
        int i=0;
        db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT email FROM EMP",null);
        String[] s=new String[cursor.getCount()];
//        System.out.println("number-----------------------"+cursor.getString(0));
        while (cursor.moveToNext())
        {
//            System.out.println("number-----------------------"+cursor.getString(0));
            s[i]=cursor.getString(0);
            i++;
        }
        System.out.println("hiii--------"+ Arrays.toString(s));
        return s;
    }
}