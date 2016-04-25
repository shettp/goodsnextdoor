package com.goodsnextdoor.pooja.goodsnextdoor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback{

    private GoogleMap mMap;
    double latitude,longitude;
    private MobileServiceTable<item> mitem;
    private MobileServiceClient mClient;
    int totalitems=0;
    // Array of strings storing country names

    item it=new item();
    String[] itemnames=new String[50];
    String[] itemcity=new String[50];
    String[] description=new String[50];
    String[] ownerids=new String[50];
    Double[] lats=new Double[50];
    Double[] longs=new Double[50];
    String category;
    String itemname;
    int k=0;
    String selectedlat,selectedlong,selecteduserid,selectedesc;
    float[] r=new float[50];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        latitude = getIntent().getDoubleExtra("latitude",-34.0);
        longitude= getIntent().getDoubleExtra("longitude", 151.0);
        category = getIntent().getStringExtra("category");
        itemname = getIntent().getStringExtra("itemname");
        try {


            mClient = new MobileServiceClient("https://goodsnextdoorcapstone.azure-mobile.net/", "IrDKWwuYiCMcDatgBeOzklZKeOINDD73", this);

            // Get the Mobile Service Table instance to use
            mitem = mClient.getTable(item.class);
        } catch (MalformedURLException e) {
            new Exception("There was an error creating the Mobile Service. Verify the URL");
        }

        // Each row in the list stores country name, currency and flag


        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>()  {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e("AsyncTask", "onPreExecute");
            }

            @Override
            protected Void doInBackground(Void... params) {
                int j=0;
                final MobileServiceTable<item> table = mClient.getTable(item.class);
                MobileServiceList<item> results;
                try {
                    if(!itemname.isEmpty()) {

                        results = mitem.where().field("category").eq(category).execute().get();
                        for(int i=0;i<results.getTotalCount();i++)
                        {
                            if(results.get(i).getname().toString().toLowerCase().contains(itemname.toLowerCase())) {
                                Location.distanceBetween(latitude, longitude, results.get(i).getlatitude(), results.get(i).getlongitude(), r);
                                if (r[0] < 1000) {
                                    itemnames[j] = results.get(i).getname().toString();
                                    itemcity[j] = results.get(i).getcity().toString();
                                    description[i] = results.get(i).getdescription().toString();
                                    lats[j] = results.get(i).getlatitude();
                                    longs[j] = results.get(i).getlongitude();
                                    j = j + 1;
                                }
                            }

                        }
                        totalitems=j;
                        //results = mitem.where().field("category").eq(category).and().field("item").eq(itemname).execute().get();
                    }
                    else
                    {
                        results = mitem.where().field("category").eq(category).execute().get();
                        totalitems = results.size();
                        for (int i = 0; i < results.size(); i++) {
                            Location.distanceBetween(latitude, longitude, results.get(i).getlatitude(), results.get(i).getlongitude(), r);
                            if (r[0] < 1000) {
                                itemnames[j] = results.get(i).getname().toString();
                                itemcity[j] = results.get(i).getcity().toString();
                                description[j] = results.get(i).getdescription().toString();
                                lats[j] = results.get(i).getlatitude();
                                longs[j] = results.get(i).getlongitude();
                                j=j+1;
                            }
                        }

                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            mapFragment.getMapAsync(MapsActivity.this);


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                return null;
            }

        };

//Check SDK and using different execute method.

        task.execute();











    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent  intent = new Intent(MapsActivity.this, itemdescriptionActivity.class);
        String selecteditemname=marker.getTitle().split(": ")[0];
        String selecteddescription=marker.getTitle().split(": ")[1];
        intent.putExtra("itemname",selecteditemname);
        intent.putExtra("description",selecteddescription);
        startActivity(intent);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(sydney).title("My location"));

        while(lats[k]!=null)
        { LatLng others = new LatLng(lats[k], longs[k]);
            mMap.addMarker(new MarkerOptions().position(others).title(itemnames[k]+": "+description[k]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mMap.setOnInfoWindowClickListener(this);
            k=k+1;
        }
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
