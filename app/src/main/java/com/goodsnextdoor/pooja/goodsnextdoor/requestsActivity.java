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
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.Profile;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class requestsActivity extends AppCompatActivity {
    private MobileServiceTable<requestitem> mitem;

    private MobileServiceClient mClient;
    String category,type;
    String[] status=new String[50];
    String[] realstatus=new String[50];
    String[] itemname=new String[50];
    String[] pos=new String[50];
    String[] types=new String[50];
    Profile d;
    Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        category= getIntent().getStringExtra("category");
        type= getIntent().getStringExtra("item");

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


                        results = mitem.where().field("category").eq(category).and().field("item").eq(type).and().field("owneruserid").eq(d.getId()).execute().get();
                        for(int i=0;i<results.getTotalCount();i++)
                        {

                                    itemname[j] = results.get(i).getname().toString();
                                    status[j] = results.get(i).getrequestername().toString().toUpperCase();
                                    pos[j] = results.get(i).getposition().toString();
                                    types[j] = results.get(i).gettype().toString();
                                    realstatus[j] = results.get(i).getstatus().toString().toUpperCase();
                                    j = j + 1;

                        }

                        //results = mitem.where().field("category").eq(category).and().field("item").eq(itemname).execute().get();






                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

                            for (int i = 0; i < results.getTotalCount(); i++) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                if(pos[i]!=""&&types[i].equals("loan")) {
                                    hm.put("item", itemname[i] + ": " + status[i]+":"+"pos "+pos[i]+":"+types[i]);
                                    hm.put("status", status[i]);
                                    }
                                else if(types[i].equals("giveaway"))
                                {
                                    hm.put("item", itemname[i] + ": " + status[i]+":"+realstatus[i]+":"+types[i]);
                                    hm.put("status", status[i]);
                                }
                                else
                                { hm.put("item", itemname[i] + ": " + status[i]+":"+"pending"+":"+types[i]);
                                    hm.put("status", status[i]);


                                }
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

                                    String iname=tvCountry.getText().toString().split(": ")[0];
                                    String idesc=tvCountry.getText().toString().split(": ")[1];
                                    String n=idesc.split(":")[0];
                                    String g=idesc.split(":")[2];
                                    Intent intent = new Intent(requestsActivity.this, requesterprofileActivity.class);
                                    String k=String.valueOf(results.getTotalCount());
                                    intent.putExtra("itemname",iname);
                                    intent.putExtra("requester",n);
                                    intent.putExtra("queuesize",k);
                                    intent.putExtra("type",g);
                                    startActivity(intent);
                                    //Toast.makeText(getBaseContext(), tvCountry.getText().toString(), Toast.LENGTH_SHORT).show();
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
        Intent  intent = new Intent(requestsActivity.this, optionsActivity.class);
        startActivity(intent);
    }
}
