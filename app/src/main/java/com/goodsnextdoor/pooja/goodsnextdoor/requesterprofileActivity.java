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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class requesterprofileActivity extends AppCompatActivity {
    String ownerid;
    private MobileServiceTable<user> muser;
    private MobileServiceTable<fbuser> mfbuser;
    private MobileServiceTable<requestitem> mreq;
    private MobileServiceClient mClient;
    Profile d;
    String cont;
    requestitem m=new requestitem();
    TextView name,contact,from, to,fromtag,totag;
    TextView comments;
    ImageView pic;
    String type,itemname,requester;
    MobileServiceList<user> results;
    MobileServiceList<fbuser> results1;
    MobileServiceList<requestitem> results2;
    URI uri;
    Spinner dropdown;
    URL urll;
    String queuesize;
    String Channelid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requesterprofile);
        queuesize=getIntent().getStringExtra("queuesize");
        type=getIntent().getStringExtra("type");
        int k=Integer.parseInt(queuesize);
        fromtag=(TextView)findViewById(R.id.fromtag);
        totag=(TextView)findViewById(R.id.texttag);
        dropdown = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[k];
        String[] items1 ={"give","given to someone"};
        int j=1;
        if(type.equals("giveaway"))
        {
                fromtag.setVisibility(View.INVISIBLE);
                totag.setVisibility(View.INVISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
            dropdown.setAdapter(adapter);
        }
        else {
            for (int i = 0; i < k; i++)
                items[i] = String.valueOf(j++);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
            dropdown.setAdapter(adapter);
        }
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
        from=(TextView)findViewById(R.id.from);
        to=(TextView)findViewById(R.id.to);
        comments=(TextView)findViewById(R.id.comments);
        contact=(TextView)findViewById(R.id.contact);
        pic=(ImageView)findViewById(R.id.pic);

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
                    results2 = mreq.where().field("owneruserid").eq(d.getId().toString()).and().field("item").eq(itemname).and().field("requestername").eq(requester).execute().get();
                    results = muser.where().field("userid").eq(results2.get(0).getuid().toString()).execute().get();
                    if(!results.isEmpty()) {
                        cont = results.get(0).getemail();
                        Channelid=results.get(0).getChannelid();

                    }
                    if(results.isEmpty())
                    {
                        results1 = mfbuser.where().field("userid").eq(results2.get(0).getuid().toString()).execute().get();
                        cont=results1.get(0).getemail();
                        Channelid=results1.get(0).getchannelid();
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            from.setText(results2.get(0).getfrom().toString() + "-");
                            to.setText(results2.get(0).getto().toString());
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
        Intent  intent = new Intent(requesterprofileActivity.this, optionsActivity.class);
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

    public void submit(View v) {

        results2.get(0).setocomments(comments.getText().toString());
        if(type.equals("loan")) {
            results2.get(0).setposition(dropdown.getSelectedItem().toString() + " out of " + queuesize);

            if (dropdown.getSelectedItem().equals("1")) {
                results2.get(0).setstatus("Item is given to me");
            } else {
                results2.get(0).setstatus("waiting");
            }
        }
        else

        {    results2.get(0).setposition(dropdown.getSelectedItem().toString());

            if (dropdown.getSelectedItem().equals("deleted")) {
                results2.get(0).setstatus("deleted");
            } else {
                results2.get(0).setstatus("closed");
            }
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                   mreq.update(results2.get(0)).get();

                }
                catch (final Exception e) {
                    new Exception("Error");
                }
                return null;
            }
        }.execute();
        if(dropdown.getSelectedItem().equals("1"))
        {   PushSender p= new PushSender();
            String ms="Hi, I am lending this item to you. Please contact me when you are ready to pick!.";
            p.sendPush(Channelid,ms);
            /*Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_EMAIL,new String[] { cont });
            i.putExtra(Intent.EXTRA_SUBJECT, "Requesting item " + m.getname());
            i.putExtra(Intent.EXTRA_TEXT, "Hi, I am lending this item to you. Please contact me when you are ready to pick!.");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(requesterprofileActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }*/
        }
        else
        {   String msg;
            PushSender p= new PushSender();
            if(type.equals("giveaway"))
            {
                if(dropdown.getSelectedItem().toString()=="give")
               msg ="Hi, I am giving away this item to you. Please collect it soon";
                else
                    msg="Hi, Sorry!, the item is given to somebody else.";

            }
            else {
                msg = "Hi, Please wait as your position is " + dropdown.getSelectedItem().toString() + "out of " + queuesize + " in the queue";
            }
                p.sendPush(Channelid,msg);
            /*Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_EMAIL,new String[] { cont });
            i.putExtra(Intent.EXTRA_SUBJECT, "Requesting item " + m.getname());
            i.putExtra(Intent.EXTRA_TEXT, "Hi, Please wait as your position is "+dropdown.getSelectedItem().toString()+ "out of "+queuesize+" in the queue");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(requesterprofileActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
            */
        }

    }
}
