package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableTimeTableDetailsDataModel;
import com.stpl.edurp.models.TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableTimeTableDetails {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableTimeTableDetails";
    private static final String TABLE_NAME = "table_timetabledetails";
    private static final String COL_MENUCODE = "menucode";
    private static final String COL_STUDENTID = "studentid";
    private static final String COL_REFERENCEDATE = "referencedate";
    private static final String COL_SUBJECTNAME = "subjectname";
    private static final String COL_TTIME = "ttime";
    private static final String COL_FACULTY = "faculty";
    private static final String COL_ROOMNAME = "roomname";
    private static final String COL_ISPRESENT = "ispresent";
    private static final String COL_SQORDER = "sqorder";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_MENUCODE + " char(3), "
            + COL_STUDENTID + " int , "
            + COL_REFERENCEDATE + " varchar(50), "
            + COL_SUBJECTNAME + " varchar(255), "
            + COL_TTIME + " varchar(50), "
            + COL_FACULTY + " varchar(100), "
            + COL_ROOMNAME + " varchar(50), "
            + COL_ISPRESENT + " varchar(255), "
            + COL_SQORDER + " varchar(50) "
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

    public void insert(ArrayList<TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel> list) {
        try {
            if (mDB != null) {
                for (TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_MENUCODE, holder.getMenuCode());
                    value.put(COL_STUDENTID, holder.getStudentId());
                    value.put(COL_REFERENCEDATE, holder.getReferenceDate());
                    value.put(COL_SUBJECTNAME, holder.getSubjectName());
                    value.put(COL_TTIME, holder.getTTime());
                    value.put(COL_FACULTY, holder.getFaculty());
                    value.put(COL_ROOMNAME, holder.getRoomName());
                    value.put(COL_ISPRESENT, holder.getIsPresent());
                    value.put(COL_SQORDER, holder.getSqOrder());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: getSubject ", holder.getSubjectName() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public boolean isExists(TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_MENUCODE + " = '" + model.getMenuCode() + "'"
                    + " and " + COL_REFERENCEDATE + " = '" + model.getReferenceDate() + "'"
                    + " and " + COL_STUDENTID + " = '" + model.getStudentId() + "'";
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

    public boolean deleteRecord(TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_SUBJECTNAME + "=? and " + COL_REFERENCEDATE + "=? " +
                        "and " + COL_STUDENTID + "=?", new String[]{holder.getSubjectName(), "" + holder.getReferenceDate(), "" + holder.getStudentId()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel" + e.getMessage());
        }
        return false;
    }


    public ArrayList<TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel> getData(int studentId, String refDate) {
        ArrayList<TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel> list = new ArrayList<>();

        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                    + COL_STUDENTID + " = '" + studentId + "' and "
                    + COL_REFERENCEDATE + " = '" + refDate + "'";
            AppLog.log(TAG,"getData selectQuery: "+selectQuery);
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel holder = new InnerTimeTableDetailDataModel();
                    holder.setReferenceDate(cursor.getString(cursor.getColumnIndex(COL_REFERENCEDATE)));
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setFaculty(cursor.getString(cursor.getColumnIndex(COL_FACULTY)));
                    holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setRoomName(cursor.getString(cursor.getColumnIndex(COL_ROOMNAME)));
                    holder.setSubjectName(cursor.getString(cursor.getColumnIndex(COL_SUBJECTNAME)));
                    holder.setTTime(cursor.getString(cursor.getColumnIndex(COL_TTIME)));
                    holder.setSqOrder(cursor.getString(cursor.getColumnIndex(COL_SQORDER)));
                    list.add(holder);
                    AppLog.log(TAG,"SubjectName: "+holder.getSubjectName());
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog("getData", e.getMessage());
        }
        return list;
    }
}
