package com.goodsnextdoor.pooja.goodsnextdoor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class loancontrolActivity extends AppCompatActivity {
   TextView size;
    String q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loancontrol);
        q=getIntent().getStringExtra("queue");
        size=(TextView)findViewById(R.id.size);
        size.setText(q);

    }

}
