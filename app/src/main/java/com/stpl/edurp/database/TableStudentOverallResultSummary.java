package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableResultMasterDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableStudentOverallResultSummary {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableStudentOverallResultSummary";
    private static final String TABLE_NAME = "table_studentoverallresultsummary";
    private static final String COL_MENUCODE = "menucode";
    private static final String COL_PARENTID = "parentid";
    private static final String COL_STUDENTID = "studentId";
    private static final String COL_REFERENCEID = "referenceid";
    private static final String COL_STUDENTNUMBER = "studentnumber";
    private static final String COL_STUDENTNAME = "studentname";
    private static final String COL_ACADEMICYEAR = "academicyear";
    private static final String COL_COURSENAME = "coursename";
    private static final String COL_SEMESTERNAME = "semestername";
    private static final String COL_ACHIEVEMENTINDEX = "achivementIndex";
    private static final String COL_RESULT = "result";
    private static final String COL_PUBLISHEDON = "publishedon";
    private static final String COL_PUBLISHEDBY = "publishedby";
    private static final String COL_EXPIRYDATE = "expirydate";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_MENUCODE + " char(10), "
            + COL_PARENTID + " int, "
            + COL_STUDENTID + " int, "
            + COL_REFERENCEID + " int, "
            + COL_STUDENTNAME + " varchar(255), "
            + COL_STUDENTNUMBER + " varchar(255), "
            + COL_ACADEMICYEAR + " varchar(255), "
            + COL_COURSENAME + " varchar(255), "
            + COL_SEMESTERNAME + " varchar(255), "
            + COL_ACHIEVEMENTINDEX + " varchar(255), "
            + COL_RESULT + " varchar(255), "
            + COL_PUBLISHEDON + " varchar(255), "
            + COL_PUBLISHEDBY + " varchar(255), "
            + COL_EXPIRYDATE + " varchar(255) "
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

    public void insert(ArrayList<TableResultMasterDataModel> list) {
        try {
            if (mDB != null) {
                for (TableResultMasterDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_MENUCODE, holder.getMenuCode());
                    value.put(COL_STUDENTID, holder.getStudentId());
                    value.put(COL_PARENTID, holder.getParentId());
                    value.put(COL_REFERENCEID, holder.getReferenceId());
                    value.put(COL_STUDENTNUMBER, holder.getStudentNumber());
                    value.put(COL_STUDENTNAME, holder.getStudentName());
                    value.put(COL_ACADEMICYEAR, holder.getAcademicYear());
                    value.put(COL_COURSENAME, holder.getCourseName());
                    value.put(COL_SEMESTERNAME, holder.getSemesterName());
                    value.put(COL_ACHIEVEMENTINDEX, holder.getAchievementIndex());
                    value.put(COL_RESULT, holder.getResult());
                    value.put(COL_PUBLISHEDON, holder.getPublishedOn());
                    value.put(COL_PUBLISHEDBY, holder.getPublishedBy());
                    value.put(COL_EXPIRYDATE, holder.getExpiryDate());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: getParentId ", "" + holder.getParentId());
                    AppLog.log(TABLE_NAME + " inserted: getSubjectId ", holder.getStudentId() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public boolean isExists(TableResultMasterDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                    + COL_MENUCODE + " = '" + model.getMenuCode() + "' and "
                    + COL_REFERENCEID + " = '" + model.getReferenceId() + "' and "
                    + COL_PARENTID + " = '" + model.getParentId() + "' and "
                    + COL_STUDENTID + " = '" + model.getStudentId() + "'";
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

    public boolean deleteRecord(TableResultMasterDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_PUBLISHEDON + "=? and " + COL_MENUCODE + "=? and " + COL_PARENTID + "=? and " + COL_STUDENTID + "=? and " + COL_REFERENCEID + "=?", new String[]{"" + holder.getPublishedOn(), "" + holder.getMenuCode(), "" + holder.getParentId(), "" + holder.getStudentId(), "" + holder.getReferenceId()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableResultMasterDataModel" + e.getMessage());
        }
        return false;
    }


    public TableResultMasterDataModel getData(String menuCode, String rederenceId) {
        TableResultMasterDataModel holder = new TableResultMasterDataModel();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_MENUCODE + " = '" + menuCode + "' and " + COL_REFERENCEID + " = '" + rederenceId + "'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {

                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setStudentNumber(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME)));
                    holder.setAcademicYear(cursor.getString(cursor.getColumnIndex(COL_ACADEMICYEAR)));
                    holder.setCourseName(cursor.getString(cursor.getColumnIndex(COL_COURSENAME)));
                    holder.setSemesterName(cursor.getString(cursor.getColumnIndex(COL_SEMESTERNAME)));
                    holder.setAchievementIndex(cursor.getString(cursor.getColumnIndex(COL_ACHIEVEMENTINDEX)));
                    holder.setResult(cursor.getString(cursor.getColumnIndex(COL_RESULT)));

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog("getData", e.getMessage());
        }
        return holder;
    }


    public ArrayList<TableResultMasterDataModel> getData(String menuCode) {
        ArrayList<TableResultMasterDataModel> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_MENUCODE + " = '" + menuCode + "'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    TableResultMasterDataModel holder = new TableResultMasterDataModel();
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setStudentNumber(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME)));
                    holder.setAcademicYear(cursor.getString(cursor.getColumnIndex(COL_ACADEMICYEAR)));
                    holder.setCourseName(cursor.getString(cursor.getColumnIndex(COL_COURSENAME)));
                    holder.setSemesterName(cursor.getString(cursor.getColumnIndex(COL_SEMESTERNAME)));
                    holder.setAchievementIndex(cursor.getString(cursor.getColumnIndex(COL_ACHIEVEMENTINDEX)));
                    holder.setResult(cursor.getString(cursor.getColumnIndex(COL_RESULT)));
                    list.add(holder);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog("getData", e.getMessage());
        }
        return list;
    }



    public ArrayList<TableResultMasterDataModel> getData(int parentId, int studentId) {
        ArrayList<TableResultMasterDataModel> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                    + COL_PARENTID + " = '" + parentId + "' and "
                    + COL_STUDENTID + " = '" + studentId + "'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    TableResultMasterDataModel holder = new TableResultMasterDataModel();
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setStudentNumber(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME)));
                    holder.setAcademicYear(cursor.getString(cursor.getColumnIndex(COL_ACADEMICYEAR)));
                    holder.setCourseName(cursor.getString(cursor.getColumnIndex(COL_COURSENAME)));
                    holder.setSemesterName(cursor.getString(cursor.getColumnIndex(COL_SEMESTERNAME)));
                    holder.setAchievementIndex(cursor.getString(cursor.getColumnIndex(COL_ACHIEVEMENTINDEX)));
                    holder.setResult(cursor.getString(cursor.getColumnIndex(COL_RESULT)));
                    AppLog.log(TAG,"getData "+holder.getResult());
                    list.add(holder);
                    AppLog.log(TAG,"list list  "+list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog(TAG, "getData from TableResultMasterDataModel " + e.getMessage());
        } finally {
            return list;
        }
    }


    public ArrayList<TableResultMasterDataModel> getData(String menuCode, int studentId) {
        ArrayList<TableResultMasterDataModel> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                    + COL_MENUCODE + " = '" + menuCode + "' and "
                    + COL_STUDENTID + " = '" + studentId + "'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    TableResultMasterDataModel holder = new TableResultMasterDataModel();
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setStudentNumber(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME)));
                    holder.setAcademicYear(cursor.getString(cursor.getColumnIndex(COL_ACADEMICYEAR)));
                    holder.setCourseName(cursor.getString(cursor.getColumnIndex(COL_COURSENAME)));
                    holder.setSemesterName(cursor.getString(cursor.getColumnIndex(COL_SEMESTERNAME)));
                    holder.setAchievementIndex(cursor.getString(cursor.getColumnIndex(COL_ACHIEVEMENTINDEX)));
                    holder.setResult(cursor.getString(cursor.getColumnIndex(COL_RESULT)));
                    AppLog.log(TAG,"getData "+holder.getResult());
                    list.add(holder);
                    AppLog.log(TAG,"list list  "+list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog(TAG, "getData " + e.getMessage());
        } finally {
            return list;
        }
    }
}
