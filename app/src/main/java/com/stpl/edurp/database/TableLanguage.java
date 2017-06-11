package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableLanguageDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableLanguage {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableLanguage";
    public static final String TABLE_NAME = "table_language";
    public static final String UNIVERSITY_ID = "UniversityId";
    public static final String CONVERSION_ID = "ConversionId";
    public static final String CONVERSION_CODE = "ConversionCode";
    public static final String ENGLISH_VERSION = "EnglishVersion";
    public static final String BAHASA_VERSION = "BahasaVersion";
    public static final String DATE_MODIFIED = "DateModified";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE_DIARY = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE_DIARY = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_LANGUAGE_TABLE = "Create table " + TABLE_NAME + "( "
            + UNIVERSITY_ID + " int, "
            + CONVERSION_ID + " int, "
            + CONVERSION_CODE + " varchar(255), "
            + ENGLISH_VERSION + " varchar(255), "
            + BAHASA_VERSION + " varchar(255), "
            + DATE_MODIFIED + " varchar(255) "
            + " ) ";
    //For Foreign key
    //  + " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";

    public  synchronized void openDB(Context pContext) {
        DatabaseHelper helper = DatabaseHelper.getInstance(pContext);
        mDB = helper.getWritableDatabase();
    }

    public synchronized  void closeDB() {
        if (mDB != null) {
            mDB = null;
        }
    }

    //--------------------------------------------------------------------------------------------------------------------

    public  synchronized boolean insert(ArrayList<TableLanguageDataModel> list) {
        try {
            if (mDB != null) {
                for (TableLanguageDataModel holder : list) {
                    AppLog.log(TAG, "TableLanguageDataModel insert()getUniversityId: " + holder.getUniversityId());
                    AppLog.log(TAG, "TableLanguageDataModel insert()getConversionId:" + holder.getConversionId());
                    deleteDataIfExist(holder.getUniversityId(), holder.getConversionId());
                    AppLog.log(TAG, "TableLanguageDataModel +++insert():" + holder.getUniversityId());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(UNIVERSITY_ID, holder.getUniversityId());
                    contentValues.put(CONVERSION_ID, holder.getConversionId());
                    contentValues.put(CONVERSION_CODE, holder.getConversionCode());
                    contentValues.put(ENGLISH_VERSION, holder.getEnglishVersion());
                    contentValues.put(BAHASA_VERSION, holder.getBahasaVersion());
                    contentValues.put(DATE_MODIFIED, holder.getDateModified());
                    mDB.insert(TABLE_NAME, null, contentValues);
                }
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return false;
        }
        return false;
    }

    private  synchronized void deleteDataIfExist(int pUniversityId, int pConversionId) {
        try {
            String selectQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + UNIVERSITY_ID + "=" + pUniversityId + " AND " + CONVERSION_ID + "=" + pConversionId;
            AppLog.log(TAG, "deleteDataIfExist +++selectQuery():" + selectQuery.toString());
            mDB.execSQL(selectQuery);
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from deleteDataIfExist " + e.getMessage());
        }
    }

    public  synchronized ArrayList<TableLanguageDataModel> read() {
        try {
            ArrayList<TableLanguageDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME;
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableLanguageDataModel model = new TableLanguageDataModel();
                        model.setUniversityId(cursor.getInt(cursor.getColumnIndex(UNIVERSITY_ID)));
                        model.setConversionCode((cursor.getString(cursor.getColumnIndex(CONVERSION_CODE))));
                        model.setConversionCode((cursor.getString(cursor.getColumnIndex(CONVERSION_ID))));
                        model.setEnglishVersion((cursor.getString(cursor.getColumnIndex(ENGLISH_VERSION))));
                        model.setBahasaVersion((cursor.getString(cursor.getColumnIndex(BAHASA_VERSION))));
                        model.setDateModified((cursor.getString(cursor.getColumnIndex(DATE_MODIFIED))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    public  synchronized ArrayList<TableLanguageDataModel> read(int pUniID) {
        try {
            ArrayList<TableLanguageDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + UNIVERSITY_ID + "='" + pUniID + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableLanguageDataModel model = new TableLanguageDataModel();
                        model.setUniversityId(cursor.getInt(cursor.getColumnIndex(UNIVERSITY_ID)));
                        model.setConversionCode((cursor.getString(cursor.getColumnIndex(CONVERSION_CODE))));
                        model.setConversionCode((cursor.getString(cursor.getColumnIndex(CONVERSION_ID))));
                        model.setEnglishVersion((cursor.getString(cursor.getColumnIndex(ENGLISH_VERSION))));
                        model.setBahasaVersion((cursor.getString(cursor.getColumnIndex(BAHASA_VERSION))));
                        model.setDateModified((cursor.getString(cursor.getColumnIndex(DATE_MODIFIED))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    public  synchronized TableLanguageDataModel getValue(String pKey) {
        try {
            TableLanguageDataModel model = new TableLanguageDataModel();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + CONVERSION_CODE + "='" + pKey + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        model.setUniversityId(cursor.getInt(cursor.getColumnIndex(UNIVERSITY_ID)));
                        model.setConversionCode((cursor.getString(cursor.getColumnIndex(CONVERSION_CODE))));
                        model.setConversionCode((cursor.getString(cursor.getColumnIndex(CONVERSION_ID))));
                        model.setEnglishVersion((cursor.getString(cursor.getColumnIndex(ENGLISH_VERSION))));
                        model.setBahasaVersion((cursor.getString(cursor.getColumnIndex(BAHASA_VERSION))));
                        model.setDateModified((cursor.getString(cursor.getColumnIndex(DATE_MODIFIED))));
                        AppLog.log(TAG, "lang getVAlue for  " + pKey +" ENG "+model.getEnglishVersion());
                        AppLog.log(TAG, "lang getVAlue for  " + pKey +" Bhasha "+model.getBahasaVersion());
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return model;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    public  synchronized void dropTable() {
        try {
            if (mDB != null) {
                mDB.execSQL(DROP_TABLE_DIARY);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }

    public  synchronized void reset() {
        try {
            if (mDB != null) {
                mDB.execSQL(TRUNCATE_TABLE_DIARY);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }


}
