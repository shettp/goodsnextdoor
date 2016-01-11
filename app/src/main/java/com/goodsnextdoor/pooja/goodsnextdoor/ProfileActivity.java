package com.goodsnextdoor.pooja.goodsnextdoor;
import java.net.MalformedURLException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonObject;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity{
    TextView username;
    TextView email;
    ImageView image;
    String uid;
    TextView con,conans;


Profile d;
    private MobileServiceTable<user> muser;
    private MobileServiceClient mClient;

    final user item = new user();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

        username=(TextView)findViewById(R.id.name);
        username.setText(":"+d.getName());
        image=(ImageView)findViewById(R.id.imageView);

        item.setname(d.getName());
        item.setuid(d.getId());


        Uri ur=d.getProfilePictureUri(500, 500);
        String g=ur.toString();
        URI uri = null;
        URL urll = null;
        String uriString =g;
        // Create a URI object
        try {
            uri = new URI(uriString);
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

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);

        }
        try {

            mClient = new MobileServiceClient("https://goodsnextdoorproject.azure-mobile.net/","wfPWzbslQQqWgCwgYRzGTzRbXeYBLj14",this);

            // Get the Mobile Service Table instance to use
            muser = mClient.getTable(user.class);
        } catch (MalformedURLException e) {
            new Exception("There was an error creating the Mobile Service. Verify the URL");
        }
        //openAlert();



    }

    private void openAlert() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileActivity.this);

        alertDialogBuilder.setTitle("Contact info");

        final EditText input = new EditText(ProfileActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setMessage("phone/email");



        alertDialogBuilder.setNegativeButton("Ok",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int id) {
                item.setemail(input.getText().toString());

                new AsyncTask<Void, Void, Void>() {
                        @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            final MobileServiceList<user> result =
                                    muser.where().field("userid").eq(item.getuid()).execute().get();
                            if(result.isEmpty()) {
                                muser.insert(item).get();
                            }
                        } catch (Exception exception) {
                            new Exception("Error");
                        }
                        return null;
                    }
                }.execute();


                // cancel the alert box and put a Toast to the user

                dialog.cancel();

                Toast.makeText(getApplicationContext(), "Your data is saved in the database",

                        Toast.LENGTH_LONG).show();

            }

        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void profile(View view)
    {

    }
    public void back(View view)
    {
        Intent intent = new Intent(this, optionsActivity.class);
        startActivity(intent);
    }

    public void yess(View view)
    {
        openAlert();
    }
    public void no(View view)
    {
        Intent intent = new Intent(this, EditActivity.class);
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
            image.setImageBitmap(result);
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
