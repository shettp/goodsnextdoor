package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class loaneroptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaneroption);

    }
    public void loanitem(View view) {

        Intent intent = new Intent(loaneroptionActivity.this, loanerActivity.class);

        startActivity(intent);
    }
    public void loanrequest(View view) {

        Intent intent = new Intent(loaneroptionActivity.this, manageeloanitemActivity.class);

        startActivity(intent);
    }
}
