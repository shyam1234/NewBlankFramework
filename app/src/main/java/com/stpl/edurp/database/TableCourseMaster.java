package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableCourseMasterDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableCourseMaster {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableCourseMaster";
    public static final String TABLE_NAME = "table_coursemaster";
    public static final String STUDENTID = "studentid";
    public static final String COURSE = "course";
    public static final String SEMESTER = "semester";
    public static final String LASTRECEIVED = "lastreceived";
    public static final String REFERENCEID = "referenceids";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + STUDENTID + " int, "
            + REFERENCEID + " int, "
            + COURSE + " varchar(100), "
            + SEMESTER + " varchar(100), "
            + LASTRECEIVED + " varchar(255) "
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

    public boolean insert(ArrayList<TableCourseMasterDataModel> list) {
        try {
            if (mDB != null) {
                for (TableCourseMasterDataModel holder : list) {
                    deleteDataIfExist(holder.getStudentId(), holder.getSemester());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(STUDENTID, holder.getStudentId());
                    contentValues.put(COURSE, holder.getCourse());
                    contentValues.put(SEMESTER, holder.getSemester());
                    contentValues.put(LASTRECEIVED, holder.getLastRetrieved());
                    contentValues.put(REFERENCEID, holder.getReferenceId());
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

    private void deleteDataIfExist(int pStudentId, String pSemester) {
        try {
            String selectQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + STUDENTID + "= '" + pStudentId + "' AND " + SEMESTER + "= '" + pSemester+"'";
            AppLog.log(TAG, "deleteDataIfExist +++selectQuery():" + selectQuery.toString());
            mDB.execSQL(selectQuery);
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from deleteDataIfExist " + e.getMessage());
        }
    }

    public ArrayList<TableCourseMasterDataModel> read() {
        try {
            ArrayList<TableCourseMasterDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME;
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableCourseMasterDataModel model = new TableCourseMasterDataModel();
                        model.setStudentId(cursor.getInt(cursor.getColumnIndex(STUDENTID)));
                        model.setSemester((cursor.getString(cursor.getColumnIndex(SEMESTER))));
                        model.setCourse((cursor.getString(cursor.getColumnIndex(COURSE))));
                        model.setReferenceId((cursor.getInt(cursor.getColumnIndex(REFERENCEID))));
                        model.setLastRetrieved((cursor.getString(cursor.getColumnIndex(LASTRECEIVED))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    public ArrayList<TableCourseMasterDataModel> read(int pStudentId) {
        try {
            ArrayList<TableCourseMasterDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + STUDENTID + "='" + pStudentId + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableCourseMasterDataModel model = new TableCourseMasterDataModel();
                        model.setStudentId(cursor.getInt(cursor.getColumnIndex(STUDENTID)));
                        model.setSemester((cursor.getString(cursor.getColumnIndex(SEMESTER))));
                        model.setCourse((cursor.getString(cursor.getColumnIndex(COURSE))));
                        model.setReferenceId((cursor.getInt(cursor.getColumnIndex(REFERENCEID))));
                        model.setLastRetrieved((cursor.getString(cursor.getColumnIndex(LASTRECEIVED))));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    private void deleteDataIfExist(int pStudentId, String pSemester, int referenceId) {
        try {
            String selectQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + STUDENTID + "= '" + pStudentId + "' AND " + REFERENCEID + "= '" + referenceId + "' AND " + SEMESTER + "= '" + pSemester + "'";
            AppLog.log(TAG, "deleteDataIfExist +++selectQuery():" + selectQuery.toString());
            mDB.execSQL(selectQuery);
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from deleteDataIfExist " + e.getMessage());
        }
    }


    public ArrayList<TableCourseMasterDataModel> getValueBySem() {
        try {
            ArrayList<TableCourseMasterDataModel> holder = new ArrayList<TableCourseMasterDataModel>();

            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME;//+ " WHERE " + REFERENCE_ID + "= '" + pStudentId +"'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        TableCourseMasterDataModel model = new TableCourseMasterDataModel();
                        // get the data into array, or class variable
                        model.setStudentId(cursor.getInt(cursor.getColumnIndex(STUDENTID)));
                        model.setSemester((cursor.getString(cursor.getColumnIndex(SEMESTER))));
                        model.setCourse((cursor.getString(cursor.getColumnIndex(COURSE))));
                        model.setReferenceId((cursor.getInt(cursor.getColumnIndex(REFERENCEID))));
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



    public ArrayList<TableCourseMasterDataModel> getValueByStudent(int pStudentId) {
            try {
                ArrayList<TableCourseMasterDataModel> holder = new ArrayList<TableCourseMasterDataModel>();

                if (mDB != null) {
                    String selectQuery = "SELECT  * FROM " + TABLE_NAME+ " WHERE " + STUDENTID + "= '" + pStudentId +"'";
                    Cursor cursor = mDB.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()) {
                        do {
                            TableCourseMasterDataModel model = new TableCourseMasterDataModel();
                            // get the data into array, or class variable
                            model.setStudentId(cursor.getInt(cursor.getColumnIndex(STUDENTID)));
                            model.setSemester((cursor.getString(cursor.getColumnIndex(SEMESTER))));
                            model.setCourse((cursor.getString(cursor.getColumnIndex(COURSE))));
                            model.setReferenceId((cursor.getInt(cursor.getColumnIndex(REFERENCEID))));
                            holder.add(model);
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                } else {
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
                }
                return holder;
            } catch (Exception e) {
                AppLog.errLog(TAG, "Exception from getValueByStudent " + e.getMessage());
                return null;
            }
        }


    }
