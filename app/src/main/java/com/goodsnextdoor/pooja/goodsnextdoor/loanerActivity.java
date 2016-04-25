package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

public class loanerActivity extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    TextView item;
    TextView description;
    String category;
    Spinner dropdown;
    public Uri mPhotoFileUri = null;
    String filePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaner);
        item = (TextView) findViewById(R.id.itemname);
        description = (TextView) findViewById(R.id.description);
        dropdown = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[]{"Cell Phones", "Yard Equipment", "Computers", "Electronics", "Furniture", "Bicycles"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        Button choose = (Button) findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

    }

    public void next(View view) {
        Intent intent = new Intent(loanerActivity.this, loanerlocationActivity.class);
        category = dropdown.getSelectedItem().toString();
        intent.putExtra("category", category);
        intent.putExtra("item", item.getText().toString());
        intent.putExtra("description", description.getText().toString());
        intent.putExtra("imageuri", mPhotoFileUri);
        intent.putExtra("filepath", filePath);


        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mPhotoFileUri = data.getData();
            String wholeID = DocumentsContract.getDocumentId(mPhotoFileUri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);


            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mPhotoFileUri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}