package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.Toast;
import android.provider.Settings.Secure;
import com.facebook.Profile;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.urbanairship.UAirship;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ownerprofileActivity extends AppCompatActivity {
     String ownerid;
    private MobileServiceTable<user> muser;
    private MobileServiceTable<fbuser> mfbuser;
    private MobileServiceTable<requestitem> mreq;
    private MobileServiceClient mClient;
    String email;
    Profile d;
    requestitem m=new requestitem();
    TextView name,contact;
    ImageView pic;
    String type,itemname,category,desc;
    MobileServiceList<user> results;
    MobileServiceList<fbuser> results1;
    String channelid;
    URI uri;
    URL urll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerprofile);
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
        ownerid=getIntent().getStringExtra("ownerid");
        itemname=getIntent().getStringExtra("item");
        category=getIntent().getStringExtra("category");
        desc=getIntent().getStringExtra("description");
        name=(TextView)findViewById(R.id.name);
        contact=(TextView)findViewById(R.id.contact);
        pic=(ImageView)findViewById(R.id.pic);
        type=getIntent().getStringExtra("type");
        try {



            mClient = new MobileServiceClient("https://goodsnextdoorcapstone.azure-mobile.net/","IrDKWwuYiCMcDatgBeOzklZKeOINDD73",this);

            // Get the Mobile Service Table instance to use
            muser = mClient.getTable(user.class);
            mfbuser = mClient.getTable(fbuser.class);
            mreq = mClient.getTable(requestitem.class);

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

                    results = muser.where().field("userid").eq(ownerid).execute().get();
                    if(results.isEmpty())
                    {
                        results1 = mfbuser.where().field("userid").eq(ownerid).execute().get();

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

    public void home(View v)
    {
        Intent  intent = new Intent(ownerprofileActivity.this, optionsActivity.class);
        startActivity(intent);
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

public void check(View v) {

    if (type.equals("loan")) {
        Intent intent = new Intent(ownerprofileActivity.this, loanrequestActivity.class);
        if (!results.isEmpty()) {
            intent.putExtra("userid", results.get(0).getuid());

            email=results.get(0).getemail();
            channelid=results.get(0).getChannelid();
        }
        else {
            intent.putExtra("userid", results1.get(0).getuid());
            email=results1.get(0).getemail();
            channelid=results1.get(0).getchannelid();
        }
        intent.putExtra("item", itemname);
        intent.putExtra("category", category);
        intent.putExtra("description", desc);
        intent.putExtra("type", type);
        intent.putExtra("contact", email);
        intent.putExtra("channelid",channelid);
        startActivity(intent);
    } else {
        if (!results.isEmpty()) {
            m.setouid(results.get(0).getuid());

            email=results.get(0).getemail();
        }
        else {
            m.setouid(results1.get(0).getuid());
            email=results1.get(0).getemail();
            channelid=results1.get(0).getchannelid();
        }
        m.setitem(itemname);
        m.setcategory(category);
        m.setdescription(desc);
        m.setuid(d.getId().toString());
        m.setrequsetername(d.getName());
        m.settype(type);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {


                    mreq.insert(m).get();


                }
                catch (final Exception e) {
                    new Exception("Error");
                }
                return null;
            }
        }.execute();

        UAirship.shared().getPushManager().getNamedUser().setId(channelid);
        PushSender p=new PushSender();
        String message="Hi, I am " +d.getName()+" I am requesting this item. If it is still available please contact me.";
        p.sendPush(channelid,message);
        /*
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL,new String[] { email });
        i.putExtra(Intent.EXTRA_SUBJECT, "Requesting item " + m.getname());
        i.putExtra(Intent.EXTRA_TEXT, "Hi, I am requesting this item. If it is still available please contact me.");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ownerprofileActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
        */

    }
}
}
