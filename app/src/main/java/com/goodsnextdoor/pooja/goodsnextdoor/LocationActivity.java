package com.goodsnextdoor.pooja.goodsnextdoor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends Activity implements LocationListener{
    protected LocationManager locationManager;
    protected Context context;
    private double latitude = 0;
    private double longitude = 0;
    int flag;
    item it=new item();
    //TextView lat,lng;
    boolean isGPSEnabled = false;
    Location location; // location
    TextView city;
    TextView state;
    TextView country;
    TextView tag,input;
    Profile d;
    String cityName;
    String stateName;
    String countryName;
    private MobileServiceTable<item> mitem;
    private MobileServiceClient mClient;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    String item,category,description;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag=0;
        setContentView(R.layout.activity_location);
        Profile.fetchProfileForCurrentAccessToken();
        Profile p=Profile.getCurrentProfile();
        while(p==null) {
            try {
                Thread.sleep(2000);
                Profile.fetchProfileForCurrentAccessToken();
                p=Profile.getCurrentProfile();
            } catch (Exception e) {
                Log.d("Exeption", e.toString());
            }
        }
        d=p.getCurrentProfile();
        it.setuid(d.getId());
        try {

            mClient = new MobileServiceClient("https://goodsnextdoorproject.azure-mobile.net/","wfPWzbslQQqWgCwgYRzGTzRbXeYBLj14",this);
            // Get the Mobile Service Table instance to use
            mitem = mClient.getTable(item.class);
        } catch (MalformedURLException e) {
            new Exception("There was an error creating the Mobile Service. Verify the URL");
        }
        item= getIntent().getStringExtra("item");
        it.setitem(item);
        category= getIntent().getStringExtra("category");
        it.setcategory(category);
        description= getIntent().getStringExtra("description");
        it.setdescription(description);
        city=(TextView)findViewById(R.id.city);
        state=(TextView)findViewById(R.id.state);
        country=(TextView)findViewById(R.id.country);
        tag=(TextView)findViewById(R.id.loctag);
        input=(TextView)findViewById(R.id.locin);
        dialog = new ProgressDialog(LocationActivity.this);
        dialog.show();
        dialog.setMessage("Getting Location");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ActivityCompat.requestPermissions(LocationActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
        if (ContextCompat.checkSelfPermission(LocationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LocationActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(LocationActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            if (location == null) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("GPS Enabled", "GPS Enabled");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
        }
    }

    public void yes(View v){
        flag=0;
    }

    public void no(View v){
        flag=1;
        tag.setVisibility(View.VISIBLE);
        input.setVisibility(View.VISIBLE);
    }
    public void submit(View v){
        if(flag==1)
        {
            it.setcity(input.getText().toString());
            it.setstate("");
            it.setcountry("");
            it.setlongitude(longitude);
            it.setlatitude(latitude);

        }
        else
        {
            it.setcity(cityName);
            it.setstate(stateName);
            it.setcountry(countryName);
            it.setlatitude(latitude);
            it.setlongitude(longitude);
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mitem.insert(it).get();
                    }
                catch (Exception exception) {
                    new Exception("Error");
                }
                return null;
            }
        }.execute();


        // cancel the alert box and put a Toast to the user
        Toast.makeText(getApplicationContext(), "Your data is saved in the database",

                Toast.LENGTH_LONG).show();





    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        dialog.show();
        latitude = location.getLatitude();
        longitude =location.getLongitude();
        if (latitude != 0 && longitude != 0){
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                cityName = addresses.get(0).getAddressLine(0);
                stateName = addresses.get(0).getAddressLine(1);
                countryName = addresses.get(0).getAddressLine(2);

                city.setText(cityName);
                state.setText(stateName);
                country.setText(countryName);
            }
            catch(Exception e)
            {

            }
            dialog.dismiss();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}