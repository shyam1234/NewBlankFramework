package com.stpl.edurp.notification;

import android.content.Intent;
import android.util.Log;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.stpl.edurp.activities.SplashActivity;
import com.stpl.edurp.application.MyApplication;

import org.json.JSONObject;

/**
 * Created by Admin on 03-06-2017.
 */

public class EduRPNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;
        String launchUrl = result.notification.payload.launchURL; // update docs launchUrl

        String customKey;
        String openURL = null;

        //Object activityToLaunch = MainActivity.class;

        if (data != null) {
            customKey = data.optString("customkey", null);
            openURL = data.optString("openURL", null);

            if (customKey != null)
                Log.i("OneSignalExample", "customkey set with value: " + customKey);

            if (openURL != null)
                Log.i("OneSignalExample", "openURL to webview with URL value: " + openURL);
        }

        if (actionType == OSNotificationAction.ActionType.ActionTaken) {
            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
            if (result.action.actionID.equals("id1")) {
                Log.i("OneSignalExample", "button id called: " + result.action.actionID);
            } else {
                Log.i("OneSignalExample", "button id called: " + result.action.actionID);
            }

            Intent intent = new Intent(MyApplication.getInstance().getApplicationContext(), SplashActivity.class);
            MyApplication.getInstance().getApplicationContext().startActivity(intent);
        }
        // The following can be used to open an Activity of your choice.
        // Replace - getApplicationContext() - with any Android Context.
        // Intent intent = new Intent(getApplicationContext(), YourActivity.class);

        // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
        //   if you are calling startActivity above.
     /*
        <application ...>
          <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
        </application>
     */
    }
}
