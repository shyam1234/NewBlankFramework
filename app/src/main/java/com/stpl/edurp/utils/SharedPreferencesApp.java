package com.stpl.edurp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.constant.WSContant;

/**
 * Created by Admin on 31-12-2016.
 */

public class SharedPreferencesApp {
    private static final String DEFAULT_SHAREPREF = "edurp_sharepref";
    private static SharedPreferencesApp mInstance;
    private String mLastLoginTime = "";


    public static SharedPreferencesApp getInstance() {
        synchronized (SharedPreferencesApp.class) {
            if (mInstance == null) {
                mInstance = new SharedPreferencesApp();
                //retrieve value from store
                getAllStoreData();
            }
        }
        return mInstance;
    }

    public String getLastLangSync() {
        String str = "";
        try {

            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                str = sharePref.getString(WSContant.TAG_LANGUAGE_VERSION_DATE, "");
            } else {
                AppLog.log("getLastLangSync", "there is not university language history");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("Exception from getLastLangSync", e.getMessage());
        }
        return str;
    }


    public void saveLastLangSync(String pLastUpdateDate) {
        try {
            AppLog.log("saveLastLangSync", "");
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_LANGUAGE_VERSION_DATE, pLastUpdateDate);
            data.commit();
        } catch (Exception e) {
            AppLog.errLog("Exception from saveLastLangSync", e.getMessage());
        }
    }


    public void removeLastLangSync() {
        try {
            saveLastLangSync(null);
        } catch (Exception e) {
            AppLog.errLog("Exception from removeLastLangSync", e.getMessage());
        }
    }


    public void removeAll() {
        //removeLastLangSync();
        removeLastSavedUniversity();
        removeSavedTime();
        removeAuthToken();
        removeLastGetMobileMenuTime();
        removeLastLoginTime();
        removeSavedTimeForNews();
        removeSelectedLangPref();
        UserInfo.clearUSerInfo();
        savedDefaultChildSelection(-1);
    }

    public void removeAllAlongWithLang() {
        removeLastLangSync();
        removeAll();
    }


    //-----------------------------------------------------------

    public void saveTime(String currTime) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_SHAREDPREF_GET_LAST_TIME, currTime);
            data.commit();
            AppLog.log("sharePreferenceApp", "saveTime: " + currTime);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveTime", e.getMessage());
        }
    }

    public String getSavedTime() {
        String mCurrTime = "";
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                mCurrTime = sharePref.getString(WSContant.TAG_SHAREDPREF_GET_LAST_TIME, "");
            } else {
                AppLog.log("getSavedTime", "there is not savedTime ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getSavedTime", e.getMessage());
        } finally {
            return mCurrTime;
        }
    }

    public void removeSavedTime() {
        try {
            saveTime(null);
        } catch (Exception e) {
            AppLog.errLog(" removeSavedTime", e.getMessage());
        }
    }

    //-----------------------------------------------------------
    public void saveLastLoginTime(String currTime) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_LAST_LOGIN_TIME, currTime);
            data.commit();
            AppLog.log("sharePreferenceApp", "saveLastLoginTime: " + currTime);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveLastLoginTime", e.getMessage());
        }
    }

    public String getLastLoginTime() {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                mLastLoginTime = sharePref.getString(WSContant.TAG_LAST_LOGIN_TIME, "");
            } else {
                AppLog.log("getLastLoginTime", "there is not savedTime ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getLastLoginTime", e.getMessage());
        } finally {
            return mLastLoginTime;
        }
    }

    public void removeLastLoginTime() {
        try {
            mLastLoginTime = null;
            saveTime(mLastLoginTime);
        } catch (Exception e) {
            AppLog.errLog(" removeLastLoginTime", e.getMessage());
        }
    }

    //-----------------------------------------------------------
/*    public void saveLangRetriveTime(String currTime) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_LANG_RETRIVE_TIME, currTime);
            data.commit();
            AppLog.log("sharePreferenceApp", "saveLangRetriveTime: " + currTime);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveLangRetriveTime", e.getMessage());
        }
    }

    public String getLangRetriveTime() {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                mLangRetrivTime = sharePref.getString(WSContant.TAG_LANG_RETRIVE_TIME, null);
            } else {
                AppLog.log("getLangRetriveTime", "there is not savedTime ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getLangRetriveTime", e.getMessage());
        } finally {
            return mLangRetrivTime;
        }
    }

    public void removeLangRetriveTime() {
        try {
            mLangRetrivTime = null;
            saveTime(mLangRetrivTime);
        } catch (Exception e) {
            AppLog.errLog(" removeLangRetriveTime", e.getMessage());
        }
    }*/
