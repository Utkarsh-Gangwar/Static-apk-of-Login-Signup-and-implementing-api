package com.example.project_1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class gpstrack implements LocationListener {
    Context context;
    public gpstrack(Context c){
        context=c;
    }
    public Location getlocation(){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context,"permition not granted",Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isgpsactive = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isgpsactive){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
            Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return location;
        }else {
            Toast.makeText(context,"please enable gps",Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
