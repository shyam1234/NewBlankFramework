package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableUniversityMasterDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableUniversityMaster {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableUniversityMaster";
    public static final String TABLE_NAME = "table_university_master";
    public static final String COL_UNIVERSITY_ID = "UniversityId";
    public static final String COL_UNIVERSITY_NAME = "UniversityName";
    public static final String COL_UNIVERSITY_URL = "university_url";
    public static final String COL_UNIVERSITY_CODE = "UniversityCode";
    public static final String COL_UNIVERSITY_LOGO = "UniversityLogoPath";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_UNIVERSITY_ID + " integer , "
            + COL_UNIVERSITY_NAME + " varchar(255), "
            + COL_UNIVERSITY_URL + " varchar(255), "
            + COL_UNIVERSITY_CODE + " varchar(255), "
            + COL_UNIVERSITY_LOGO + " varchar(255) "
            + " ) ";
    //For Foreign key
    //  + " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";


    public void openDB(Context pContext) {
        DatabaseHelper helper = DatabaseHelper.getInstance(pContext);
        mDB = helper.getWritableDatabase();
    }

    public void closeDB() {
        if (mDB != null) {
            mDB = null;
        }
    }

    //--------------------------------------------------------------------------------------------------------------------

    public void dropTable() {
        try {
            if (mDB != null) {
                mDB.execSQL(DROP_TABLE);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }

    public void reset() {
        try {
            if (mDB != null) {
                mDB.execSQL(TRUNCATE_TABLE);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------

    public void insert(ArrayList<TableUniversityMasterDataModel> list) {
        try {
            if (mDB != null) {
                for (TableUniversityMasterDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_UNIVERSITY_ID, holder.getUniversity_id());
                    value.put(COL_UNIVERSITY_NAME, holder.getUniversity_name());
                    value.put(COL_UNIVERSITY_URL, holder.getUniversity_url());
                    value.put(COL_UNIVERSITY_CODE, holder.getUniversity_code());
                    value.put(COL_UNIVERSITY_LOGO, holder.getUniversity_logo_path());
                    value.put(COL_UNIVERSITY_ID, holder.getUniversity_id());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: ", holder.getUniversity_id() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public boolean isExists(TableUniversityMasterDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_UNIVERSITY_ID + " = '" + model.getUniversity_id()+"'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    AppLog.log("isExists ", "" + true);
                    return true;
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog("isIDExists", e.getMessage());
        }
        return false;
    }

    public boolean deleteRecord(TableUniversityMasterDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_UNIVERSITY_ID + "=?", new String[]{""+holder.getUniversity_id()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableUniversityMasterDataModel" + e.getMessage());
        }
        return false;
    }


}
