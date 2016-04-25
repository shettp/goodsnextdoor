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

public class choiceActivity extends AppCompatActivity {
    Spinner dropdown;
    Spinner dropdown1;
    String category,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        dropdown = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[]{"Cell Phones", "Yard Equipment", "Computers", "Electronics", "Furniture", "Bicycles"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown1 = (Spinner) findViewById(R.id.spinner2);
        String[] items1 = new String[]{"giveaway","loan"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);


    }
    public void home(View v)
    {
        Intent  intent = new Intent(choiceActivity.this, optionsActivity.class);
        startActivity(intent);
    }
    public void next(View view)
    {
        category=dropdown.getSelectedItem().toString();
        type=dropdown1.getSelectedItem().toString();


        Intent intent = new Intent(choiceActivity.this,positionActivity.class);

        intent.putExtra("category", category);
        intent.putExtra("type",  type);
        startActivity(intent);
    }
}
