package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.Profile;

public class optionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
    }

        public void profile(View view)
        {  //Intent intent = this.getIntent();
            //Bundle bundle = getIntent().getExtras();
            //String message = bundle.getString("fname");
            //String name = intent.getStringExtra("name");

            //Log.d("pooja:", name);

           Intent  intent = new Intent(optionsActivity.this, ProfileActivity.class);

            //intent.putExtra("fname",message);

            startActivity(intent);


        }
    public void search(View view)
    {  //Intent intent = this.getIntent();
        //Bundle bundle = getIntent().getExtras();
        //String message = bundle.getString("fname");
        //String name = intent.getStringExtra("name");

        //Log.d("pooja:", name);

        Intent  intent = new Intent(optionsActivity.this, searchActivity.class);

        //intent.putExtra("fname",message);

        startActivity(intent);
    }

    public void mypost(View view)
    {  //Intent intent = this.getIntent();
        //Bundle bundle = getIntent().getExtras();
        //String message = bundle.getString("fname");
        //String name = intent.getStringExtra("name");

        //Log.d("pooja:", name);

        Intent  intent = new Intent(optionsActivity.this, mypostsActivity.class);

        //intent.putExtra("fname",message);

        startActivity(intent);
    }

    public void Post(View view)
    {  //Intent intent = this.getIntent();
        //Bundle bundle = getIntent().getExtras();
        //String message = bundle.getString("fname");
        //String name = intent.getStringExtra("name");

        //Log.d("pooja:", name);

        Intent  intent = new Intent(optionsActivity.this, PostActivity.class);

        //intent.putExtra("fname",message);

        startActivity(intent);
    }






}
