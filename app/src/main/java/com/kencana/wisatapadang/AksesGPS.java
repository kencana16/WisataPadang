package com.kencana.wisatapadang;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AksesGPS implements LocationListener {
    Context context;
    Location loc;

    public AksesGPS(Context ctx){
        this.context=ctx;
    }

    public Location ambilLokasi(){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        }else{
            Log.i("GPS Eror","Non Aktif ACCESS_COARSE_LOCATION");
        }
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 50, this);
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return loc;
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
