package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

public class selectActivity extends AppCompatActivity {
   String p;
    String q;
    private MobileServiceTable<item> mitem;
    private MobileServiceClient mClient;
    String t;
    item i=new item();
    MobileServiceTable<item> table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
         p=getIntent().getStringExtra("category");
        q=getIntent().getStringExtra("itemname");
        try {



            mClient = new MobileServiceClient("https://goodsnextdoorproject.azure-mobile.net/","wfPWzbslQQqWgCwgYRzGTzRbXeYBLj14",this);

            // Get the Mobile Service Table instance to use
            mitem = mClient.getTable(item.class);
        } catch (MalformedURLException e) {
            new Exception("There was an error creating the Mobile Service. Verify the URL");
        }
    }
    public void Maps(View view)
    {
        Intent  intent = new Intent(selectActivity.this, MapsActivity.class);
        startActivity(intent);
    }
   public void list(View view)
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



        Intent intent = new Intent(selectActivity.this, listActivity.class);
        intent.putExtra("category", p);
        intent.putExtra("itemname", q);
        startActivity(intent);
    }


}
