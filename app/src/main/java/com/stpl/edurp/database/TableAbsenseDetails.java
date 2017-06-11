package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableAbsenseDetailsDataModel;
import com.stpl.edurp.models.TableAbsenseDetailsDataModel.MessageBodyDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableAbsenseDetails {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableAbsenseDetails";
    public static final String TABLE_NAME = "table_absensedetails";
    public static final String REFERENCE_ID = "ReferenceId";
    public static final String SUBJECT_ID = "SubjectId";
    public static final String ABSENCE_DATE = "AbsenceDate";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE_DIARY = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + REFERENCE_ID + " int, "
            + SUBJECT_ID + " int, "
            + ABSENCE_DATE + " nchar(100) "
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

    public synchronized  boolean insert(ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel> list) {
        try {
            if (mDB != null) {
                deleteDataIfExist(list.get(0).getReferenceId(), list.get(0).getSubjectId());
                for (TableAbsenseDetailsDataModel.MessageBodyDataModel holder : list) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(REFERENCE_ID, holder.getReferenceId());
                    contentValues.put(SUBJECT_ID, holder.getSubjectId());
                    contentValues.put(ABSENCE_DATE, holder.getAbsenceDate());
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

    private synchronized void deleteDataIfExist(int pRefID, String pSubId) {
        try {
            String selectQuery = "DELETE FROM " + TABLE_NAME + " WHERE "
                    + REFERENCE_ID + "= '" + pRefID + "'  AND "
                    + SUBJECT_ID + "= '" + pSubId + "'";
            AppLog.log(TAG, "deleteDataIfExist +++selectQuery():" + selectQuery.toString());
            mDB.execSQL(selectQuery);
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from deleteDataIfExist " + e.getMessage());
        }
    }


    public synchronized ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel> getValueBySem(String pCourse, String pSemseter) {
        try {
            ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel> holder = new ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel>();

            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ABSENCE_DATE + "='" + pSemseter + "' and "
                        + SUBJECT_ID + "='" + pCourse + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        TableAbsenseDetailsDataModel.MessageBodyDataModel model = new TableAbsenseDetailsDataModel.MessageBodyDataModel();
                        // get the data into array, or class variable
                        model.setReferenceId(cursor.getInt(cursor.getColumnIndex(REFERENCE_ID)));
                        model.setAbsenceDate((cursor.getString(cursor.getColumnIndex(ABSENCE_DATE))));
                        model.setSubjectId((cursor.getString(cursor.getColumnIndex(SUBJECT_ID))));
                        holder.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return holder;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getValueBySem() " + e.getMessage());
            return null;
        }
    }

    public synchronized ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel> getValueByDate(String pAttendanceDate) {
        try {
            ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel> holder = new ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel>();

            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ABSENCE_DATE + "='" + pAttendanceDate + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        TableAbsenseDetailsDataModel.MessageBodyDataModel model = new TableAbsenseDetailsDataModel.MessageBodyDataModel();
                        // get the data into array, or class variable
                        model.setReferenceId(cursor.getInt(cursor.getColumnIndex(REFERENCE_ID)));
                        model.setAbsenceDate((cursor.getString(cursor.getColumnIndex(ABSENCE_DATE))));
                        model.setSubjectId((cursor.getString(cursor.getColumnIndex(SUBJECT_ID))));
                        holder.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return holder;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getValueByDate() " + e.getMessage());
            return null;
        }
    }

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

    public synchronized  void reset() {
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


    public synchronized  ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel> getValue(int studentId, int pRefID) {
        try {
            ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel> holder = new ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel>();

            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
                        + REFERENCE_ID + "='" + pRefID +"' and "
                        + SUBJECT_ID + "='" + studentId +"'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        TableAbsenseDetailsDataModel.MessageBodyDataModel model = new MessageBodyDataModel();
                        // get the data into array, or class variable
                        model.setReferenceId(cursor.getInt(cursor.getColumnIndex(REFERENCE_ID)));
                        model.setAbsenceDate((cursor.getString(cursor.getColumnIndex(ABSENCE_DATE))));
                        model.setSubjectId((cursor.getString(cursor.getColumnIndex(SUBJECT_ID))));
                        holder.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return holder;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from getValue " + e.getMessage());
            return null;
        }

    }
}
