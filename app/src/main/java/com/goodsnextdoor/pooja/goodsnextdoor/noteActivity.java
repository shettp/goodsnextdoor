package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class noteActivity extends AppCompatActivity {
    TextView msg;
    String b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        msg=(TextView)findViewById(R.id.notify);
        b=getIntent().getStringExtra("message");
        msg.setText(b);

    }
    public  void ok(View view)
    {
        Intent m=new Intent(noteActivity.this,optionsActivity.class);
        startActivity(m);
    }

}
