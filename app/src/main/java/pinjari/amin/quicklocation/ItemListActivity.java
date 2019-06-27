package pinjari.amin.quicklocation;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.pinjariamin.quickcurrenlocationlibrary.QuickLocation;
import com.pinjariamin.quickcurrenlocationlibrary.QuickLocationListener;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements LocationListener, QuickLocationListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private Button getLocationBtn;
    private TextView locationText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        initLocationFunction();
    }

    private void initLocationFunction() {
        getSupportActionBar().hide();

        getLocationBtn = findViewById(R.id.getLocationBtn);
        locationText = findViewById(R.id.locationText);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                QuickLocation.getCurrentLocation(ItemListActivity.this, ItemListActivity.this);
            }
        });
    }


    @SuppressLint("SetTextI18n")
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
        Toast.makeText(ItemListActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationFailed(String string) {
        Toast.makeText(this, "" + string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getLocationAddresses(Location location, List<Address> addresses) {
        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        Address returnAddress = addresses.get(0);

        String localityString = returnAddress.getLocality();
        String name = returnAddress.getFeatureName();
        String subLocality = returnAddress.getSubLocality();
        String country = returnAddress.getCountryName();
        String region_code = returnAddress.getCountryCode();
        String zipcode = returnAddress.getPostalCode();
        String state = returnAddress.getAdminArea();


        locationText.setText(locationText.getText() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
                addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2)
                + " localityString " + localityString
                + "\n name " + name
                + "\nsubLocality " + subLocality
                + "\ncountry " + country
                + "\nregion_code " + region_code
                + "\nzipcode " + zipcode
                + "\nstate " + state
        );

    }

}
