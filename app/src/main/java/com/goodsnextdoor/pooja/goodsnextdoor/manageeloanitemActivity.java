package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class manageeloanitemActivity extends AppCompatActivity {
    private ListView lv;
    private MobileServiceTable<requestitem> mitem;
    private MobileServiceClient mClient;
    final MobileServiceList<requestitem> results=null;

    Profile d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageeloanitem);
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
        lv = (ListView) findViewById(R.id.listrequest);
        try {

            mClient = new MobileServiceClient("https://goodsnextdoorcapstone.azure-mobile.net/","IrDKWwuYiCMcDatgBeOzklZKeOINDD73",this);
            // Get the Mobile Service Table instance to use
            mitem = mClient.getTable(requestitem.class);

        } catch (MalformedURLException e) {
            new Exception("There was an error creating the Mobile Service. Verify the URL");
        }

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>()  {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e("AsyncTask", "onPreExecute");
            }

            @Override
            protected Void doInBackground(Void... params) {
                int j=0;
                final MobileServiceTable<requestitem> table = mClient.getTable(requestitem.class);

                final MobileServiceList<requestitem> results;

                try {
                    results = mitem.where().field("owneruserid").eq(d.getId()).execute().get();




                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

                            for (int i = 0; i < results.getTotalCount(); i++) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("item",results.get(i).getname()+": " +results.get(i).getrequestername());
                                hm.put("requester",results.get(i).getrequestername() );
                                aList.add(hm);
                            }

                            // Keys used in Hashmap
                            String[] from = {"flag", "item", "city"};

                            // Ids of views in listview_layout
                            int[] to = {R.id.flag, R.id.txt, R.id.cur};

                            // Instantiating an adapter to store each items
                            // R.layout.listview_layout defines the layout of each item
                            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

                            // Getting a reference to listview of main.xml layout file
                            ListView listView = (ListView) findViewById(R.id.listrequest);
                            // Setting the adapter to the listView
                            listView.setAdapter(adapter);

                            // Item Click Listener for the listview
                            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                                    // Getting the Container Layout of the ListView
                                    LinearLayout linearLayoutParent = (LinearLayout) container;

                                    // Getting the inner Linear Layout
                                    LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent.getChildAt(1);

                                    // Getting the Country TextView
                                    TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);
                                    tvCountry.setTypeface(null, Typeface.BOLD);
                                    String itemname=tvCountry.getText().toString().split(": ")[0];
                                    String reqname=tvCountry.getText().toString().split(": ")[1];
                                    Intent intent = new Intent(manageeloanitemActivity.this, ownercontrolActivity.class);

                                    intent.putExtra("itemname",itemname);
                                    intent.putExtra("requester",reqname);

                                    startActivity(intent);
                                    //Toast.makeText(getBaseContext(), tvCountry.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                            };

                            // Setting the item click listener for the listview
                            listView.setOnItemClickListener(itemClickListener);


                        }
                    });}catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                return null;
            }

        };

//Check SDK and using different execute method.

        task.execute();

        /*new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
               // final MobileServiceTable<requestitem> table = mClient.getTable(requestitem.class);



                try {


                    results = mitem.where().field("owneruserid").eq(d.getId()).execute().get();



                }
                catch (final Exception e) {
                    new Exception("Error");
                }
                return null;
            }
        }.execute();*/



        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.


    }

}
