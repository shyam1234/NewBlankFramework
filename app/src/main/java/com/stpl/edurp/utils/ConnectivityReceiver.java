package com.stpl.edurp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.stpl.edurp.application.MyApplication;


/**
 * Created by Admin on 29-11-2016.
 */

public class ConnectivityReceiver extends BroadcastReceiver {
    public static ConnectivityReceiverListener mConnectivityReceiverListener;

    public  ConnectivityReceiver(){
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        if(mConnectivityReceiverListener!=null){
              mConnectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }
    }

    public static  boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }


}
