package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableAttendanceDetailsDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableAttendanceDetails {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableAtbtendanceDetails";
    public static final String TABLE_NAME = "table_attendacedetails";
    public static final String SUBJECTID = "subjectid";
    public static final String SUBJECT = "Subject";
    public static final String TOTAL = "total";
    public static final String PRESENT = "present";
    public static final String ABSENT = "absent";
    public static final String PERCENTAGE = "percentage";
    public static final String COLOR = "color";
    public static final String REFENCEID = "referenceid";

    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE_DIARY = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + SUBJECTID + " int, "
            + REFENCEID + " int, "
            + SUBJECT + " varchar(255), "
            + TOTAL + " varchar(50), "
            + PRESENT + " varchar(50), "
            + ABSENT + " varchar(50), "
            + PERCENTAGE + " varchar(50), "
            + COLOR + " varchar(50) "
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

    public boolean insert(ArrayList<TableAttendanceDetailsDataModel> list) {
        try {
            if(list.size()<= 0){
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "StudentAttendanceDetailList is empty. Contact to DB", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (mDB != null) {
                deleteDataIfExist(list.get(0).getSubjectId(), list.get(0).getSemester(), list.get(0).getReferenceId());
                for (TableAttendanceDetailsDataModel holder : list) {
                    deleteDataIfExist(holder.getSubjectId(), holder.getSemester(), holder.getReferenceId());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SUBJECTID, holder.getSubjectId());
                    contentValues.put(SUBJECT, holder.getSubject());
                    contentValues.put(TOTAL, holder.getTotal());
                    contentValues.put(PRESENT, holder.getPresent());
                    contentValues.put(ABSENT, holder.getAbsent());
                    contentValues.put(PERCENTAGE, holder.getPercentage());
                    contentValues.put(COLOR, holder.getColor());
                    contentValues.put(REFENCEID, holder.getReferenceId());
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

    private void deleteDataIfExist(int pSubject, String pSemester, int pRefID) {
        try {
            String selectQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + SUBJECTID + "= '" + pSubject
                    + "'  AND " + REFENCEID + "= '" + pRefID + "'";
            AppLog.log(TAG, "deleteDataIfExist +++selectQuery():" + selectQuery.toString());
            mDB.execSQL(selectQuery);
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from deleteDataIfExist " + e.getMessage());
        }
    }


    public ArrayList<TableAttendanceDetailsDataModel> getValue(int pRefId) {
        try {
            ArrayList<TableAttendanceDetailsDataModel> holder = new ArrayList<TableAttendanceDetailsDataModel>();

            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " INNER JOIN "
                        + TableCourseMaster.TABLE_NAME + " on " + TABLE_NAME + "." + REFENCEID + "=" + TableCourseMaster.TABLE_NAME + "." + TableCourseMaster.REFERENCEID + " WHERE " + REFENCEID + "='" + pRefId + "'" ;
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        TableAttendanceDetailsDataModel model = new TableAttendanceDetailsDataModel();
                        // get the data into array, or class variable
                        model.setSubjectId(cursor.getInt(cursor.getColumnIndex(SUBJECTID)));
                        model.setSemester((cursor.getString(cursor.getColumnIndex(TableCourseMaster.SEMESTER))));
                        model.setCourse((cursor.getString(cursor.getColumnIndex(TableCourseMaster.COURSE))));
                        model.setSubject((cursor.getString(cursor.getColumnIndex(SUBJECT))));
                        model.setTotal((cursor.getString(cursor.getColumnIndex(TOTAL))));
                        model.setPresent((cursor.getString(cursor.getColumnIndex(PRESENT))));
                        model.setPercentage((cursor.getString(cursor.getColumnIndex(PERCENTAGE))));
                        model.setAbsent((cursor.getString(cursor.getColumnIndex(ABSENT))));
                        model.setColor(cursor.getString(cursor.getColumnIndex(COLOR)));
                        model.setReferenceId(cursor.getInt(cursor.getColumnIndex(REFENCEID)));
                        holder.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return holder;
        } catch (Exception e) {
            AppLog.errLog(TAG, " getValueBySem() " + e.getMessage());
            return null;
        }
    }


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
                mDB.execSQL(TRUNCATE_TABLE_DIARY);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }


}
