package com.stpl.edurp.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableLanguage;
import com.stpl.edurp.database.TableParentStudentMenuDetails;
import com.stpl.edurp.interfaces.ICallBack;
import com.stpl.edurp.models.GetMobileHomeDataModel;
import com.stpl.edurp.models.LoginDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.TableParentStudentMenuDetailsDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 26-11-2016.
 */

public class Utils {

    private static final String TAG = Utils.class.getName();
    private static Context mContext = MyApplication.getInstance().getApplicationContext();
    private static String currTimeYYYYMMDDOOOOOO;


    public static boolean validateUserName(EditText pEditText) {
        if (pEditText != null && !pEditText.getText().toString().equals("")) {
            return validateEmail(pEditText);
        } else {
            pEditText.setError(mContext.getResources().getString(R.string.msg_validate_username_2));
            vibrateEditText(pEditText);
        }
        return false;
    }

    public static boolean validatePassword(EditText pEditText) {
        if (pEditText != null && !pEditText.getText().toString().equals("")) {
            if (pEditText.getText().toString().length() >= 4 && pEditText.getText().toString().length() <= 12) {
                return true;
            } else {
                pEditText.setError(mContext.getResources().getString(R.string.msg_validate_password_3));
                vibrateEditText(pEditText);
            }
        } else {
            pEditText.setError(mContext.getResources().getString(R.string.msg_validate_password_2));
            vibrateEditText(pEditText);
        }
        return false;
    }

