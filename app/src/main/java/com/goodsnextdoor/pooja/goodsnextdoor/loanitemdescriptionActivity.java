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

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class loanitemdescriptionActivity extends AppCompatActivity {
    String description;
    String item;
    TextView dec,itemname;
    ImageView img;
    private MobileServiceTable<loanitem> mitem;
    private MobileServiceClient mClient;
    URI uri;
    URL urll;
    MobileServiceList<loanitem> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loanitemdescription);
        description= getIntent().getStringExtra("description");
        item = getIntent().getStringExtra("itemname");
        dec=(TextView)findViewById(R.id.desc);
        itemname=(TextView)findViewById(R.id.itemname);
        img=(ImageView)findViewById(R.id.im);
        try {



            mClient = new MobileServiceClient("https://goodsnextdoorcapstone.azure-mobile.net/","IrDKWwuYiCMcDatgBeOzklZKeOINDD73",this);

            // Get the Mobile Service Table instance to use
            mitem = mClient.getTable(loanitem.class);
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

    public void ownerdetails(View view)
    {
        Intent intent = new Intent(loanitemdescriptionActivity.this, loanerprofileActivity.class);
        intent.putExtra("ownerid", results.get(0).getuid());
        intent.putExtra("item",results.get(0).getname());
        intent.putExtra("category",results.get(0).getcategory());
        intent.putExtra("description",results.get(0).getdescription());
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
