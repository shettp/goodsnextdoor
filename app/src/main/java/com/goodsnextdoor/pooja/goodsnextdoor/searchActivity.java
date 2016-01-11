package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class searchActivity extends AppCompatActivity {
    Spinner dropdown;
    String category;
    TextView itemname;
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
    {
        Intent intent = new Intent(searchActivity.this, selectActivity.class);
        category=dropdown.getSelectedItem().toString();

        intent.putExtra("category", category);
        intent.putExtra("itemname",  itemname.getText().toString());
        startActivity(intent);
    }
}