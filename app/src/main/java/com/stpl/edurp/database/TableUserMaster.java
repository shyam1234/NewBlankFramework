package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableUserMasterDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableUserMaster {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableUserMaster";
    private static final String TABLE_NAME = "table_usermaster";
    private static final String COL_USERID = "UserId";
    private static final String COL_USERNAME = "UserName";
    private static final String COL_USERTYPE = "UserType";
    private static final String COL_DESIGNATION = "Designation";
    private static final String COL_IMAGEURL = "ImageURL";

    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_USERID + " int , "
            + COL_USERNAME + " varchar (100) , "
            + COL_USERTYPE + " char(1), "
            + COL_DESIGNATION + " varchar(100), "
            + COL_IMAGEURL + "  varchar(100) "
            + " )";

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

    public void insert(ArrayList<TableUserMasterDataModel> list) {
        try {
            if (mDB != null) {
                for (TableUserMasterDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_DESIGNATION, holder.getDesignation());
                    value.put(COL_IMAGEURL, holder.getImageURL());
                    value.put(COL_USERID, holder.getUserId());
                    value.put(COL_USERNAME, holder.getUserName());
                    value.put(COL_USERTYPE, holder.getUserType());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: ", holder.getUserId() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public boolean isExists(TableUserMasterDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_USERID + " = '" + model.getUserId() + "'";
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

    public boolean deleteRecord(TableUserMasterDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_USERID + "=?", new String[]{"" + holder.getUserId()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableUserMaster" + e.getMessage());
        }
        return false;
    }


}
