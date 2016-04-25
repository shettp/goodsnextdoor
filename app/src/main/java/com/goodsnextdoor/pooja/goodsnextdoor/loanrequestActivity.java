package com.goodsnextdoor.pooja.goodsnextdoor;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.facebook.Profile;
import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsSharedAccessSignature;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class loanrequestActivity extends AppCompatActivity implements OnClickListener {
    private EditText fromDateEtxt;
    private EditText toDateEtxt,comment;
    String item,category,description,ownerid,uid,type,channelid;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private MobileServiceTable<requestitem> mitem;
    private MobileServiceClient mClient;
    String cont;
    private SimpleDateFormat dateFormatter;
    requestitem  m;
    Profile d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loanrequest);
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
         m =new requestitem();
        item = getIntent().getStringExtra("item");
        cont = getIntent().getStringExtra("contact");
        category = getIntent().getStringExtra("category");
        description = getIntent().getStringExtra("description");
        channelid = getIntent().getStringExtra("channelid");
        ownerid=getIntent().getStringExtra("userid");
        type=getIntent().getStringExtra("type");
        try {



            mClient = new MobileServiceClient("https://goodsnextdoorcapstone.azure-mobile.net/","IrDKWwuYiCMcDatgBeOzklZKeOINDD73",this);

            // Get the Mobile Service Table instance to use
            mitem = mClient.getTable(requestitem.class);
        } catch (MalformedURLException e) {
            new Exception("There was an error creating the Mobile Service. Verify the URL");
        }
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.fromtext);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (EditText) findViewById(R.id.totext);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        comment=(EditText)findViewById(R.id.comment);
    }
    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }

    public void home(View v)
    {
        Intent  intent = new Intent(loanrequestActivity.this, optionsActivity.class);
        startActivity(intent);
    }
    public void submit(View view) {
        m.setitem(item);
        m.setouid(ownerid);
        m.setcategory(category);
        m.setdescription(description);
        m.setuid(d.getId().toString());
        m.setrequsetername(d.getName());
        m.setcomments(comment.getText().toString());
        m.setto(toDateEtxt.getText().toString());
        m.setfrom(fromDateEtxt.getText().toString());
        m.settype(type);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {


                        mitem.insert(m).get();


                }
                catch (final Exception e) {
                    new Exception("Error");
                }
                return null;
            }
        }.execute();
        PushSender p=new PushSender();
        String message= "Hi I am "+d.getName()+","+comment.getText().toString();
        p.sendPush(channelid, message);
        /*Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{cont});
        i.putExtra(Intent.EXTRA_SUBJECT, "Requesting item " + m.getname());
        i.putExtra(Intent.EXTRA_TEXT, comment.getText().toString());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(loanrequestActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }*/

    }
}
