package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class loanoptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loanoptions);

    }


    public void loaner(View view) {

        Intent intent = new Intent(loanoptionsActivity.this, loaneroptionActivity.class);

        startActivity(intent);
    }

    public void loanee(View view) {

        Intent intent = new Intent(loanoptionsActivity.this, loaneeActivity.class);

        startActivity(intent);
    }
}