package com.goodsnextdoor.pooja.goodsnextdoor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsSharedAccessSignature;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class deletepostActivity extends AppCompatActivity {
    String description;
    String item;
    TextView dec,itemname,type;
    ImageView img;
    private MobileServiceTable<item> mitem;
    private MobileServiceClient mClient;
    URI uri;
    URL urll;
    Spinner dropdown;
    MobileServiceList<item> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletepost);


        description= getIntent().getStringExtra("description");
        item = getIntent().getStringExtra("itemname");
        dec=(TextView)findViewById(R.id.desc);
        type=(TextView)findViewById(R.id.type);
        itemname=(TextView)findViewById(R.id.itemname);
        img=(ImageView)findViewById(R.id.im);
        try {

            mClient = new MobileServiceClient("https://goodsnextdoorcapstone.azure-mobile.net/","IrDKWwuYiCMcDatgBeOzklZKeOINDD73",this);

            // Get the Mobile Service Table instance to use
            mitem = mClient.getTable(item.class);
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

                    results = mitem.where().field("item").eq(item).and().field("description").eq(description).execute().get();

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            itemname.setText(results.get(0).getname().toString());
                            dec.setText(results.get(0).getdescription().toString());
                            type.setText(results.get(0).gettype().toString());
                            String u=results.get(0).getImageUri();
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

                            }
                            catch (Exception e)
                            {
                                throw new RuntimeException(e);

                            }

                            // img.setImageURI(Uri.parse(u));
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
    public void check(View v)
    {        Intent intent = new Intent(deletepostActivity.this, requestsActivity.class);

        intent.putExtra("item",results.get(0).getname());
        intent.putExtra("description",results.get(0).getdescription());
        intent.putExtra("category", results.get(0).getcategory());
        intent.putExtra("ownerid", results.get(0).getuid());
        intent.putExtra("type", results.get(0).gettype());
        startActivity(intent);
    }
    private void openAlert() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(deletepostActivity.this);

        alertDialogBuilder.setTitle("Alert!");

        final EditText input = new EditText(deletepostActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
       // alertDialogBuilder.setView(input);
        alertDialogBuilder.setMessage("Are you sure you want to delete?");



        alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            mitem.delete(results.get(0));

                        } catch (Exception exception) {
                        }
                        return null;
                    }
                }.execute();

                Toast.makeText(getApplicationContext(), "Item is successully deleted", Toast.LENGTH_LONG).show();


                // cancel the alert box and put a Toast to the user

                dialog.cancel();


            }

        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void home(View v)
    {
        Intent  intent = new Intent(deletepostActivity.this, optionsActivity.class);
        startActivity(intent);
    }

    public void yess(View view)
    {
        openAlert();
    }
public void delete(View view)
{
   openAlert();

}

    private class DownloadImageTask extends AsyncTask<String,Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String[] params) {
            String[] urls =(String[]) params;
            return loadImageFromNetwork(urls[0]);
        }
        @Override
        protected void onPostExecute(Bitmap result ) {
            img.setImageBitmap(Bitmap.createScaledBitmap(result,300,300, false));
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
