package com.stpl.edurp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.DatabaseHelper;
import com.stpl.edurp.database.TableLanguage;
import com.stpl.edurp.interfaces.ICallBack;
import com.stpl.edurp.models.LanguageArrayDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.TableLanguageDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.CustomDialogbox;
import com.stpl.edurp.utils.InternetManager;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.stpl.edurp.constant.Constant.DB_VERSION;

/**
 * Created by Admin on 11-12-2016.
 */
public class SplashActivity extends BaseActivity {
    private final String TAG = SplashActivity.class.getName();
    private static final long TIME_DELAY = 2000;
    private static Handler mHandler;
    private static Runnable mRunnable;
    public static Context mContext;
    // CircularProgressBar mCircularProgressBar;
    ArrayList<TableLanguageDataModel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Utils.animRightToLeft(SplashActivity.this);
        //needUpgrade forcefully call to onUpgrade method to check is db need to upgrade.
        DatabaseHelper.getInstance(getApplicationContext()).getReadableDatabase().needUpgrade(DB_VERSION);
        init();
        initView();
        checkDPI();

//        MyApplication.getInstance().setConnectivityListener(new InternetManager.ConnectivityReceiverListener() {
//            @Override
//            public void onNetworkConnectionChanged(boolean isConnected) {
//
//            }
//        });
        // Utils.showProgressBar(100, mCircularProgressBar);
    }


    private void checkDPI() {
        /*
            0.75 - ldpi
            1.0 - mdpi
            1.5 - hdpi
            2.0 - xhdpi
            3.0 - xxhdpi
            4.0 - xxxhdpi
        */
        AppLog.log(TAG, "density+++ " + getResources().getDisplayMetrics().density);
        if(AppLog.FLAG_LOG)
        Toast.makeText(mContext, "dpi: " + getResources().getDisplayMetrics().density, Toast.LENGTH_SHORT).show();
    }


    private void init() {
        mContext = this;
        //for check database version number if less then new one then delete all tables and update again
        SharedPreferencesApp.getInstance();
        DatabaseHelper.getInstance(this);
        //---------------------------------------
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                checkForLanguage();
            }
        };
        mHandler.postDelayed(mRunnable, TIME_DELAY);
        UserInfo.studentId = SharedPreferencesApp.getInstance().getDefaultChildSelection();
        SharedPreferencesApp.getInstance().getLangSelection();
        //------------------------------------------

    }

    private void initView() {
        //mCircularProgressBar = (CircularProgressBar) findViewById(R.id.progressBar);
        ImageView logo = (ImageView) findViewById(R.id.imgview_logo);
//        Animation an2= AnimationUtils.loadAnimation(this,R.anim.bounce);
//        logo.startAnimation(an2);


        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l = (RelativeLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        logo.clearAnimation();
        logo.startAnimation(anim);


    }


    private void checkForLanguage() {

        //---------------------------------------------------------------------------------------
        if (InternetManager.isInternetConnected(this)) {
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_UNIVERSITYID, SharedPreferencesApp.getInstance().getLastSavedUniversityID());
            header.put(WSContant.TAG_LANGUAGE_VERSION_DATE, SharedPreferencesApp.getInstance().getLastLangSync());
            WSRequest.getInstance().requestWithParam(WSRequest.GET, WSContant.URL_BASE, header, null, WSContant.TAG_LANG, new IWSRequest() {
                @Override
                public void onResponse(String response) {

                    AppLog.log("onResponse", "res++ " + response);
                    ParseResponse obj = new ParseResponse(response, LanguageArrayDataModel.class, ModelFactory.MODEL_LANG);
                    LanguageArrayDataModel holder = ((LanguageArrayDataModel) obj.getModel());
                    new LoadLangAsyncTask().execute(holder);
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    AppLog.log("splashActivity++", "onErrorResponse");
                    showErrorDialog();
                }
            });
        } else {
            TableLanguage table = new TableLanguage();
            table.openDB(SplashActivity.this);
            final ArrayList<TableLanguageDataModel> lLangList = table.read();
            table.closeDB();
            checkOfflineData(lLangList);
        }
    }

    private void checkOfflineData(ArrayList<TableLanguageDataModel> lLangList) {
        if (lLangList.size() > 0) {
            Toast.makeText(mContext, "Offline Activated", Toast.LENGTH_SHORT).show();
            if (UserInfo.authToken != null && (UserInfo.parentId != -1 && UserInfo.studentId != -1)) {
                navigateToNextPage(DashboardActivity.class);
            } else {
                navigateToNextPage(LoginActivity.class);
            }
        } else {
            showErrorDialog();
        }
    }

    private void showErrorDialog() {
        try {
            final CustomDialogbox dilog = new CustomDialogbox(SplashActivity.this, CustomDialogbox.TYPE_OK);
            dilog.setTitle(getResources().getString(R.string.msg_network_prob));
            dilog.show();
            dilog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                    // TODO Auto-generated method stub
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        arg0.dismiss();
                        finish();
                    }
                    return true;
                }
            });
            dilog.getBtnOK().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dilog.dismiss();
                    finish();
                }
            });
        } catch (Exception e) {
            AppLog.errLog("SplashActivity onErrorResponse", e.getMessage());
        }
    }



   /* private void storeIntoDB(ParseResponse obj) {
        TableLanguage table = new TableLanguage();
        table.openDB(SplashActivity.this);
        //AppLog.log("table: ",((LanguageArrayDataModel) obj.getModel()).LanguageArray.get(3).EnglishVersion);
        boolean isAdded = table.insert(((LanguageArrayDataModel) obj.getModel()).LanguageArray);
        if (isAdded)
            Toast.makeText(this, "Language db updated for university id: " + SharedPreferencesApp.getInstance().universityID, Toast.LENGTH_SHORT).show();
        table.closeDB();
        SharedPreferencesApp.getInstance().saveLanguageUpdateHistory(SharedPreferencesApp.getInstance().universityID, Utils.getCurrTime());
        navigateToNextPage();
    }*/

    private boolean bindDataWithLanguageDataModel(LanguageArrayDataModel holder) {
        try {
            list = new ArrayList<TableLanguageDataModel>();
            for (LanguageArrayDataModel.LanguageDataModel model : holder.LanguageArray) {
                //-- assign value to model
                TableLanguageDataModel obj = new TableLanguageDataModel();
                obj.setUniversityId(model.getUniversityId());
                obj.setBahasaVersion(model.getBahasaVersion());
                obj.setConversionCode(model.getConversionCode());
                obj.setConversionId(model.getConversionId());
                obj.setDateModified(model.getDateModified());
                obj.setEnglishVersion(model.getEnglishVersion());
                list.add(obj);
            }

        } catch (Exception e) {
            AppLog.errLog("SplashActivity bindDataWithLanguageDataModel", e.getMessage());
            return false;
        }
        return true;
    }


    private void navigateToNextPage(Class<?> activity) {
        Intent intent = new Intent(SplashActivity.this, activity);
        startActivity(intent);
        finish();
        Utils.animRightToLeft(SplashActivity.this);
    }


    private class LoadLangAsyncTask extends AsyncTask<LanguageArrayDataModel, Void, Boolean> {


        @Override
        protected Boolean doInBackground(LanguageArrayDataModel... holder) {
            return bindDataWithLanguageDataModel(holder[0]);
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            try {
                if (!b) {
                    return;
                }
                //saving into database
                TableLanguage table = new TableLanguage();
                table.openDB(getApplicationContext());
                boolean isAdded = table.insert(list);
                table.closeDB();
                AppLog.log("lang inserted+++ 111 ", "" + isAdded);
                if (isAdded) {
                    SharedPreferencesApp.getInstance().saveLastLangSync(Utils.getCurrTime());
                    AppLog.log("splash", "Lang Sync for university id: " + SharedPreferencesApp.getInstance().getLastSavedUniversityID());
                }

                AppLog.log("splash UserInfo.authToken  ", "" + UserInfo.authToken);
                if (UserInfo.authToken != null && (UserInfo.parentId != -1 && UserInfo.studentId != -1)) {
                    Utils.getHomeFragmentItems(new ICallBack() {
                        @Override
                        public void callBack() {
                            navigateToNextPage(DashboardActivity.class);
                        }
                    });
                } else {
                    navigateToNextPage(LoginActivity.class);
                }
            } catch (Exception e) {
                AppLog.errLog(TAG, e.getMessage());
            }
        }
    }



}
