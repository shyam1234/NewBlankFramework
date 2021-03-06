package com.stpl.edurp.application;

import android.app.Application;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;
import com.stpl.edurp.notification.EduRPNotificationOpenedHandler;
import com.stpl.edurp.notification.EduRPNotificationReceivedHandler;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.InternetManager;
import com.stpl.edurp.utils.SharedPreferencesApp;


/**
 * Created by Admin on 15-11-2016.
 * This class treats as singleton. This class also handle client server communication.
 */
public class MyApplication extends Application {
    static final String TAG = MyApplication.class.getSimpleName();
    private static final int MY_SOCKET_TIMEOUT_MS = 20000;//20000;
    public static int HEIGHT = 0;
    public static int WIDTH = 0;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static MyApplication mInstance;
    public String versionCode;
    public String versionName;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        //--for solving android.os.FileUriExposedException: file download and open
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        //--------------------------------------------------------------------
        mInstance = this;
        SharedPreferencesApp.getInstance();
        initOneSignalNotification();

        //registering BroadcastReceiver dynamic--
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        InternetManager myReceiver = new InternetManager();
        registerReceiver(myReceiver, filter);
        //---------------------------------------
    }

    private void init() {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        HEIGHT = displayMetrics.heightPixels;
        WIDTH = displayMetrics.widthPixels;
        AppLog.log("Device WIDTH+++ " + WIDTH);
        AppLog.log("Device HEIGHT+++ " + HEIGHT);
        getVersionCode();
    }

    private void getVersionCode() {
        try {
            versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
            versionCode = Integer.toString(getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            AppLog.errLog(TAG, e.getMessage());
        }
    }

    private void initOneSignalNotification() {
        OneSignal.startInit(this)
                .setNotificationReceivedHandler(new EduRPNotificationReceivedHandler())
                .setNotificationOpenedHandler(new EduRPNotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        //set default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }


    /**
     * cancel given tag request
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }

    }

    /**
     * cancel all current standing requests
     */
    public void cancelAllRequests() {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
    }

    /**
     * use for getting runtime network status
     *
     * @param listener
     */
    public void setConnectivityListener(InternetManager.ConnectivityReceiverListener listener) {
        InternetManager.mConnectivityReceiverListener = listener;
    }


    /*This is called when the overall system is running low on memory,
    and would like actively running processes to tighten their belts.*/
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    /*Called by the system when the device configuration changes while your component is running.*/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}