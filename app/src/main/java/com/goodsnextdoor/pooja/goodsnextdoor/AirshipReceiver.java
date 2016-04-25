package com.goodsnextdoor.pooja.goodsnextdoor;


 import android.app.Notification;
 import android.app.NotificationManager;
 import android.app.PendingIntent;
 import android.content.Context;
 import android.content.Intent;
 import android.os.Bundle;
 import android.support.annotation.CallSuper;
 import android.support.v4.content.LocalBroadcastManager;
 import android.util.Log;
 import android.widget.Toast;

 import com.urbanairship.UAirship;
 import com.urbanairship.push.BaseIntentReceiver;
 import com.urbanairship.push.PushManager;
 import com.urbanairship.push.PushMessage;

 import org.json.JSONException;
 import android.support.v4.app.NotificationCompat;
 import org.json.JSONObject;

public class AirshipReceiver extends BaseIntentReceiver {


    private static final String TAG = "AirshipReceiver";

     @Override
     protected void onChannelRegistrationSucceeded(Context context, String channelId) {
         Log.i(TAG, "Channel registration updated. Channel Id:" + channelId + ".");
     }

     @Override
     protected void onChannelRegistrationFailed(Context context) {
         Log.i(TAG, "Channel registration failed.");
     }

     @Override
     protected void onPushReceived(Context context, PushMessage message, int notificationId) {
         Log.i(TAG, "Received push notification. Alert: " + message.getAlert() + ". Notification ID: " + notificationId);

     }

     @Override
     protected void onBackgroundPushReceived(Context context, PushMessage message) {
         Log.i(TAG, "Received background push message: " + message);
     }

     @Override
     protected boolean onNotificationOpened(Context context, PushMessage message, int notificationId) {
         Log.i(TAG, "User clicked notification. Alert: " + message.getAlert());
         Intent m=new Intent(context,noteActivity.class);
         m.putExtra("message", message.getAlert());
         m.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(m);

         // Return false to let UA handle launching the launch activity
         return true;
     }

     @Override
     protected boolean onNotificationActionOpened(Context context, PushMessage message, int notificationId, String buttonId, boolean isForeground) {
         Log.i(TAG, "User clicked notification button. Button ID: " + buttonId + " Alert: " + message.getAlert());

         // Return false to let UA handle launching the launch activity
         return false;
     }

     @Override
     protected void onNotificationDismissed(Context context, PushMessage message, int notificationId) {
         Log.i(TAG, "Notification dismissed. Alert: " + message.getAlert() + ". Notification ID: " + notificationId);
     }
        }