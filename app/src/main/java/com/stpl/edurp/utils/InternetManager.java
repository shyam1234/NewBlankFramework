package com.stpl.edurp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.stpl.edurp.R;


/**
 * Created by Admin on 29-11-2016.
 */

public class InternetManager extends BroadcastReceiver {
    private ConnectivityReceiverListener mConnectivityReceiverListener;

    public InternetManager(ConnectivityReceiverListener listner) {
        super();
        mConnectivityReceiverListener = listner;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (mConnectivityReceiverListener != null) {
            mConnectivityReceiverListener.onNetworkConnectionChanged(isConnected);
//            if (isConnected)
//                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
//            else
//                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Internet Not Connected", Toast.LENGTH_SHORT).show();
        }
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }

    public static boolean isInternetConnected(Context pContext) {
        ConnectivityManager cm = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            Toast.makeText(pContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }


}
