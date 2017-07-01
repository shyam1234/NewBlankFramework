package com.stpl.edurp.utils;

import android.util.Log;

/**
 * Created by Admin on 15-11-2016.
 */

public class AppLog {
    private static boolean FLAG_LOG = false;
    private static boolean FLAG_ERR_LOG = true;
    private static final String TAG_LOG = "debug_log";
    private static final String TAG_NET_LOG = "net_log";
    private static final String TAG_ERR_LOG = "error_log";

    public static void log(String TAG, String msg) {
        if (FLAG_LOG) {
            Log.d(TAG_LOG, TAG + " : " + msg);
        }
    }

    public static void log(String msg) {
        if (FLAG_LOG) {
            Log.d(TAG_LOG, msg);
        }
    }

    public static void networkLog(String TAG, String msg) {
        if (FLAG_LOG) {
            Log.d(TAG_NET_LOG, TAG + " : " + msg);
        }
    }

    public static void errLog(String TAG, String msg) {
        if (FLAG_ERR_LOG) {
            Log.e(TAG_ERR_LOG, "Exception from: " + TAG + " : " + msg);
        }
    }
}
