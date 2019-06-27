package com.pinjariamin.quickcurrenlocationlibrary;

import android.location.Address;
import android.location.Location;
import android.os.Bundle;

import java.util.List;

public interface QuickLocationListener {

    void onLocationChanged(Location location);


    void onStatusChanged(String provider, int status, Bundle extras);


    void onProviderEnabled(String provider);


    void onProviderDisabled(String provider);

    void onLocationFailed(String errorMessage);

    void getLocationAddresses(Location location, List<Address> addresses);
}
