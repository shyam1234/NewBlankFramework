package com.stpl.edurp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stpl.edurp.R;
import com.stpl.edurp.constant.Constant;

/**
 * Created by Admin on 26-11-2016.
 * a. Create table in database package
 * b. Create tableDataModel in model package
 * c. Parse table in ParseResponse *
 * We can access same parse model by ModelFactory
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //    private static final String DB_NAME = "db_edurp";

    private static DatabaseHelper mInstance;

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }


    private DatabaseHelper(Context context) {
        super(context, context.getString(R.string.app_name)+"_db", null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL(TableLanguage.CREATE_LANGUAGE_TABLE);
        db.execSQL(TableParentStudentMenuDetails.CREATE_TABLE);
        //db.execSQL(TableMenuMaster.CREATE_TABLE);
        db.execSQL(TableParentMaster.CREATE_TABLE);
        db.execSQL(TableParentStudentAssociation.CREATE_TABLE);
        db.execSQL(TableStudentDetails.CREATE_TABLE);
        db.execSQL(TableUniversityMaster.CREATE_TABLE);
        db.execSQL(TableDocumentMaster.CREATE_TABLE);
        db.execSQL(TableNewsMaster.CREATE_TABLE);
        db.execSQL(TableNoticeBoard.CREATE_TABLE);
        db.execSQL(TableUserMaster.CREATE_TABLE);
        db.execSQL(TableStudentOverallFeeSummary.CREATE_TABLE);
        db.execSQL(TableStudentOverallResultSummary.CREATE_TABLE);
        db.execSQL(TableResultDetails.CREATE_TABLE);
        db.execSQL(TableTimeTableDetails.CREATE_TABLE);
        db.execSQL(TableCourseMaster.CREATE_TABLE);
        db.execSQL(TableAttendanceDetails.CREATE_TABLE);
        db.execSQL(TableAbsenseDetails.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        //modify table if version changes
        if (old_version < new_version) {
            db.execSQL(TableCourseMaster.DROP_TABLE);
            db.execSQL(TableLanguage.DROP_TABLE_DIARY);
            db.execSQL(TableParentStudentMenuDetails.DROP_TABLE);
            db.execSQL(TableParentMaster.DROP_TABLE);
            db.execSQL(TableParentStudentAssociation.DROP_TABLE);
            db.execSQL(TableStudentDetails.DROP_TABLE);
            db.execSQL(TableUniversityMaster.DROP_TABLE);
            db.execSQL(TableDocumentMaster.DROP_TABLE);
            db.execSQL(TableNewsMaster.DROP_TABLE);
            db.execSQL(TableNoticeBoard.DROP_TABLE);
            db.execSQL(TableUserMaster.DROP_TABLE);
            db.execSQL(TableStudentOverallResultSummary.DROP_TABLE);
            db.execSQL(TableStudentOverallFeeSummary.DROP_TABLE);
            db.execSQL(TableResultDetails.DROP_TABLE);
            db.execSQL(TableTimeTableDetails.DROP_TABLE);
            db.execSQL(TableAttendanceDetails.DROP_TABLE);
            db.execSQL(TableAbsenseDetails.DROP_TABLE);
            onCreate(db);
        }
    }
}
