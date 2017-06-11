package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableStudentDetailsDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableStudentDetails {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableStudentDetails";
    public static final String TABLE_NAME = "table_student_details";
    public static final String COL_COURSE = "Course";
    public static final String COL_GENDER = "Gender";
    public static final String COL_IMAGEURL = "ImageURL";
    public static final String COL_STUDENT_ID = "StudentId";
    public static final String COL_STUDENT_NAME = "StudentName";
    public static final String COL_UNIVERSITY_ID = "UniversityId";
    public static final String COL_STUDENTNUMBER = "StudentNumber";
    public static final String COL_DATAOFBIRTH = "DateOfBirth";
    public static final String COL_LASTRETRIEVEDON = "LastRetrievedOn";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_COURSE + " varchar(255), "
            + COL_GENDER + " varchar(255), "
            + COL_IMAGEURL + " varchar(255), "
            + COL_STUDENT_ID + " integer, "
            + COL_STUDENT_NAME + " varchar(255), "
            + COL_UNIVERSITY_ID + " integer , "
            + COL_STUDENTNUMBER + " varchar(255) , "
            + COL_LASTRETRIEVEDON + " datetime , "
            + COL_DATAOFBIRTH + " varchar(255) "
            + " ) ";
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

    public synchronized  void insert(ArrayList<TableStudentDetailsDataModel> list) {
        try {
            if (mDB != null) {
                for (TableStudentDetailsDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_COURSE, holder.getCourseCode());
                    value.put(COL_GENDER, holder.getGender());
                    value.put(COL_IMAGEURL, holder.getImageurl());
                    value.put(COL_STUDENT_ID, holder.getStudent_id());
                    value.put(COL_STUDENT_NAME, holder.getFullName());
                    value.put(COL_UNIVERSITY_ID, holder.getUniversity_id());
                    value.put(COL_STUDENTNUMBER, holder.getStudentNumber());
                    value.put(COL_LASTRETRIEVEDON, holder.getLastRetrievedOn());
                    value.put(COL_DATAOFBIRTH, holder.getDateOfBirth());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: ", holder.getStudent_id() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public  synchronized boolean isExists(TableStudentDetailsDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_STUDENT_ID + " = '" + model.getStudent_id() + "'";
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

    public  synchronized boolean deleteRecord(TableStudentDetailsDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_STUDENT_ID + "=?", new String[]{""+holder.getStudent_id()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableStudentDetailsDataModel" + e.getMessage());
        }
        return false;
    }


    public  synchronized ArrayList<TableStudentDetailsDataModel> getStudentInfo(String studentId) {
        ArrayList<TableStudentDetailsDataModel> list = new ArrayList<TableStudentDetailsDataModel>();
        try {
            AppLog.log("getStudentInfo++++++", "");
            if (mDB != null) {
                String selectQuery = "Select * from " + TABLE_NAME
                        + " WHERE " + COL_STUDENT_ID + "='" + studentId
                        + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableStudentDetailsDataModel model = new TableStudentDetailsDataModel();
                        model.setGender(cursor.getString(cursor.getColumnIndex(COL_GENDER)));
                        model.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENT_ID)));
                        model.setCourseCode(cursor.getString(cursor.getColumnIndex(COL_COURSE)));
                        model.setImageurl(cursor.getString(cursor.getColumnIndex(COL_IMAGEURL)));
                        model.setFullName(cursor.getString(cursor.getColumnIndex(COL_STUDENT_NAME)));
                        model.setUniversity_id(cursor.getInt(cursor.getColumnIndex(COL_UNIVERSITY_ID)));
                        model.setStudentNumber(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                        model.setLastRetrievedOn(cursor.getString(cursor.getColumnIndex(COL_LASTRETRIEVEDON)));
                        model.setDateOfBirth(cursor.getString(cursor.getColumnIndex(COL_DATAOFBIRTH)));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog("getStudentInfo", e.getMessage());
        } finally {
            return list;
        }

    }
}
