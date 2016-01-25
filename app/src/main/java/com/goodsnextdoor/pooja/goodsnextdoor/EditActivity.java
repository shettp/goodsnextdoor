package com.goodsnextdoor.pooja.goodsnextdoor;

import android.Manifest;
import android.content.CursorLoader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonObject;
import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsSharedAccessSignature;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import java.net.URI;
import java.net.URISyntaxException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import java.net.URL;


import com.facebook.Profile;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class EditActivity extends AppCompatActivity {
    final ToDoItem item1 = new ToDoItem();
    private int PICK_IMAGE_REQUEST = 1;
    private final int SELECT_PHOTO = 1;
    private MobileServiceTable<user> muser;
    private MobileServiceTable<fbuser> otheruser;
    private MobileServiceClient mClient;
    int flag=0;
    private static int RESULT_LOAD_IMG = 1;
    TextView pname;
    TextView pcontact;
    ImageView imageView;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int SELECT_PICTURE = 1;
    // Run an Intent to start up the Android camera
    static final int REQUEST_TAKE_PHOTO = 1;
    public Uri mPhotoFileUri = null;
    public File mPhotoFile = null;
    String filePath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        pname=(TextView)(findViewById(R.id.pname));
        pcontact=(TextView)(findViewById(R.id.pcontact));
        imageView=(ImageView)(findViewById(R.id.imageView));


        try {



            mClient = new MobileServiceClient("https://goodsnextdoorproject.azure-mobile.net/","wfPWzbslQQqWgCwgYRzGTzRbXeYBLj14",this);

            // Get the Mobile Service Table instance to use
            muser = mClient.getTable(user.class);
            otheruser=mClient.getTable(fbuser.class);
        } catch (MalformedURLException e) {
            new Exception("There was an error creating the Mobile Service. Verify the URL");
        }
       Button choose=(Button)findViewById(R.id.pick);
        choose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
               flag=0;
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });
    }
    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

             mPhotoFileUri = data.getData();
            String wholeID = DocumentsContract.getDocumentId(mPhotoFileUri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = { MediaStore.Images.Media.DATA };

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{ id }, null);



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



    // Create a File object for storing the photo
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }



    public void takePicture(View view) {

        ActivityCompat.requestPermissions(EditActivity.this,
                new String[]{Manifest.permission.CAMERA},
                1);
        if (ContextCompat.checkSelfPermission(EditActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(EditActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        1);
            }
        }
        flag=1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                mPhotoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                new Exception("error", ex);
            }
            // Continue only if the File was successfully created
            if (mPhotoFile != null) {
                mPhotoFileUri = Uri.fromFile(mPhotoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoFileUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

  public user addItemInTable(user item) throws ExecutionException, InterruptedException {
      final MobileServiceList<user> result =
              muser.where().field("userid").eq(item.getuid()).execute().get();
      final MobileServiceList<fbuser> result1 =
              otheruser.where().field("userid").eq(item.getuid()).execute().get();
      if(result.isEmpty()&&result1.isEmpty()) {
          user entity = muser.insert(item).get();
          return entity;
      }
      else
      {
          return null;
      }
    }


    /**
     * Add a new item
     *
     * @param view
     *            The view that originated the call
     */
    public void uploadPhoto(View view) {
        final user item1 = new user();

        //item1.setText(pname.getText().toString());
        item1.setContainerName("todoitemimages");
       item1.setname(pname.getText().toString());
        item1.setemail(pcontact.getText().toString());
        item1.setuid(Profile.getCurrentProfile().getId());
        item1.setImageUri(mPhotoFileUri.toString());

        // Use a unigue GUID to avoid collisions.
        UUID uuid = UUID.randomUUID();
        String uuidInString = uuid.toString();
        item1.setResourceName(uuidInString);

        // Send the item to be inserted. When blob properties are set this
        // generates an SAS in the response.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final user entity = addItemInTable(item1);
                    if (entity != null) {
                        // If we have a returned SAS, then upload the blob.
                        if (entity.getSasQueryString() != null) {

                            // Get the URI generated that contains the SAS
                            // and extract the storage credentials.
                            StorageCredentials cred =
                                    new StorageCredentialsSharedAccessSignature(entity.getSasQueryString());
                            URI imageUri = new URI(entity.getImageUri());

                            // Upload the new image as a BLOB from a stream.
                            CloudBlockBlob blobFromSASCredential =
                                    new CloudBlockBlob(imageUri, cred);

                            if (flag == 0)
                                blobFromSASCredential.uploadFromFile(filePath);
                            else
                                blobFromSASCredential.uploadFromFile(mPhotoFileUri.getPath());


                        }


                    }
                }
                    catch (final Exception e) {
                    new Exception("Error");
                }
                return null;
            }
        }.execute();



    }


}
