package com.goodsnextdoor.pooja.goodsnextdoor;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.Profile;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

public class selectActivity extends Activity implements LocationListener {
   String p;
    String q;
    String t;
    item i=new item();
    MobileServiceTable<item> table;
    protected LocationManager locationManager;
    protected Context context;
    private double latitude = 0;
    private double longitude = 0;
    int flag;
    //TextView lat,lng;
    boolean isGPSEnabled = false;
    Location location; // location
    item it=new item();
    String[] itemnames=new String[50];
    String[] itemcity=new String[50];
    Double[] lats=new Double[50];
    Double[] longs=new Double[50];
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
    MobileServiceList<item> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
         p=getIntent().getStringExtra("category");
        q=getIntent().getStringExtra("itemname");
        try {



            mClient = new MobileServiceClient("https://goodsnextdoorcapstone.azure-mobile.net/","IrDKWwuYiCMcDatgBeOzklZKeOINDD73",this);

            // Get the Mobile Service Table instance to use
            mitem = mClient.getTable(item.class);
        } catch (MalformedURLException e) {
            new Exception("There was an error creating the Mobile Service. Verify the URL");
        }


        dialog = new ProgressDialog(selectActivity.this);
        dialog.show();
        dialog.setMessage("Getting Location");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ActivityCompat.requestPermissions(selectActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
        if (ContextCompat.checkSelfPermission(selectActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(selectActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(selectActivity.this,
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

                    List<String> providers = locationManager.getProviders(true);
                    Location bestLocation = null;
                    for (String provider : providers) {
                        Location l = locationManager.getLastKnownLocation(provider);

                        if (l == null) {
                            continue;
                        }
                        if (bestLocation == null
                                || l.getAccuracy() < bestLocation.getAccuracy()) {
                            bestLocation = l;
                        }
                    }
                    if (bestLocation == null) {
                        location= null;
                    }
                    else
                        location=bestLocation;

                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        if (latitude != 0 && longitude != 0){
                            try {
                                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                cityName = addresses.get(0).getAddressLine(0);
                                stateName = addresses.get(0).getAddressLine(1);
                                countryName = addresses.get(0).getAddressLine(2);

                                // city.setText(cityName);
                                //state.setText(stateName);
                                //country.setText(countryName);
                            }
                            catch(Exception e)
                            {

                            }
                            dialog.dismiss();
                        }
                    }
                }
            }
        }
    }

    public void home(View v)
    {
        Intent  intent = new Intent(selectActivity.this, optionsActivity.class);
        startActivity(intent);
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

                // city.setText(cityName);
                //state.setText(stateName);
                //country.setText(countryName);
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
    public void Maps(View view)
    {   new AsyncTask<Void, Void, Void>() {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                final MobileServiceList<item> results = mitem.where().field("category").eq(p).execute().get();

                for (item iem : results) {
                    t+=iem.getname().toString();
                }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }.execute();

        Intent  intent = new Intent(selectActivity.this, MapsActivity.class);
        intent.putExtra("category", p);
        intent.putExtra("itemname", q);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        startActivity(intent);
    }
   public void list(View view)
    {           new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final MobileServiceList<item> results = mitem.where().field("category").eq(p).execute().get();

                    for (item iem : results) {
                        t+=iem.getname().toString();
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();



        Intent intent = new Intent(selectActivity.this, listActivity.class);
        intent.putExtra("category", p);
        intent.putExtra("itemname", q);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);

        startActivity(intent);
    }


}
