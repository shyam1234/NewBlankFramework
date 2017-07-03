package com.stpl.edurp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.models.LoginDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 27-05-2017.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAuthTokenExpireThenRenew();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static void checkAuthTokenExpireThenRenew() {
        if (Utils.getTimeDiffFromCurrTime(UserInfo.tokenExp)) {
            //renew the authtoken
            if (Utils.isInternetConnected(MyApplication.getInstance().getApplicationContext())) {
                Map<String, String> header = new HashMap<>();
                header.put(WSContant.TAG_AUTHORIZATION, "Basic " + Utils.encodeToString(UserInfo.userId + ":" + UserInfo.authToken));
                header.put(WSContant.TAG_LANGUAGE_VERSION_DATE, SharedPreferencesApp.getInstance().getLastLangSync());
                header.put(WSContant.TAG_ISMOBILE, "true");
                header.put(WSContant.TAG_DATELASTRETRIEVED, SharedPreferencesApp.getInstance().getLastLoginTime());

                WSRequest.getInstance().requestWithParam(WSRequest.GET, WSContant.URL_LOGIN, header, null, WSContant.TAG_LOGIN, new IWSRequest() {
                    @Override
                    public void onResponse(String response) {
                        //--parsing logic------------------------------------------------------------------
                        ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_LOGIN);
                        LoginDataModel holder = ((LoginDataModel) obj.getModel());
                        if (holder.Status) {
                            LoginActivity.savedDataOnSharedPrefences(holder);
                            // SharedPreferencesApp.getInstance().saveAuthToken(UserInfo.authToken, UserInfo.userId, UserInfo.currUserType);
                        } else {
                            Toast.makeText(MyApplication.getInstance().getApplicationContext(), R.string.msg_invalide_credential, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError response) {
                    }
                });
            } else {
                //offline
            }
        }
    }
}
