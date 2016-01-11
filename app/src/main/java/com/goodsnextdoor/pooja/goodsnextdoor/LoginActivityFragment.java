package com.goodsnextdoor.pooja.goodsnextdoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import android.widget.ProgressBar;
import android.app.AlertDialog;

import org.json.JSONObject;


public class LoginActivityFragment extends Fragment {
    private TextView mTextDetail;
    private CallbackManager mCallbackManager;
    public MobileServiceClient mClient;
    public MobileServiceTable<user> muser;
    private ProgressBar mProgressBar;
    private List<String> permissions = new ArrayList<String>();
    private  String name,email,id;

    public FacebookCallback<LoginResult> mCallback =new FacebookCallback<LoginResult>() {
        private ProfileTracker mProfileTracker;
        @Override
        public void onSuccess(LoginResult loginResult) {

            AccessToken accessToken =loginResult.getAccessToken();

            if(Profile.getCurrentProfile() == null) {
                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                        Log.v("facebook - profile", profile2.getFirstName());
                        mProfileTracker.stopTracking();
                    }
                };
                mProfileTracker.startTracking();
            }
            else {
                Profile profile = Profile.getCurrentProfile();
                Log.v("facebook - profile", profile.getFirstName());
            }


            //Profile prof= Profile.getCurrentProfile();
            //displayWelcomeMessage(prof);
          /*  final user item = new user();

        //    item.setname(name);
            item.setuid(id);
            item.setemail(email);
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        muser.insert(item).get();
                    } catch (Exception exception) {
                        new Exception("Error");
                    }
                    return null;
                }
            }.execute();*/
            Intent myintent =new Intent(LoginActivityFragment.this.getActivity(), optionsActivity.class);
            startActivity(myintent);

        }

        public void displayWelcomeMessage(Profile profile)
        {
            if(profile!=null)
            {
                mTextDetail.setText("Welcome"+ profile.getName());

            }
        }
        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    public LoginActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManager=CallbackManager.Factory.create();





        try {
// Create the Mobile Service Client instance, using the provided
// Mobile Service URL and key
            mClient = new MobileServiceClient("https://goodsnextdoorproject.azure-mobile.net/","wfPWzbslQQqWgCwgYRzGTzRbXeYBLj14",this.getContext());

            // Get the Mobile Service Table instance to use
            muser = mClient.getTable(user.class);
        }
        catch (MalformedURLException e) {
            new Exception("There was an error creating the Mobile Service. Verify the URL");
        }
        //Log.d("pooja:","i reached here in oncreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton=(LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, mCallback);
        mTextDetail = (TextView)view.findViewById(R.id.text_details);
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile=Profile.getCurrentProfile();
        //displayWelcomeMessage(prof);
        System.out.print("i reached here in on resume");
        if(profile!=null)
        {
            mTextDetail.setText("Welcome"+ profile.getName());
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("pooja:", "i reached here in onactivityresult");
        Intent myintent =new Intent(this.getActivity(), optionsActivity.class);
        startActivity(myintent);


    }

}
