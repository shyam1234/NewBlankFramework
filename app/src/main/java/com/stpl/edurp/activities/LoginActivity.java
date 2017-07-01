package com.stpl.edurp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableParentMaster;
import com.stpl.edurp.database.TableParentStudentAssociation;
import com.stpl.edurp.database.TableParentStudentMenuDetails;
import com.stpl.edurp.database.TableStudentDetails;
import com.stpl.edurp.database.TableUniversityMaster;
import com.stpl.edurp.models.LoginDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.TableParentMasterDataModel;
import com.stpl.edurp.models.TableParentStudentAssociationDataModel;
import com.stpl.edurp.models.TableParentStudentMenuDetailsDataModel;
import com.stpl.edurp.models.TableStudentDetailsDataModel;
import com.stpl.edurp.models.TableUniversityMasterDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 03-12-2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = LoginActivity.class.getName();
    private TextView mTextViewForgotPassword;
    private Button mButtonLogin;
    private TextInputEditText mEditTextUserName;
    private TextInputEditText mEditTextPassword;
    private SwitchCompat mSwitchLang;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    @Override
    protected void onStart() {
        super.onStart();
        reset();
    }

    private void initView() {
        mScrollView = (ScrollView) findViewById(R.id.scrollview);
        mEditTextUserName = (TextInputEditText) findViewById(R.id.edittext_email);
        mEditTextPassword = (TextInputEditText) findViewById(R.id.edittext_password);
        mTextViewForgotPassword = (TextView) findViewById(R.id.textview_forgot_password);
        mButtonLogin = (Button) findViewById(R.id.btnSignIn);
        mSwitchLang = (SwitchCompat) findViewById(R.id.switch_login_language);
        //----------------------------------------------------
        mScrollView.setSmoothScrollingEnabled(true);
        if (UserInfo.lang_pref.equalsIgnoreCase(WSContant.TAG_ENG)) {
            mSwitchLang.setChecked(false);
            setLangSelection();
        } else {
            mSwitchLang.setChecked(true);
            setLangSelection();
        }
        //----------------------------------------------------
        setListner();
        //cheat code                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             ++
        /*For parent*/
        //mEditTextUserName.setText("divyaparent1@gmail.com");
        //mEditTextPassword.setText("login@123");
        /*For employee*/
       // mEditTextUserName.setText("divyaemp1@gmail.com");
       // mEditTextPassword.setText("89156");

        mEditTextUserName.setText("divyaparent1@gmail.com");
        mEditTextPassword.setText("login@123");

//        mEditTextUserName.setText("harshaparent@gmail.com");
//        mEditTextPassword.setText("96968");
    }


    private void setListner() {
        mTextViewForgotPassword.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mSwitchLang.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textview_forgot_password:
                //  Intent intent = new Intent(LoginActivity.this, ChangePasswordtActivity.class);
                Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(intent);
                Utils.animRightToLeft(LoginActivity.this);
                break;
            case R.id.btnSignIn:
                if (Utils.isInternetConnected(this)) {
                    mScrollView.setEnabled(false);
                    mEditTextUserName.setFocusable(false);
                    mEditTextPassword.setFocusable(false);
                    mScrollView.setFocusable(false);
                    doLogin();
                }
                break;
        }
    }


    private void doLogin() {
        if (Utils.validateUserName(mEditTextUserName) &&
                Utils.validatePassword(mEditTextPassword)) {

            mButtonLogin.setText(Utils.getLangConversion(WSContant.TAG_LANG_PROCESSDING,getString(R.string.proceeding),UserInfo.lang_pref));
            //Utils.langConversion(this, mButtonLogin, WSContant.TAG_LANG_PROCESSDING, getString(R.string.proceeding), UserInfo.lang_pref);
            //mButtonLogin.setText(getResources().getString(R.string.proceeding));
            mButtonLogin.setEnabled(false);
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_AUTHORIZATION, "Basic " + Utils.encodeToString(mEditTextUserName.getText().toString() + ":" + mEditTextPassword.getText().toString()));
            header.put(WSContant.TAG_LANGUAGE_VERSION_DATE, SharedPreferencesApp.getInstance().getLastLangSync());
            header.put(WSContant.TAG_ISMOBILE, "true");
            //header.put(WSContant.TAG_UNIVERSITYID, "" + 3);
            header.put(WSContant.TAG_DATELASTRETRIEVED, SharedPreferencesApp.getInstance().getSavedTime());

            WSRequest.getInstance().requestWithParam(WSRequest.GET, WSContant.URL_LOGIN, header, null, WSContant.TAG_LOGIN, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    //--parsing logic------------------------------------------------------------------
                    ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_LOGIN);
                    LoginDataModel holder = ((LoginDataModel) obj.getModel());
                    AppLog.log(TAG, "holder.data.Status: " + holder.Status);
                    AppLog.log(TAG, "holder.data.UniversityName: " + holder.universityArrayList.get(0).UniversityName);
                    if (holder.Status) {
                        savedDataOnSharedPrefences(holder);
                        //-------------------------------------------------------------------
                        bindDataWithTableParentStudentAssociationDataModel(holder);
                        bindDataWithStudentDetailsDataModel(holder);
                        bindDataWithParentDetailsDataModel(holder);
                        bindDataWithUniversityDataModel(holder);
                        bindDataWithParentStudentMenuDetailsDataModel(holder);
                        //--------------------------------------------------------------------
                        //mButtonLogin.setText(getResources().getString(R.string.success));
                        //Utils.langConversion(LoginActivity.this, mButtonLogin, WSContant.TAG_LANG_SUCCESS, getString(R.string.success), UserInfo.lang_pref);
                        mButtonLogin.setText(Utils.getLangConversion(WSContant.TAG_LANG_SUCCESS,getString(R.string.success),UserInfo.lang_pref));
                        mButtonLogin.setEnabled(false);
                        navigateToNextPage();

                        AppLog.log(TAG, "++++++++++ load all the data+++++++++");
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.msg_invalide_credential, Toast.LENGTH_SHORT).show();
                        reset();
                    }
                }

                public void onErrorResponse(VolleyError response) {
                    reset();
                }
            });
            //------------------------------------------------

        } else {
            reset();
        }
    }

    public static void savedDataOnSharedPrefences(LoginDataModel holder) {
        AppLog.log(TAG, "getPhoneNumber: " + holder.data.PhoneNumber);
        AppLog.log(TAG, "parentId: " + holder.data.UserId);
        AppLog.log(TAG, "parentName: " + holder.data.UserName);
        AppLog.log(TAG, "currUserType: " + holder.data.UserType);
        if (holder.data.UserType != null && holder.data.PhoneNumber != null && holder.data.UserName != null) {
            UserInfo.userId = holder.data.UserId;
            UserInfo.parentName = holder.data.UserName;
            UserInfo.parentId = UserInfo.userId;
            UserInfo.currUserType = holder.data.UserType;
            SharedPreferencesApp.getInstance().saveAuthToken(UserInfo.authToken, UserInfo.userId, UserInfo.currUserType);
            SharedPreferencesApp.getInstance().saveLastLoginTime(Utils.getCurrTime());
            SharedPreferencesApp.getInstance().saveLastSavedUniversityID("" + holder.data.UniversityId);
        }
    }


    private void navigateToNextPage() {
        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        Utils.animRightToLeft(LoginActivity.this);
        finish();
    }

    private void reset() {
        mButtonLogin.setText(getResources().getString(R.string.sign_in));
        mButtonLogin.setEnabled(true);
        mScrollView.setEnabled(true);
        mEditTextUserName.setFocusable(true);
        mEditTextPassword.setFocusable(true);
        mEditTextUserName.setFocusableInTouchMode(true);
        mEditTextPassword.setFocusableInTouchMode(true);
        mScrollView.setFocusable(true);
    }

    @Override
    public void onBackPressed() {
        finish();
        Utils.animLeftToRight(LoginActivity.this);
    }

    private void bindDataWithTableParentStudentAssociationDataModel(LoginDataModel holder) {
        try {
            ArrayList<TableParentStudentAssociationDataModel> tableParentStudentList = new ArrayList<TableParentStudentAssociationDataModel>();
            for (LoginDataModel.ParentStudentAssociation parentStudentAsso : holder.parentStudentAssociationArrayList) {
                //-- assign value to model
                TableParentStudentAssociationDataModel obj = new TableParentStudentAssociationDataModel();
                obj.setStudentid(parentStudentAsso.StudentId);
                obj.setParentId(parentStudentAsso.ParentId);
                obj.setIsDefault(parentStudentAsso.IsDefault);
                tableParentStudentList.add(obj);
                if (parentStudentAsso.IsDefault) {
                    AppLog.log(TAG, "default student: " + parentStudentAsso.StudentId);
                    UserInfo.studentId = parentStudentAsso.StudentId;
                    SharedPreferencesApp.getInstance().savedDefaultChildSelection(UserInfo.studentId);
                }
            }
            //saving into database
            TableParentStudentAssociation table = new TableParentStudentAssociation();
            table.openDB(getApplicationContext());
            table.insert(tableParentStudentList);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("LoginActivity bindDataWithTableParentStudentAssociationDataModel", e.getMessage());
        }
    }


    private void bindDataWithStudentDetailsDataModel(LoginDataModel holder) {
        try {
            ArrayList<TableStudentDetailsDataModel> list = new ArrayList<TableStudentDetailsDataModel>();
            for (LoginDataModel.StudentProfiles model : holder.studentProfilesArrayList) {
                //-- assign value to model
                TableStudentDetailsDataModel obj = new TableStudentDetailsDataModel();
                obj.setUniversity_id(model.UniversityId);
                obj.setFullName(model.FullName);
                obj.setImageurl(model.ImageURL);
                obj.setCourseCode(model.CourseCode);
                obj.setGender(model.Gender);
                obj.setDateOfBirth(model.DateOfBirth);
                obj.setStudentNumber(model.StudentNumber);
                obj.setStudentId(model.StudentId);
                list.add(obj);
            }
            //saving into database
            TableStudentDetails table = new TableStudentDetails();
            table.openDB(getApplicationContext());
            table.insert(list);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("LoginActivity bindDataWithStudentDetailsDataModel", e.getMessage());
        }
    }


    /**
     * bind data for dashboard
     *
     * @param holder
     */
    private void bindDataWithParentDetailsDataModel(LoginDataModel holder) {
        try {
            ArrayList<TableParentMasterDataModel> list = new ArrayList<TableParentMasterDataModel>();
            for (LoginDataModel.ParentProfile model : holder.parentProfileArrayList) {
                //-- assign value to model
                TableParentMasterDataModel obj = new TableParentMasterDataModel();
                obj.setParent_name(model.ParentName);
                obj.setPhone_number(model.PhoneNumber);
                obj.setImageurl(model.ImageURL);
                obj.setParentid(model.ParentId);
                obj.setEmailid(model.EmailAddress);
                list.add(obj);
            }
            //saving into database
            TableParentMaster table = new TableParentMaster();
            table.openDB(getApplicationContext());
            table.insert(list);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("LoginActivity bindDataWithParentDetailsDataModel", e.getMessage());
        }
    }


    private void bindDataWithUniversityDataModel(LoginDataModel holder) {
        try {
            ArrayList<TableUniversityMasterDataModel> list = new ArrayList<TableUniversityMasterDataModel>();
            for (LoginDataModel.University model : holder.universityArrayList) {
                //-- assign value to model
                TableUniversityMasterDataModel obj = new TableUniversityMasterDataModel();
                obj.setUniversity_id(model.UniversityId);
                obj.setUniversity_url(model.UniversityURL);
                obj.setUniversity_name(model.UniversityName);
                obj.setUniversity_code(model.UniversityCode);
                obj.setUniversity_logo_path(model.UniversityLogoPath);
                list.add(obj);
            }
            //saving into database
            TableUniversityMaster table = new TableUniversityMaster();
            table.openDB(getApplicationContext());
            table.insert(list);
            table.closeDB();

        } catch (Exception e) {
            AppLog.errLog("LoginActivity bindDataWithUniversityDataModel", e.getMessage());
        }
    }


    private void bindDataWithParentStudentMenuDetailsDataModel(LoginDataModel holder) {
        try {
            ArrayList<TableParentStudentMenuDetailsDataModel> list = new ArrayList<TableParentStudentMenuDetailsDataModel>();
            for (LoginDataModel.ParentStudentMenuDetails model : holder.parentStudentMenuDetailsArrayList) {
                //-- assign value to model
                TableParentStudentMenuDetailsDataModel obj = new TableParentStudentMenuDetailsDataModel();
                obj.setAlert_count(model.ColumnCount);
                obj.setIsActive(model.IsActive);
                obj.setMenuCode(model.SubscriptionCode);
                obj.setParentId(model.ParentId);
                obj.setStudentId(model.StudentId);
                obj.setUniversityId(model.UniversityId);
                list.add(obj);
            }
            //saving into database
            final TableParentStudentMenuDetails table = new TableParentStudentMenuDetails();
            table.openDB(getApplicationContext());
            table.insert(list);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("LoginActivity bindDataWithParentStudentMenuDetailsDataModel", e.getMessage());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            SharedPreferencesApp.getInstance().saveLangSelection(WSContant.TAG_BHASHA);
        } else {
            SharedPreferencesApp.getInstance().saveLangSelection(WSContant.TAG_ENG);
        }
        setLangSelection();
    }


    public void setLangSelection() {
        mButtonLogin.setText(Utils.getLangConversion( WSContant.TAG_LANG_LOGIN, getString(R.string.sign_in), UserInfo.lang_pref));
        mEditTextUserName.setText(Utils.getLangConversion( WSContant.TAG_LANG_USERNAME, getString(R.string.user_name), UserInfo.lang_pref));
        mEditTextPassword.setText(Utils.getLangConversion( WSContant.TAG_LANG_PASSWORD, getString(R.string.password), UserInfo.lang_pref));
        mTextViewForgotPassword.setText(Utils.getLangConversion( WSContant.TAG_LANG_FORGOTPASSWORD, getString(R.string.forgot_password), UserInfo.lang_pref));

       /* Utils.langConversion(this, mButtonLogin, WSContant.TAG_LANG_LOGIN, getString(R.string.sign_in), UserInfo.lang_pref);
        Utils.langConversion(this, mEditTextUserName, WSContant.TAG_LANG_USERNAME, getString(R.string.user_name), UserInfo.lang_pref);
        Utils.langConversion(this, mEditTextPassword, WSContant.TAG_LANG_PASSWORD, getString(R.string.password), UserInfo.lang_pref);
        Utils.langConversion(this, mTextViewForgotPassword, WSContant.TAG_LANG_FORGOTPASSWORD, getString(R.string.forgot_password), UserInfo.lang_pref);*/
    }

}
