package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

public class searchActivity extends AppCompatActivity {
    Spinner dropdown;

    TextView itemname;
    private MobileServiceTable<item> mitem;
    private MobileServiceClient mClient;
    int totalitems=0;
    // Array of strings storing country names

    item it=new item();
    String[] itemnames=new String[50];
    String[] itemcity=new String[50];
    Double[] lats=new Double[50];
    Double[] longs=new Double[50];
    String category;
    String itemn;
    String itemtosearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dropdown = (Spinner) findViewById(R.id.spinner1);
        itemname=(TextView)findViewById(R.id.itemname);
        String[] items = new String[]{"Cell Phones", "Yard Equipment", "Computers", "Electronics", "Furniture", "Bicycles"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

    }
    public void sear(View view)

    {   itemn=itemname.getText().toString();
        category=dropdown.getSelectedItem().toString();


        Intent intent = new Intent(searchActivity.this, selectActivity.class);

        intent.putExtra("category", category);
        intent.putExtra("itemname",  itemname.getText().toString());
        startActivity(intent);
    }
}