package com.pinjariamin.quickcurrenlocationlibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class QuickLocation {

    private static LocationManager locationManager;


    public static void getCurrentLocation(final Context context, final QuickLocationListener locationListener) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            try {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            } catch (Exception e) {
                locationListener.onLocationFailed(context.getString(R.string.permission_not_enabled));
            }
        } else {

            try {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            locationListener.getLocationAddresses(location, addresses);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        locationListener.onLocationChanged(location);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        locationListener.onStatusChanged(provider, status, extras);
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        locationListener.onProviderEnabled(provider);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        locationListener.onProviderDisabled(provider);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                locationListener.onLocationFailed(context.getString(R.string.something_went_wrong));
            }
        }
    }
}
