package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ownercontrolActivity extends AppCompatActivity {
    String itemname,requester;
    private MobileServiceTable<user> muser;
    private MobileServiceTable<fbuser> mfbuser;
    private MobileServiceTable<requestitem> mrequestitem;
    private MobileServiceClient mClient;

    TextView name,contact;
    ImageView pic;
    MobileServiceList<user> results;
    MobileServiceList<fbuser> results1;
    MobileServiceList<requestitem> result2;
    MobileServiceList<requestitem> queue;
    URI uri;
    URL urll;
    Profile d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownercontrol);
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
        itemname=getIntent().getStringExtra("itemname");
        requester=getIntent().getStringExtra("requester");
        name=(TextView)findViewById(R.id.name);
        contact=(TextView)findViewById(R.id.contact);
        pic=(ImageView)findViewById(R.id.pic);
        try {



            mClient = new MobileServiceClient("https://goodsnextdoorcapstone.azure-mobile.net/","IrDKWwuYiCMcDatgBeOzklZKeOINDD73",this);

            // Get the Mobile Service Table instance to use
            muser = mClient.getTable(user.class);
            mfbuser = mClient.getTable(fbuser.class);
            mrequestitem = mClient.getTable(requestitem.class);
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

                try {
                    result2 = mrequestitem.where().field("item").eq(itemname).and().field("requestername").eq(requester).execute().get();
                    queue = mrequestitem.where().field("item").eq(itemname).and().field("owneruserid").eq(d.getId()).execute().get();

                    results = muser.where().field("userid").eq(result2.get(0).getuid()).execute().get();
                    if(results.isEmpty())
                    {
                        results1 = mfbuser.where().field("userid").eq(result2.get(0).getuid()).execute().get();

                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (!results.isEmpty()) {
                                name.setText(results.get(0).getname());
                                contact.setText(results.get(0).getemail());
                                //String u=results.get(0).getImageUri();
                                try {
                                    uri = new URI(results.get(0).getImageUri().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    urll = uri.toURL();
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                try {

                                    String[] params = new String[]{urll.toString()};
                                    new DownloadImageTask().execute(urll.toString());

                                } catch (Exception e) {
                                    throw new RuntimeException(e);

                                }

                                // img.setImageURI(Uri.parse(u));
                            }

                            else
                            {
                                name.setText(results1.get(0).getname());
                                contact.setText(results1.get(0).getemail());
                                //String u=results.get(0).getImageUri();
                                try {
                                    uri = new URI(results1.get(0).getImageUri().toString());
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    urll = uri.toURL();
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                try {

                                    String[] params = new String[]{urll.toString()};
                                    new DownloadImageTask().execute(urll.toString());

                                } catch (Exception e) {
                                    throw new RuntimeException(e);

                                }

                                // img.setImageURI(Uri.parse(u));
                            }
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

    private class DownloadImageTask extends AsyncTask<String,Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String[] params) {
            String[] urls =(String[]) params;
            return loadImageFromNetwork(urls[0]);
        }
        @Override
        protected void onPostExecute(Bitmap result ) {
            pic.setImageBitmap(Bitmap.createScaledBitmap(result, 300, 300, false));
        }


    }

    public final  Bitmap loadImageFromNetwork(String urlString){
        try {
            URL url = new URL(urlString);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    public void submit(View v)
    {   String m= String.valueOf(queue.getTotalCount());
        Intent intent = new Intent(ownercontrolActivity.this, loancontrolActivity.class);
        intent.putExtra("queue", m);
        startActivity(intent);
    }

}