//-----------------------------------------------------------------------------------
    public void saveAuthToken(String authToken, int id, String currUserType,
                              String parentName, String currUserName, String currUserEmail, String currUserPhoneNumber) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_AUTHTOKEN, authToken);
            data.putInt(WSContant.TAG_SAVED_USERID, id);
            data.putString(WSContant.TAG_USER_TYPE, currUserType);

            data.putString(WSContant.TAG_PARENT_NAME, parentName);
            data.putString(WSContant.TAG_CURR_USER_NAME, currUserName);
            data.putString(WSContant.TAG_USER_EMAIL, currUserEmail);
            data.putString(WSContant.TAG_USER_PHONE_NUMBER, currUserPhoneNumber);

            data.commit();
            AppLog.log("sharePreferenceApp", "saveAuthToken: " + authToken);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveAuthToken", e.getMessage());
        }
    }

    private static void getAllStoreData() {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                UserInfo.parentId = UserInfo.userId = sharePref.getInt(WSContant.TAG_SAVED_USERID, -1);
                //UserInfo.studentId = sharePref.getInt(WSContant.TAG_DEFAULT_CHILD, -1);
                UserInfo.authToken = sharePref.getString(WSContant.TAG_AUTHTOKEN, null);
                UserInfo.lang_pref = sharePref.getString(WSContant.TAG_LANG, WSContant.TAG_ENG);
                UserInfo.currUserType = sharePref.getString(WSContant.TAG_USER_TYPE, null);

                UserInfo.parentName = sharePref.getString(WSContant.TAG_PARENT_NAME, null);
                UserInfo.currUserName =sharePref.getString(WSContant.TAG_CURR_USER_NAME, null);
                UserInfo.currUserEmail =sharePref.getString(WSContant.TAG_USER_EMAIL, null);
                UserInfo.currUserPhoneNumber = sharePref.getString(WSContant.TAG_USER_PHONE_NUMBER, null);
                //--------------------------------------------------------------------------------------
                AppLog.log("getAllStoreData", " UserInfo.userId: " + UserInfo.userId);
                AppLog.log("getAllStoreData", " UserInfo.authToken: " + UserInfo.authToken);
                AppLog.log("getAllStoreData", " UserInfo.currUserType: " + UserInfo.currUserType);
                AppLog.log("getAllStoreData", " UserInfo.parentId: " + UserInfo.parentId);
                AppLog.log("getAllStoreData", " UserInfo.studentId: " + UserInfo.studentId);

                AppLog.log("getAllStoreData", " UserInfo.parentName: " + UserInfo.parentName);
                AppLog.log("getAllStoreData", " UserInfo.currUserName: " + UserInfo.currUserName);
                AppLog.log("getAllStoreData", " UserInfo.currUserEmail: " + UserInfo.currUserEmail);
                AppLog.log("getAllStoreData", " UserInfo.currUserPhoneNumber: " + UserInfo.currUserPhoneNumber);

            } else {
                AppLog.log("getUserInfo", "there is not getAllStoreData ");
            }
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getAllStoreData", e.getMessage());
        }
    }


    public void removeAuthToken() {
        try {
            saveAuthToken(null, -1, null, UserInfo.parentName, UserInfo.currUserName, UserInfo.currUserEmail, UserInfo.currUserPhoneNumber);
        } catch (Exception e) {
            AppLog.errLog(" removeAuthToken", e.getMessage());
        }
    }


    //--------------------------------------------------------------------------------------------------------

    public void removeLastSavedUniversity() {
        try {
            saveLastSavedUniversityID("1");
        } catch (Exception e) {
            AppLog.errLog(" removeLastSavedUniversity", e.getMessage());
        }
    }


    public void saveLastSavedUniversityID(String langID) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_UNIVERSITYID, langID);
            UserInfo.univercityId = Integer.parseInt(langID);
            data.commit();
            AppLog.log("sharePreferenceApp", "saveLastSavedUniversityID: " + langID);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveLastSavedUniversityID", e.getMessage());
        }
    }

    public String getLastSavedUniversityID() {
        String str = "";
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                str = sharePref.getString(WSContant.TAG_UNIVERSITYID, "1");
                UserInfo.univercityId = Integer.parseInt(str);
            } else {
                AppLog.log("getLastSavedUniversityID", "there is not getAllStoreData ");
            }
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getLastSavedUniversityID", e.getMessage());
        }
        return str;
    }


    //-----------------------------------------------------------------------
    public void savedDefaultChildSelection(int studentId) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putInt(WSContant.TAG_DEFAULT_CHILD, studentId);
            UserInfo.studentId = studentId;
            data.commit();
            AppLog.log("sharePreferenceApp", "savedDefaultChildSelection: " + studentId);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp savedDefaultChildSelection", e.getMessage());
        }
    }

    public int getDefaultChildSelection() {
        try {
            int child_id = -1;
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                child_id = sharePref.getInt(WSContant.TAG_DEFAULT_CHILD, -1);
                AppLog.log("getDefaultChildSelection", "default child " + child_id);
            } else {
                AppLog.log("getDefaultChildSelection", "there is not getAllStoreData ");
            }
            return child_id;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getDefaultChildSelection", e.getMessage());
        }
        return -1;
    }

    //-----------------------------------------------------------

    public void saveTimeForNews(String currTime) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_NEWS_GET_LAST_TIME, currTime);
            data.commit();
            AppLog.log("sharePreferenceApp", "saveTimeForNews: " + currTime);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveTimeForNews", e.getMessage());
        }
    }

    public String getSavedTimeForNews() {
        String mCurrTime = "";
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                mCurrTime = sharePref.getString(WSContant.TAG_NEWS_GET_LAST_TIME, "");
            } else {
                AppLog.log("getSavedTime", "there is not getSavedTimeForNews ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getSavedTimeForNews", e.getMessage());
        } finally {
            return mCurrTime;
        }
    }

    public void removeSavedTimeForNews() {
        try {
            saveTime(null);
        } catch (Exception e) {
            AppLog.errLog(" removeSavedTimeForNews", e.getMessage());
        }
    }


    //------------------------------------------------------------------------------------------------

    public void saveGetMobileMenuTime(String currTime) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_LAST_LOGIN_TIME, currTime);
            data.commit();
            AppLog.log("sharePreferenceApp", "saveGetMobileMenuTime: " + currTime);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveGetMobileMenuTime", e.getMessage());
        }
    }

    public String getLastGetMobileMenuTime() {
        String mLastLoginTime = "";
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                mLastLoginTime = sharePref.getString(WSContant.TAG_LAST_LOGIN_TIME, "");
            } else {
                AppLog.log("getLastGetMobileMenuTime", "there is not savedTime ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getLastLoginTime", e.getMessage());
        } finally {
            return mLastLoginTime;
        }
    }

    public void removeLastGetMobileMenuTime() {
        try {
            mLastLoginTime = null;
            saveTime(mLastLoginTime);
        } catch (Exception e) {
            AppLog.errLog(" removeLastGetMobileMenuTime", e.getMessage());
        }
    }

    //-----------------------------------------------------------

    public void saveLangSelection(String pLangSelection) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_LANG, pLangSelection);
            data.commit();
            UserInfo.lang_pref = pLangSelection;
            AppLog.log("sharePreferenceApp", "saveLangSelection: " + pLangSelection);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveLangSelection", e.getMessage());
        }
    }

    public String getLangSelection() {
        UserInfo.lang_pref = null;
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                UserInfo.lang_pref = sharePref.getString(WSContant.TAG_LANG, WSContant.TAG_ENG);
            } else {
                AppLog.log("getLangSelection", "there is not saved land selection ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getLangSelection", e.getMessage());
        } finally {
            return UserInfo.lang_pref == null ? WSContant.TAG_ENG : UserInfo.lang_pref;
        }
    }


    public void removeSelectedLangPref() {
        try {
            saveLangSelection(WSContant.TAG_ENG);
        } catch (Exception e) {
            AppLog.errLog(" removeSelectedLangPref", e.getMessage());
        }
    }


    //--using currently to save and retrieve time ---------------------------------------------------------
    public String getLastRetrieveTime(String pTAG) {
        String mCurrTime = "";
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                mCurrTime = sharePref.getString(pTAG, "");
            } else {
                AppLog.log("getSavedTime", "there is not savedTime ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getSavedTime", e.getMessage());
        } finally {
            return mCurrTime;
        }
    }

    public void setLastRetrieveTime(String pTAG, String time) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                SharedPreferences.Editor data = sharePref.edit();
                data.putString(pTAG, time);
                data.commit();
            } else {
                AppLog.log("setLastRetrieveTime", "there is not setLastRetrieveTime ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : setLastRetrieveTime", e.getMessage());
        }
    }
    //-----------------------------------------------------------------------------------------------
}
