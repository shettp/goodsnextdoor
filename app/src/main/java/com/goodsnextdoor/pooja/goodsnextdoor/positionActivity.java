package com.goodsnextdoor.pooja.goodsnextdoor;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.goodsnextdoor.pooja.goodsnextdoor.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

public class positionActivity extends Activity {

    int totalitems=0;
    // Array of strings storing country names

    item it=new item();
    String[] itemnames=new String[50];
    String[] status=new String[50];
    String[] position=new String[50];
    String[] comments=new String[50];

    // Array of integers points to images stored in /res/drawable-ldpi/

    String category;
    String itemname,type;
    Profile d;
    // Array of strings to store currencies

    private MobileServiceTable<requestitem> mitem;
    private MobileServiceClient mClient;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        category = getIntent().getStringExtra("category");
        type= getIntent().getStringExtra("type");
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

        try {


            mClient = new MobileServiceClient("https://goodsnextdoorcapstone.azure-mobile.net/", "IrDKWwuYiCMcDatgBeOzklZKeOINDD73", this);

            // Get the Mobile Service Table instance to use
            mitem = mClient.getTable(requestitem.class);
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
                final MobileServiceTable<requestitem> table = mClient.getTable(requestitem.class);

                final MobileServiceList<requestitem> results;

                try {


                        results = mitem.where().field("category").eq(category).and().field("type").eq(type).and().field("userid").eq(d.getId()).execute().get();

                       for(int i=0;i<results.getTotalCount();i++)
                        {

                                    itemnames[j] = results.get(i).getname().toString();
                                    status[j] = results.get(i).getstatus().toString();
                                    position[j] = results.get(i).getposition().toString();
                                    comments[j]=results.get(i).getocomments().toString();
                                    j = j + 1;

                        }


                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

                            for (int i = 0; i < results.getTotalCount(); i++) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("item", itemnames[i]+": status: "+status[i].toUpperCase()+": pos: "+position[i]);
                                hm.put("position", position[i]);
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
                            ListView listView = (ListView) findViewById(R.id.listview);
                            // Setting the adapter to the listView
                            listView.setAdapter(adapter);

                            // Item Click Listener for the listview
                            OnItemClickListener itemClickListener = new OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                                    // Getting the Container Layout of the ListView
                                    LinearLayout linearLayoutParent = (LinearLayout) container;

                                    // Getting the inner Linear Layout
                                    LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent.getChildAt(1);

                                    // Getting the Country TextView
                                    TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);
                                    tvCountry.setTypeface(null, Typeface.BOLD);


                                }
                            };

                            // Setting the item click listener for the listview
                            listView.setOnItemClickListener(itemClickListener);


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
    public void home(View v)
    {
        Intent  intent = new Intent(positionActivity.this, optionsActivity.class);
        startActivity(intent);
    }

}