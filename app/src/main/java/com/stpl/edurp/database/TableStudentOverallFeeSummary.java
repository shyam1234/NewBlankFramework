package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableFeeMasterDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableStudentOverallFeeSummary {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableStudentOverallFeeSummary";
    private static final String TABLE_NAME = "table_feemaster";
    private static final String COL_MENUCODE = "menucode";
    private static final String COL_PARENTID = "parentid";
    private static final String COL_STUDENTID = "studentId";
    private static final String COL_REFERENCEID = "referenceid";
    private static final String COL_STUDENTNUMBER = "studentnumber";
    private static final String COL_STUDENTNAME = "studentname";
    private static final String COL_COURSENAME = "coursename";
    private static final String COL_SEMESTERNAME = "semestername";
    private static final String COL_TOTALDUE = "totaldue";
    private static final String COL_DUEDATE = "duedate";
    private static final String COL_PUBLISHEDON = "publishedon";
    private static final String COL_PUBLISHEDBY = "publishedby";
    private static final String COL_EXPIRYDATE = "expirydate";
    private static final String COL_FEETITLE = "feetitle";
    private static final String COL_STATUS = "status";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_MENUCODE + " char(10) , "
            + COL_PARENTID + " int , "
            + COL_STUDENTID + " int , "
            + COL_REFERENCEID + " int , "
            + COL_STUDENTNAME + " varchar(255) , "
            + COL_STUDENTNUMBER + " varchar(255) , "
            + COL_DUEDATE + " varchar(255) , "
            + COL_COURSENAME + " varchar(255) , "
            + COL_SEMESTERNAME + " varchar(255) , "
            + COL_TOTALDUE + " varchar(255) , "
            + COL_PUBLISHEDON + " varchar(255) , "
            + COL_PUBLISHEDBY + " varchar(255) , "
            + COL_FEETITLE + " varchar(255) , "
            + COL_EXPIRYDATE + " varchar(255) , "
            + COL_STATUS + " varchar(100) "
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

    public void insert(ArrayList<TableFeeMasterDataModel> list) {
        try {
            if (mDB != null) {
                for (TableFeeMasterDataModel holder : list) {
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
                    value.put(COL_DUEDATE, holder.getDueDate());
                    value.put(COL_COURSENAME, holder.getCourseName());
                    value.put(COL_SEMESTERNAME, holder.getSemsterName());
                    value.put(COL_TOTALDUE, holder.getTotalDue());
                    value.put(COL_PUBLISHEDON, holder.getPublishedOn());
                    value.put(COL_PUBLISHEDBY, holder.getPublishedBy());
                    value.put(COL_FEETITLE, holder.getFeeTitle());
                    value.put(COL_EXPIRYDATE, holder.getExpiryDate());
                    value.put(COL_STATUS, holder.getStatus());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: value ", "" +value.toString());
                    AppLog.log(TABLE_NAME + " inserted: getSubjectId ", holder.getStudentId() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog(TABLE_NAME, e.getMessage());
        }
    }


    public boolean isExists(TableFeeMasterDataModel model) {
        try {
            //String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_PARENTID + " = '" + model.getParentId() + "' and " + COL_STUDENTID + " = '" + model.getSubjectId() + "'";
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

    public boolean deleteRecord(TableFeeMasterDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_PUBLISHEDON + "=? and " + COL_MENUCODE + "=? and " + COL_PARENTID + "=? and " + COL_STUDENTID + "=? and " + COL_REFERENCEID + "=?", new String[]{"" + holder.getPublishedOn(), "" + holder.getMenuCode(), "" + holder.getParentId(), "" + holder.getStudentId(), "" + holder.getReferenceId()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableFeeMasterDataModel" + e.getMessage());
        }
        return false;
    }


    public TableFeeMasterDataModel getData(String menuCode, String rederenceId) {
        TableFeeMasterDataModel holder = new TableFeeMasterDataModel();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_MENUCODE + " = '" + menuCode + "' and " + COL_REFERENCEID + " = '" + rederenceId + "'";
            AppLog.log(TABLE_NAME,"selectQuery: "+selectQuery);
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    holder.setCourseName(cursor.getString(cursor.getColumnIndex(COL_COURSENAME)));
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME)));
                    holder.setSemsterName(cursor.getString(cursor.getColumnIndex(COL_SEMESTERNAME)));
                    holder.setTotalDue(cursor.getString(cursor.getColumnIndex(COL_TOTALDUE)));
                    holder.setDueDate(cursor.getString(cursor.getColumnIndex(COL_DUEDATE)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setFeeTitle(cursor.getString(cursor.getColumnIndex(COL_FEETITLE)));
                    holder.setStatus(cursor.getString(cursor.getColumnIndex(COL_STATUS)));
                    AppLog.log(TABLE_NAME,"setTotalDue: "+holder.getTotalDue());
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog("getData", e.getMessage());
        }
        return holder;
    }


    public ArrayList<TableFeeMasterDataModel> getData(String menuCode) {
        ArrayList<TableFeeMasterDataModel> list = new ArrayList<>();

        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_MENUCODE + " = '" + menuCode + "'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    TableFeeMasterDataModel holder = new TableFeeMasterDataModel();
                    holder.setCourseName(cursor.getString(cursor.getColumnIndex(COL_COURSENAME)));
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME)));
                    holder.setSemsterName(cursor.getString(cursor.getColumnIndex(COL_SEMESTERNAME)));
                    holder.setTotalDue(cursor.getString(cursor.getColumnIndex(COL_TOTALDUE)));
                    holder.setDueDate(cursor.getString(cursor.getColumnIndex(COL_DUEDATE)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setFeeTitle(cursor.getString(cursor.getColumnIndex(COL_FEETITLE)));
                    holder.setStatus(cursor.getString(cursor.getColumnIndex(COL_STATUS)));
                    list.add(holder);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog("getData", e.getMessage());
        }
        return list;
    }


    public ArrayList<TableFeeMasterDataModel> getData(int parentId, int studentId) {
        ArrayList<TableFeeMasterDataModel> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                    + COL_PARENTID + " = '" + parentId + "' and "
                    + COL_STUDENTID + " = '" + studentId + "'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    TableFeeMasterDataModel holder = new TableFeeMasterDataModel();
                    holder.setCourseName(cursor.getString(cursor.getColumnIndex(COL_COURSENAME)));
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME)));
                    holder.setSemsterName(cursor.getString(cursor.getColumnIndex(COL_SEMESTERNAME)));
                    holder.setTotalDue(cursor.getString(cursor.getColumnIndex(COL_TOTALDUE)));
                    holder.setDueDate(cursor.getString(cursor.getColumnIndex(COL_DUEDATE)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setFeeTitle(cursor.getString(cursor.getColumnIndex(COL_FEETITLE)));
                    holder.setStatus(cursor.getString(cursor.getColumnIndex(COL_STATUS)));
                    list.add(holder);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog(TAG, "getData from TableFeeMasterDataModel " + e.getMessage());
        } finally {

            return list;
        }
    }

    public ArrayList<TableFeeMasterDataModel> getData(String timeTableRefDate, int studentId) {
            ArrayList<TableFeeMasterDataModel> list = new ArrayList<>();
            try {
                String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                        + COL_REFERENCEID + " = '" + timeTableRefDate + "' and "
                        + COL_STUDENTID + " = '" + studentId + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        TableFeeMasterDataModel holder = new TableFeeMasterDataModel();
                        holder.setCourseName(cursor.getString(cursor.getColumnIndex(COL_COURSENAME)));
                        holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                        holder.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                        holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                        holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                        holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                        holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME)));
                        holder.setSemsterName(cursor.getString(cursor.getColumnIndex(COL_SEMESTERNAME)));
                        holder.setTotalDue(cursor.getString(cursor.getColumnIndex(COL_TOTALDUE)));
                        holder.setDueDate(cursor.getString(cursor.getColumnIndex(COL_DUEDATE)));
                        holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                        holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                        holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                        holder.setFeeTitle(cursor.getString(cursor.getColumnIndex(COL_FEETITLE)));
                        holder.setStatus(cursor.getString(cursor.getColumnIndex(COL_STATUS)));
                        list.add(holder);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } catch (Exception e) {
                AppLog.errLog(TAG, "getData from TableFeeMasterDataModel " + e.getMessage());
            } finally {

                return list;
            }
        }

    public ArrayList<TableFeeMasterDataModel> getData(int studentId) {
        ArrayList<TableFeeMasterDataModel> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                    + COL_STUDENTID + " = '" + studentId + "'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    TableFeeMasterDataModel holder = new TableFeeMasterDataModel();
                    holder.setCourseName(cursor.getString(cursor.getColumnIndex(COL_COURSENAME)));
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME)));
                    holder.setSemsterName(cursor.getString(cursor.getColumnIndex(COL_SEMESTERNAME)));
                    holder.setTotalDue(cursor.getString(cursor.getColumnIndex(COL_TOTALDUE)));
                    holder.setDueDate(cursor.getString(cursor.getColumnIndex(COL_DUEDATE)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setFeeTitle(cursor.getString(cursor.getColumnIndex(COL_FEETITLE)));
                    holder.setStatus(cursor.getString(cursor.getColumnIndex(COL_STATUS)));
                    list.add(holder);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog(TAG, "getData from TableFeeMasterDataModel " + e.getMessage());
        } finally {

            return list;
        }
    }

}