    // String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    /**
     * validate your email address format. Ex-akhi@mani.com
     */
    public static boolean validateEmail(EditText pEditText) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(pEditText.getText().toString());
        if (matcher.matches()) {
            return true;
        } else {
            pEditText.setError(mContext.getResources().getString(R.string.msg_validate_emai));
            vibrateEditText(pEditText);
            return false;
        }

    }

    public static boolean isContainSpecialChar(EditText pEditText) {
        if (pEditText.getText().toString().matches("[a-zA-Z0-9]*")) {
            return false;
        } else {
            pEditText.setError(mContext.getResources().getString(R.string.msg_validate_password_1));
            vibrateEditText(pEditText);
            return true;
        }

    }

    public static void vibrateEditText(EditText pEditText) {
        Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.vibrate);
        pEditText.startAnimation(shake);
    }


    // for in
    public static void animRightToLeft(Activity pActivity) {
        if (pActivity instanceof Activity)
            pActivity.overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    // for out
    public static void animLeftToRight(Activity pActivity) {
        if (pActivity instanceof Activity)
            pActivity.overridePendingTransition(R.anim.left, R.anim.right);
    }


    public static String encodeToString(String text) {
        byte[] data = new byte[0];
        try {
            data = text.getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String decodeToString(String base64) {
        try {
            byte[] data = Base64.decode(base64, Base64.DEFAULT);
            return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public static boolean validateChangePassword(EditText pEditTextEmail, EditText pEditTextNewPassword, EditText pEditTextConfirmPassword) {
        if (validateEmail(pEditTextEmail)
                && validatePassword(pEditTextNewPassword)
                && validatePassword(pEditTextConfirmPassword)) {
            // if (!pEditTextEmail.getText().toString().equals(pEditTextNewPassword.getText().toString())) {
            if (pEditTextNewPassword.getText().toString().equals(pEditTextConfirmPassword.getText().toString())) {
                return true;
            } else {
                pEditTextConfirmPassword.setError(mContext.getResources().getString(R.string.msg_validate_new_password));
            }
//            } else {
//                pEditTextNewPassword.setError(mContext.getResources().getString(R.string.msg_validate_new_password1));
//            }
        }
        return false;
    }


    public static String getCurrTime() {
        Calendar c = Calendar.getInstance();
//      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        AppLog.log("getCurrTime ", formattedDate);
        return formattedDate;

    }


    public static void showProgressBar(int progress, CircularProgressBar circularProgressBar) {

        circularProgressBar.setColor(ContextCompat.getColor(circularProgressBar.getContext(), R.color.progressBarColor));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(circularProgressBar.getContext(), R.color.backgroundProgressBarColor));
        circularProgressBar.setProgressBarWidth(6);
        circularProgressBar.setBackgroundProgressBarWidth(9);
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(progress, animationDuration); // Default duration = 1500ms
    }


    public static void navigateFragment(FragmentManager fragmentManager, Fragment fragment, String TAG) {
        try {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.framelayout_holder, fragment);
            ft.addToBackStack(TAG);
            ft.commit();
            ft.setCustomAnimations(R.anim.left, R.anim.right);
        } catch (Exception e) {
            AppLog.errLog("navigateFragment", e.getMessage());
        }
    }


    public static void navigateFragmentMenu(FragmentManager fragmentManager, Fragment fragment, String TAG) {
        try {
            AppLog.log("navigateFragmentMenu ", "navigateFragmentMenu called on menu clicked " + TAG);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.framelayout_holder, fragment);
            ft.addToBackStack(TAG);
            ft.commit();
            ft.setCustomAnimations(R.anim.left, R.anim.right);
        } catch (Exception e) {
            AppLog.errLog("navigateFragmentMenu", e.getMessage());
        }
    }


//    public static String getLastRetrivedTime() {
//        try {
//            SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(WSContant.TAG_SHAREDPREF_NAME, Context.MODE_PRIVATE);
//            String lastRetrivedTime = getCurrTime();
//            if ((lastRetrivedTime = sp.getString(WSContant.TAG_SHAREDPREF_GET_LAST_TIME, null)) != null) {
//                return lastRetrivedTime;
//            } else {
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString(WSContant.TAG_SHAREDPREF_GET_LAST_TIME, lastRetrivedTime);
//                editor.commit();
//            }
//            return lastRetrivedTime;
//        } catch (Exception e) {
//            AppLog.errLog("getLastRetrivedTime", e.getMessage());
//        } finally {
//            return "";
//        }
//    }

    public static String getLastRetrivedTime() {
        try {
            if (SharedPreferencesApp.getInstance().getSavedTime() != null) {
                return SharedPreferencesApp.getInstance().getSavedTime();
            } else {
                SharedPreferencesApp.getInstance().saveTime(getCurrTime());
            }
        } catch (Exception e) {
            AppLog.errLog("getLastRetrivedTime", e.getMessage());

        } finally {
            return SharedPreferencesApp.getInstance().getSavedTime();
        }
    }


    public static void getHomeFragmentItems(final ICallBack pCallback) {
        // UserInfo.studentId = SharedPreferencesApp.getInstance().getDefaultChildSelection();
        AppLog.log("Utils ", "UserInfo.studentId :" + UserInfo.studentId);
        AppLog.log("Utils ", "UserInfo.univercityId :" + UserInfo.univercityId);
        if (UserInfo.parentId != -1 && UserInfo.studentId != -1) {
            //--for header
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTime());
            header.put(WSContant.TAG_NEW, Utils.getCurrTime());
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_PARENTID, "" + UserInfo.parentId);
            body.put(WSContant.TAG_STUDENTID, "" + UserInfo.studentId);
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEHOME, header, body, WSContant.TAG_GETMOBILEHOME, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    AppLog.log(TAG, "onResponse +++ " + response.toString());
                    AppLog.log("Utils ", "bindDataWithParentStudentMenuDetailsDataModel: : onResponse");
                    initTableAndDisplay(response, pCallback);
                    Utils.dismissProgressBar();
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    initTableAndDisplay(null, pCallback);
                    Utils.dismissProgressBar();
                    AppLog.log("Utils ", "bindDataWithParentStudentMenuDetailsDataModel: : onErrorResponse");
                }
            });

        } else {
            AppLog.errLog("Utils", "Empty field parentId " + UserInfo.parentId);
            AppLog.errLog("Utils", "Empty field studentId " + UserInfo.studentId);
            Utils.dismissProgressBar();
        }
    }


    private static void initTableAndDisplay(String response, ICallBack pCallback) {

        if (response != null) {
            ParseResponse obj = new ParseResponse(response, GetMobileHomeDataModel.class, ModelFactory.MODEL_GETMOBILEHOME);
            GetMobileHomeDataModel holder = ((GetMobileHomeDataModel) obj.getModel());
            bindDataWithParentStudentMenuDetailsDataModel(holder);
        }
        pCallback.callBack();
    }


    private static void bindDataWithParentStudentMenuDetailsDataModel(GetMobileHomeDataModel holder) {
        try {
            AppLog.log("Utils ", "bindDataWithParentStudentMenuDetailsDataModel");
            ArrayList<TableParentStudentMenuDetailsDataModel> list = new ArrayList<TableParentStudentMenuDetailsDataModel>();
            for (LoginDataModel.ParentStudentMenuDetails model : holder.ParentStudentMenuDetailsArrayList) {
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
            table.openDB(MyApplication.getInstance().getApplicationContext());
            table.insert(list);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog(TAG + " bindDataWithParentStudentMenuDetailsDataModel", e.getMessage());
        }
    }


    private static ProgressDialog dialog;

    public static void showProgressBar(Context pContext) {
        dialog = new ProgressDialog(pContext);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(R.layout.my_progress);

    }

    public static void dismissProgressBar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public static String getLastRetrivedTimeForNews() {
        try {
            if (SharedPreferencesApp.getInstance().getSavedTime() != null) {
                return SharedPreferencesApp.getInstance().getSavedTime();
            } else {
                SharedPreferencesApp.getInstance().saveTime("");
            }
        } catch (Exception e) {
            AppLog.errLog("getLastRetrivedTimeForNews", e.getMessage());

        } finally {
            return SharedPreferencesApp.getInstance().getSavedTime();
        }
    }

    public static void hideNativeKeyboard(Activity context) {
        // Check if no view has focus:
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static boolean isInternetConnected(Context pContext) {
        ConnectivityManager cm = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            Toast.makeText(pContext, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
        return cm.getActiveNetworkInfo() != null;
    }

    public static String getTimeInYYYYMMDD(String yyyyMMddHHmmss) {
        String time = "";
        try {
            if (yyyyMMddHHmmss != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(yyyyMMddHHmmss);
                time = formatter1.format(date);
                AppLog.log("Time: " + time);
            }
        } catch (Exception e) {
            AppLog.errLog("getTimeInYYYYMMDD+++ from Utils", e.getMessage());
        } finally {
            return time;
        }

    }

    public static String getTimeInDDMMYYYY(String yyyyMMddHHmmss) {
        String time = "";
        try {
            if (yyyyMMddHHmmss != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = formatter.parse(yyyyMMddHHmmss);
                time = formatter1.format(date);
                AppLog.log("Time: " + time);
            }
        } catch (Exception e) {
            AppLog.errLog("getTimeInDDMMYYYY 333  from Utils", e.getMessage());
        } finally {
            return time;
        }

    }


    /**
     * Firday, 19th March 2017 (today)
     *
     * @param yyyyMMdd
     * @return
     */
    public static String getTimeInDayDateMonthYear(String yyyyMMdd) {
        String time = "";
        try {
            if (yyyyMMdd != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat formatter1 = new SimpleDateFormat("EEE, MMMM d, yyyy");
                Date date = formatter.parse(yyyyMMdd);
                time = formatter1.format(date);
                AppLog.log("Time: " + time);
            }
        } catch (Exception e) {
            AppLog.errLog("getTimeInDayDateMonthYear from Utils", e.getMessage());
        } finally {
            return time;
        }

    }


    /**
     * * this method takes single conversion code and append in signle String
     *
     * @param pContext
     * @param obj
     * @param pConversionCode
     * @param defaultString
     * @param langPref
     */
    public static void langConversion(Context pContext, Object obj, String pConversionCode, String defaultString, String langPref) {
        TableLanguage holder = new TableLanguage();
        try {
            AppLog.log(TAG, "Lang pref " + langPref);
            holder.openDB(pContext);
            langPref = (langPref != null ? langPref : WSContant.TAG_ENG);
            switch (langPref) {
                case WSContant.TAG_ENG:
                    if (obj instanceof Button) {
                        ((Button) obj).setText(holder.getValue(pConversionCode).getEnglishVersion() != null ? holder.getValue(pConversionCode).getEnglishVersion() : defaultString);
                    } else if (obj instanceof EditText) {
                        ((EditText) obj).setHint(holder.getValue(pConversionCode).getEnglishVersion() != null ? holder.getValue(pConversionCode).getEnglishVersion() : defaultString);
                    } else if (obj instanceof TextView) {
                        ((TextView) obj).setText(holder.getValue(pConversionCode).getEnglishVersion() != null ? holder.getValue(pConversionCode).getEnglishVersion() : defaultString);
                    }
                    break;
                case WSContant.TAG_BHASHA:
                    if (obj instanceof Button) {
                        ((Button) obj).setText(holder.getValue(pConversionCode).getBahasaVersion() != null ? holder.getValue(pConversionCode).getBahasaVersion() : defaultString);
                    } else if (obj instanceof EditText) {
                        ((EditText) obj).setHint(holder.getValue(pConversionCode).getBahasaVersion() != null ? holder.getValue(pConversionCode).getBahasaVersion() : defaultString);
                    } else if (obj instanceof TextView) {
                        ((TextView) obj).setText((holder.getValue(pConversionCode).getBahasaVersion() != null ? holder.getValue(pConversionCode).getBahasaVersion() : defaultString));
                    }
                    break;
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, e.getMessage());
        } finally {
            holder.closeDB();
        }
    }


    /**
     * this method takes array of conversion code and append in signle String
     *
     * @param pContext
     * @param obj
     * @param pArrayConversionCode
     * @param defaultString
     * @param langPref
     */
    public static void langConversion(Context pContext, Object obj, String[] pArrayConversionCode, String defaultString, String langPref) {
        TableLanguage holder = new TableLanguage();
        try {
            AppLog.log(TAG, "Lang pref " + langPref);
            holder.openDB(pContext);
            langPref = (langPref != null ? langPref : WSContant.TAG_ENG);
            String text = "";
            for (String pConversionCode : pArrayConversionCode) {
                if (text.toString().trim().length() > 0) {
                    text = text + " ";
                }
                switch (langPref) {
                    case WSContant.TAG_ENG:
                        text = text + (holder.getValue(pConversionCode).getEnglishVersion() != null ? holder.getValue(pConversionCode).getEnglishVersion() : defaultString);
                        break;
                    case WSContant.TAG_BHASHA:
                        text = text + (holder.getValue(pConversionCode).getBahasaVersion() != null ? holder.getValue(pConversionCode).getBahasaVersion() : defaultString);
                        break;
                }
            }
            if (obj instanceof Button) {
                ((Button) obj).setText(text);
            } else if (obj instanceof EditText) {
                ((EditText) obj).setHint(text);
            } else if (obj instanceof TextView) {
                ((TextView) obj).setText(text);
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, e.getMessage());
        } finally {
            holder.closeDB();
        }
    }


    /**
     * getExternalStorageDirectory creates folder in This "Internal storage\Edurp_results"
     *
     * @param pContext
     * @param pFolderName
     * @param pFileName
     */
    public static void showDownloadFile(Activity pContext, String pFolderName, String pFileName) {
        verifyStoragePermissions(pContext);
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + pFolderName + "/" + pFileName);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            pContext.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(pContext, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    public static void deleteDownloadFile(Activity pContext, String pFolderName, String pFileName, ICallBack pCallBack) {
        verifyStoragePermissions(pContext);
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + pFolderName + "/" + pFileName);  // -> filename = maven.pdf
        if (pdfFile != null) {
            pdfFile.delete();
            pCallBack.callBack();
        }
    }

    public static boolean isFileDownloaded(Context pContext, String pFolderName, String pFileName) {
        // File pdfFile = new File(Environment.getExternalStorageDirectory()+ "/" + pFolderName + "/" + pFileName);
        // boolean isSDPresent = Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED);
        //Utils.showProgressBar(pContext);
        if (true/*isSDPresent*/) {
            File file[] = Environment.getExternalStorageDirectory().listFiles();
            if (file != null) {
                for (int i = 0; i < file.length; i++) {
                    File fileList = file[i].getAbsoluteFile();
                    AppLog.log("getAbsolutePath ", fileList.toString());
                    if (fileList.toString().contains(pFolderName)) {
                        File[] files = fileList.listFiles();
                        for (File f : files) {
                            AppLog.log("getAbsolutePath file : " + f.getName());
                            if (f.getName().equals(pFileName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    /**
     * permission is need from Android 6.0
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static String getTimeInDayDateMonthYear(String string, int value) {
        String time = "";
        try {
            if (string != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMMM d, yyyy");
                SimpleDateFormat formatter1 = new SimpleDateFormat("EEE, MMMM d, yyyy");
                Calendar c = Calendar.getInstance();
                c.setTime(formatter.parse(string));
                c.add(Calendar.DATE, value);
                time = formatter1.format(c.getTime());
                AppLog.log("Time: " + time);
            }
        } catch (Exception e) {
            AppLog.errLog("getTimeInDayDateMonthYear from Utils", e.getMessage());
        } finally {
            return time;
        }

    }


    public static String getCurrTimeYYYYMMDD(String string) {
        String time = "";
        try {
            if (string != null) {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMMM d, yyyy");
                Date date = formatter.parse(string);
                time = formatter1.format(date);
                AppLog.log("Time: " + time);
            }
        } catch (Exception e) {
            AppLog.errLog("getCurrTimeYYYYMMDD from Utils", e.getMessage());
        } finally {
            return time;
        }
    }

    public static String getCurrTimeYYYYMMDD() {
        String time = "";
        Calendar c = Calendar.getInstance();
        try {
            if (true) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                time = formatter.format(c.getTime());
                AppLog.log("Time: " + time);
            }
        } catch (Exception e) {
            AppLog.errLog("getCurrTimeYYYYMMDD from Utils", e.getMessage());
        } finally {
            return time;
        }

    }

    /**
     * @param attendanceDate:20170322
     * @return date object
     */
    public static Date getDate(String attendanceDate) {
        Date date = null;
        try {
            if (attendanceDate != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                date = formatter.parse(attendanceDate);
                AppLog.log("getDate from Utils", date.toString());
            }
        } catch (Exception e) {
            AppLog.errLog("getDate from Utils", e.getMessage());
        } finally {
            return date;
        }
    }

    public static void setBounceAni(Context context, ImageView pImgView) {
        Animation an2 = AnimationUtils.loadAnimation(context, R.anim.bounce);
        pImgView.startAnimation(an2);
    }

    /**
     * yyyyMMddHHmmss
     * @return yyyyMMddHHmmss
     */
    public static String getCurrTimeYYYYMMDDOOOOOO() {
        String time = "";
        try {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = formatter.parse(getCurrTime());
                time = formatter1.format(date);
                AppLog.log("Time: " + time);
        } catch (Exception e) {
            AppLog.errLog("getCurrTimeYYYYMMDDOOOOOO from Utils", e.getMessage());
        } finally {
            return "20170301000000";//time  //cheat code
        }
    }
}
