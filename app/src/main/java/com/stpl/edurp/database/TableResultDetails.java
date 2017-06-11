package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableResultDetailsDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableResultDetails {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableResultDetails";
    private static final String TABLE_NAME = "table_resultdetails";
    private static final String COL_REFERENCEID = "referenceid";
    private static final String COL_STUDENTNAME = "subjectname";
    private static final String COL_CREDITS = "credits";
    private static final String COL_GRADE = "grade";
    private static final String COL_RESULT = "result";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_REFERENCEID + " int, "
            + COL_STUDENTNAME + " varchar(255), "
            + COL_CREDITS + " varchar(255), "
            + COL_GRADE + " varchar(255), "
            + COL_RESULT + " varchar(255) "
            + " )";

    //For Foreign key
    //  + " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";


    public  synchronized void openDB(Context pContext) {
        DatabaseHelper helper = DatabaseHelper.getInstance(pContext);
        mDB = helper.getWritableDatabase();
    }

    public  synchronized void closeDB() {
        if (mDB != null) {
            mDB = null;
        }
    }

    //--------------------------------------------------------------------------------------------------------------------

    public  synchronized void dropTable() {
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

    public  synchronized void reset() {
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

    public  synchronized void insert(ArrayList<TableResultDetailsDataModel.InnerResultDetails> list) {
        try {
            if (mDB != null) {
                for (TableResultDetailsDataModel.InnerResultDetails holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_REFERENCEID, holder.getReferenceId());
                    value.put(COL_STUDENTNAME, holder.getSubjectName());
                    value.put(COL_CREDITS, holder.getCredits());
                    value.put(COL_GRADE, holder.getGrade());
                    value.put(COL_RESULT, holder.getResult());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: getReferenceId ", holder.getReferenceId() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public  synchronized boolean isExists(TableResultDetailsDataModel.InnerResultDetails model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_CREDITS + " = '" + model.getReferenceId() + "'";
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

    public  synchronized boolean deleteRecord(TableResultDetailsDataModel.InnerResultDetails holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_REFERENCEID + "=?", new String[]{"" + holder.getReferenceId()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableResultDetailsDataModel.InnerResultDetails" + e.getMessage());
        }
        return false;
    }




    public ArrayList<TableResultDetailsDataModel.InnerResultDetails> getValue(int pRefId) {
        try {
            ArrayList<TableResultDetailsDataModel.InnerResultDetails> holder = new ArrayList<TableResultDetailsDataModel.InnerResultDetails>();

            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME+ " WHERE " +COL_REFERENCEID+ "= '" + pRefId +"'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        TableResultDetailsDataModel.InnerResultDetails model = new TableResultDetailsDataModel.InnerResultDetails();
                        // get the data into array, or class variable
                        model.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                        model.setSubjectName((cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME))));
                        model.setCredits((cursor.getString(cursor.getColumnIndex(COL_CREDITS))));
                        model.setGrade((cursor.getString(cursor.getColumnIndex(COL_GRADE))));
                        model.setResult((cursor.getString(cursor.getColumnIndex(COL_RESULT))));
                        holder.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return holder;
        } catch (Exception e) {
            AppLog.errLog(TAG, "getValue " + e.getMessage());
            return null;
        }
    }


}
