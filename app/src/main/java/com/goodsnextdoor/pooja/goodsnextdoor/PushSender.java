package com.goodsnextdoor.pooja.goodsnextdoor;
import android.os.StrictMode;


import com.urbanairship.UAirship;
import com.urbanairship.api.client.APIClient;
import com.urbanairship.api.client.APIClientResponse;
import com.urbanairship.api.client.APIPushResponse;
import com.urbanairship.api.client.APIRequestException;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushPayload;
import com.urbanairship.api.push.model.audience.Selectors;
import com.urbanairship.api.push.model.notification.Notifications;
import com.urbanairship.push.PushManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class PushSender {
    public void sendPush(String Channelid, String message) {
        final String appKey = "zZ0u5tMAQoijo935s9Ox8w";
        final String appSecret = "JwAAiQd6Qy-IHkO8Vu4roA";
        OutputStream output = null;
        HttpsURLConnection connection = null;

        try {

            // Build and configure an APIClient
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(appKey, appSecret.toCharArray());
                }
            });

            URL url = new URL("https://go.urbanairship.com/api/push");
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);


            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/vnd.urbanairship+json; version=3;");
            String postdata = "{\"audience\": {\"android_channel\": \""+Channelid+"\"}, \"notification\": {\"alert\": \""+message+"\"}, \"device_types\": [\"android\"]}";
            //connection.setRequestProperty("Content-Length", Integer.toString(data.length()));
           /* JSONObject json = new JSONObject();

            JSONObject audience = new JSONObject();
            audience.put("android_channel", "bd8cb75c-555b-4818-86a1-cba565f47c29");
            json.put("audience", audience);

            JSONObject notification = new JSONObject();
            notification.put("alert", "Hello!");
            json.put("notification", notification);

            JSONObject device_types = new JSONObject();
            device_types.put("device_types", "android");
            json.put("device_types", device_types);*/
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            output = connection.getOutputStream();
            output.write(postdata.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (Exception e) {
                }
            }
        }
        try {
            int status = ((HttpURLConnection) connection).getResponseCode();

            System.out.println("status is..." + status);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}