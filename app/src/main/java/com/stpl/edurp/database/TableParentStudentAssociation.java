package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableParentStudentAssociationDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableParentStudentAssociation {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableParentStudentAssociation";
    public static final String TABLE_NAME = "table_parent_student_rel";
    public static final String COL_IS_DEFAULT = "is_default";
    public static final String COL_PARENTID = "parent_id";
    public static final String COL_STUDENTID = "studentid";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_IS_DEFAULT + " varchar(255), "
            + COL_PARENTID + " integer, "
            + COL_STUDENTID + " integer "
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


    //-------------------------------------------------------------------------------
    public void insert(ArrayList<TableParentStudentAssociationDataModel> list) {
        try {
            if (mDB != null) {
                for (TableParentStudentAssociationDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_IS_DEFAULT, holder.getIs_default());
                    value.put(COL_PARENTID, holder.getParent_id());
                    value.put(COL_STUDENTID, holder.getStudentid());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: ", holder.getStudentid() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public boolean isExists(TableParentStudentAssociationDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_PARENTID + " = '" + model.getParent_id()
                    +"' AND "+ COL_STUDENTID + " = '" + model.getStudentid()+"'";
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

    public boolean deleteRecord(TableParentStudentAssociationDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_PARENTID + "=? and "+COL_STUDENTID + "=?", new String[]{""+holder.getParent_id(),""+holder.getStudentid()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "delete Record from TableParentStudentAssociationDataModel" + e.getMessage());
        }
        return false;
    }


    public TableParentStudentAssociationDataModel getStudentIDWRTParentID(int parentId) {
        TableParentStudentAssociationDataModel model = new TableParentStudentAssociationDataModel();
        try {
            AppLog.log("getStudentIDWRTParentID++++++", "");
            if (mDB != null) {
                String selectQuery = "Select * from " + TABLE_NAME  + " WHERE " + COL_PARENTID + "='" + parentId+ "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                AppLog.log(TABLE_NAME + " selectQuery: ", selectQuery);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        model.setIsDefault(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(COL_IS_DEFAULT))));
                        model.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                        model.setStudentid(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    } while (cursor.moveToNext());
                }

                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog("getStudentIDWRTParentID", e.getMessage());
        }
        return model;
    }

    public TableParentStudentAssociationDataModel getParentIDWRTStudentId(int studentid) {
        TableParentStudentAssociationDataModel model = new TableParentStudentAssociationDataModel();
        try {
            AppLog.log("getParentIDWRTStudentId++++++", "");
            if (mDB != null) {
                String selectQuery = "Select * from " + TABLE_NAME  + " WHERE " + COL_STUDENTID + "='" + studentid+ "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        model.setIsDefault(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(COL_IS_DEFAULT))));
                        model.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                        model.setStudentid(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog("getParentIDWRTStudentId", e.getMessage());
        }
        return model;
    }


}
