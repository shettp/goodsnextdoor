package com.goodsnextdoor.pooja.goodsnextdoor;

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

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ownerprofileActivity extends AppCompatActivity {
     String ownerid;
    private MobileServiceTable<user> muser;
    private MobileServiceTable<fbuser> mfbuser;
    private MobileServiceClient mClient;
    TextView name,contact;
    ImageView pic;
    MobileServiceList<user> results;
    MobileServiceList<fbuser> results1;
    URI uri;
    URL urll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerprofile);
        ownerid=getIntent().getStringExtra("ownerid");
        name=(TextView)findViewById(R.id.name);
        contact=(TextView)findViewById(R.id.contact);
        pic=(ImageView)findViewById(R.id.pic);
        try {



            mClient = new MobileServiceClient("https://goodsnextdoorproject.azure-mobile.net/","wfPWzbslQQqWgCwgYRzGTzRbXeYBLj14",this);

            // Get the Mobile Service Table instance to use
            muser = mClient.getTable(user.class);
            mfbuser = mClient.getTable(fbuser.class);
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


}
