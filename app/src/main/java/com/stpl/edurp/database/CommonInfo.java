package com.stpl.edurp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableStudentDetailsDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 19-01-2017.
 */

public class CommonInfo {
    private SQLiteDatabase mDB;
    private Context mContext;

    public void openDB(Context pContext) {
        mContext = pContext;
        DatabaseHelper helper = DatabaseHelper.getInstance(pContext);
        mDB = helper.getWritableDatabase();
    }

    public void closeDB() {
        if (mDB != null) {
            mDB = null;
        }
    }

    public ArrayList<TableStudentDetailsDataModel> getAllChildWRTParent(int parentId) {
        ArrayList<TableStudentDetailsDataModel> list = new ArrayList<TableStudentDetailsDataModel>();
        try {
            if (mDB != null) {
                String query = "select * from " + TableStudentDetails.TABLE_NAME + " join " + TableParentStudentAssociation.TABLE_NAME+" , "+TableUniversityMaster.TABLE_NAME
                        + " on " + TableStudentDetails.TABLE_NAME + "." + TableStudentDetails.COL_STUDENT_ID
                        + "=" + TableParentStudentAssociation.TABLE_NAME + "." + TableParentStudentAssociation.COL_STUDENTID+" and "
                        + TableStudentDetails.TABLE_NAME + "." + TableStudentDetails.COL_UNIVERSITY_ID
                        + "=" + TableUniversityMaster.TABLE_NAME + "." + TableUniversityMaster.COL_UNIVERSITY_ID
                        + " where " + TableParentStudentAssociation.TABLE_NAME + "." + TableParentStudentAssociation.COL_PARENTID + "='" + parentId + "'";
                AppLog.log("query getCourseCode: ",query);

                Cursor cursor = mDB.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableStudentDetailsDataModel model = new TableStudentDetailsDataModel();
                        model.setGender(cursor.getString(cursor.getColumnIndex(TableStudentDetails.COL_GENDER)));
                        model.setStudentId(cursor.getInt(cursor.getColumnIndex(TableStudentDetails.COL_STUDENT_ID)));
                        model.setCourseCode(cursor.getString(cursor.getColumnIndex(TableStudentDetails.COL_COURSE)));
                        model.setImageurl(cursor.getString(cursor.getColumnIndex(TableStudentDetails.COL_IMAGEURL)));
                        model.setFullName(cursor.getString(cursor.getColumnIndex(TableStudentDetails.COL_STUDENT_NAME)));
                        model.setUniversity_id(cursor.getInt(cursor.getColumnIndex(TableStudentDetails.COL_UNIVERSITY_ID)));
                        model.setUnversity_name(cursor.getString(cursor.getColumnIndex(TableUniversityMaster.COL_UNIVERSITY_NAME)));
                        list.add(model);
                        AppLog.log("getAllChildWRTParent getFullName: ", model.getFullName());
                        AppLog.log("getAllChildWRTParent getCourseCode: ", model.getCourseCode());

                    } while (cursor.moveToNext());

                } else {
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        } catch (Exception e) {
            AppLog.errLog("getAllChildWRTParent", e.getMessage());
        } finally {
            return list;
        }
    }
}
