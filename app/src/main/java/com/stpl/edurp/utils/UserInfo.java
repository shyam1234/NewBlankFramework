package com.stpl.edurp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.stpl.edurp.R;
import com.stpl.edurp.activities.DashboardActivity;
import com.stpl.edurp.activities.LoginActivity;
import com.stpl.edurp.activities.SplashActivity;
import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.constant.WSContant;

/**
 * Created by Admin on 14-01-2017.
 */

public class UserInfo {
    public static int parentId=-1;
    public static String parentName;
    public static int studentId=-1;
    public static String authToken;
    public static int univercityId;
    public static String tokenExp;
    public static String tokenIssue;
    public static int userId=-1;
    public static String currUserType;
    public static String selectedStudentImageURL="";
    public static String menuCode="";
    public static String timeTableRefDate="2016-02-04 09:54:14";
    public static String lang_pref= WSContant.TAG_ENG;

    public static void clearUSerInfo() {
        lang_pref = WSContant.TAG_ENG;
        userId = -1;
        parentName = null;
        parentId = -1;
        parentName = null;
        studentId = -1;
        authToken = null;
        univercityId = 0;
        tokenExp = null;
        tokenIssue = null;
        currUserType = null;
        selectedStudentImageURL = "";
        menuCode = null;

    }

    public static void logout(boolean isShowToastAndNavigateToLogin) {
        clearUSerInfo();
        SharedPreferencesApp.getInstance().removeAll();
        AppCompatActivity activity =null;
        if (SplashActivity.mContext != null) {
            activity = (AppCompatActivity) SplashActivity.mContext;
        }else if (DashboardActivity.mContext != null) {
            activity = (AppCompatActivity) DashboardActivity.mContext;
        }

        if(activity!=null) {
            if(isShowToastAndNavigateToLogin) {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), R.string.msg_logout, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
                activity.finish();
                Utils.animLeftToRight(activity);
            }
            //Utils.removeAllFragments(activity.getSupportFragmentManager());
        }
    }



}

