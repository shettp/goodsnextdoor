package com.goodsnextdoor.pooja.goodsnextdoor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class DoSomething extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //ImageView image = new ImageView(this);
        //image.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        //setContentView(image);
        Toast.makeText(getApplicationContext(),
                "Do Something NOW",
                Toast.LENGTH_LONG).show();
    }

}