package com.stpl.edurp.notification;

import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.utils.AppLog;

import org.json.JSONObject;

/**
 * Created by Admin on 03-06-2017.
 */

public class EduRPNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
    private static final java.lang.String TAG = EduRPNotificationReceivedHandler.class.getName();
    public static String NOTIFICATION_RECEIVED;//="NEW";

    @Override
    public void notificationReceived(OSNotification notification) {

        JSONObject data = notification.payload.additionalData;
        String notificationID = notification.payload.notificationID;
        String title = notification.payload.title;
        String body = notification.payload.body;
        String smallIcon = notification.payload.smallIcon;
        String largeIcon = notification.payload.largeIcon;
        String bigPicture = notification.payload.bigPicture;
        String smallIconAccentColor = notification.payload.smallIconAccentColor;
        String sound = notification.payload.sound;
        String ledColor = notification.payload.ledColor;
        int lockScreenVisibility = notification.payload.lockScreenVisibility;
        String groupKey = notification.payload.groupKey;
        String groupMessage = notification.payload.groupMessage;
        String fromProjectNumber = notification.payload.fromProjectNumber;
        //BackgroundImageLayout backgroundImageLayout = notification.payload.backgroundImageLayout;
        String rawPayload = notification.payload.rawPayload;

        String customKey;

        Log.i("OneSignalExample", "NotificationID received: " + data.toString());

        /*if (data != null) {
            customKey = data.optString("customkey", null);
            if (customKey != null)
                Log.i("OneSignalExample", "customkey set with value: " + customKey);
        }*/
        //-----------------------------------------------------------------------------
        NOTIFICATION_RECEIVED = "NEW";
        pareseResp(data.toString());
        //------------------------------------------------------------------------------
    }

    private void pareseResp(String str) {
        try {
            if (str != null && str.length() > 0) {
                JSONObject jsonObject = new JSONObject(str);
                NOTIFICATION_RECEIVED = jsonObject.optString(WSContant.TAG_MENUCODE);
                AppLog.log(TAG,"Arrived NOTIFICATION_RECEIVED++ "+NOTIFICATION_RECEIVED);
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, " notificationReceived " + e.getMessage());
        }
    }

}


