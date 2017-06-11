package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableNewsMasterDataModel;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableNewsMaster {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableNewsMaster";
    private static final String TABLE_NAME = "table_newsmaster";
    private static final String COL_PARENTID = "ParentId";
    private static final String COL_STUDENTID = "StudentId";
    private static final String COL_NEWSTITLE = "NewsTitle";
    private static final String COL_SHORTBODY = "ShortBody";
    private static final String COL_NEWSBODY = "NewsBody";
    private static final String COL_THUMBNAILPATH = "ThumbNailPath";
    private static final String COL_PUBLISHEDON = "PublishedOn";
    private static final String COL_PUBLISHEDBY = "PublishedBy";
    private static final String COL_TOTALCOMMENTS = "TotalComments";
    private static final String COL_TOTALLIKES = "TotalLikes";
    //added new field
    private static final String COL_MENUCODE = "MenuCode";
    private static final String COL_REFERENCEID = "ReferenceId";
    private static final String COL_DOCUMENTMASTERID = "DocumentMasterId";
    private static final String COL_DOCUMENTID = "DocumentId";
    private static final String COL_REFERENCETITLE = "ReferenceTitle";
    private static final String COL_EXPIRYDATE = "ExpiryDate";
    private static final String COL_FILEPATH = "FilePath";
    private static final String COL_FILETYPE = "FileType";
    private static final String COL_LIKEDBYME = "LikedByMe";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_PARENTID + " int , "
            + COL_STUDENTID + " int , "
            + COL_NEWSTITLE + " varchar(100), "
            + COL_SHORTBODY + "  varchar(100) , "
            + COL_NEWSBODY + "  varchar(1000) , "
            + COL_THUMBNAILPATH + " varchar(255), "
            + COL_PUBLISHEDON + " datetime , "
            + COL_PUBLISHEDBY + " char(30) , "
            + COL_TOTALCOMMENTS + " int , "
            + COL_TOTALLIKES + " int , "
            + COL_LIKEDBYME + " int , "
            + COL_MENUCODE + " int , "
            + COL_REFERENCEID + " int , "
            + COL_DOCUMENTMASTERID + " int ,"
            + COL_DOCUMENTID + " int , "
            + COL_REFERENCETITLE + " char(30) , "
            + COL_FILEPATH + " varchar(255), "
            + COL_FILETYPE + " varchar(255), "
            + COL_EXPIRYDATE + " varchar(100) "
            + " )";

    //For Foreign key
    //  + " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";


    public synchronized  void openDB(Context pContext) {
        DatabaseHelper helper = DatabaseHelper.getInstance(pContext);
        mDB = helper.getWritableDatabase();
    }

    public  synchronized void closeDB() {
        if (mDB != null) {
            mDB = null;
        }
    }

    //--------------------------------------------------------------------------------------------------------------------

    public synchronized  void dropTable() {
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

    public  synchronized void insert(ArrayList<TableNewsMasterDataModel> list) {
        try {
            if (mDB != null) {
                for (TableNewsMasterDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_PARENTID, holder.getParentId());
                    value.put(COL_STUDENTID, holder.getStudentId());
                    value.put(COL_NEWSTITLE, holder.getNewsTitle());
                    value.put(COL_SHORTBODY, holder.getShortBody());
                    value.put(COL_NEWSBODY, holder.getNewsBody());
                    value.put(COL_THUMBNAILPATH, holder.getThumbNailPath());
                    value.put(COL_PUBLISHEDON, holder.getPublishedOn());
                    value.put(COL_PUBLISHEDBY, holder.getPublishedBy());
                    value.put(COL_TOTALCOMMENTS, holder.getTotalComments());
                    value.put(COL_TOTALLIKES, holder.getTotalLikes());
                    value.put(COL_MENUCODE, holder.getMenuCode());
                    value.put(COL_FILEPATH, holder.getFilePath());
                    value.put(COL_LIKEDBYME, holder.getLikedByMe());
                    value.put(COL_REFERENCEID, holder.getReferenceId());
                    value.put(COL_DOCUMENTMASTERID, holder.getDocumentMasterId());
                    value.put(COL_DOCUMENTID, holder.getDocumentId());
                    value.put(COL_REFERENCETITLE, holder.getReferenceTitle());
                    value.put(COL_EXPIRYDATE, holder.getExpiryDate());
                    value.put(COL_FILETYPE, holder.getFileType());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TAG, TABLE_NAME + " inserted: " + holder.getReferenceId() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public  synchronized boolean isExists(TableNewsMasterDataModel model) {
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

    public  synchronized boolean deleteRecord(TableNewsMasterDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, /*COL_PUBLISHEDON + "=? and " +*/ COL_MENUCODE + "=? and " + COL_PARENTID + "=? and " + COL_STUDENTID + "=? and " + COL_REFERENCEID + "=?", new String[]{/*"" + holder.getPublishedOn(),*/ "" + holder.getMenuCode(), "" + holder.getParentId(), "" + holder.getStudentId(), "" + holder.getReferenceId()});
                AppLog.log(TAG, "deleteRecord row: " + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableNewsMasterDataModel" + e.getMessage());
        }
        return false;
    }


    public  synchronized boolean insertMessageBody(String pRefId, String pMessageBody) {
        try {
            if (mDB != null) {
                ContentValues value = new ContentValues();
                value.put(COL_NEWSBODY, pMessageBody);
                long row = mDB.update(TABLE_NAME, value, COL_REFERENCEID + "=? and "+COL_STUDENTID+ "=?", new String[]{""+pRefId,""+ UserInfo.studentId});
                AppLog.log(TAG, "insertMessageBody row " + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "insertMessageBody from TableNewsMaster" + e.getMessage());
        }
        return false;

    }


    public  synchronized ArrayList<TableNewsMasterDataModel> getDataByStudent(int pStudent) {
        ArrayList<TableNewsMasterDataModel> mNewsList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_STUDENTID + " = '" + pStudent
                    + "' and " + COL_EXPIRYDATE + " >= '" + Utils.getCurrTimeYYYYMMDDOOOOOO() + "'";
            AppLog.log(TAG,"getDataByStudent selectQuery: "+selectQuery);
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    TableNewsMasterDataModel holder = new TableNewsMasterDataModel();
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getString(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getString(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setNewsTitle(cursor.getString(cursor.getColumnIndex(COL_NEWSTITLE)));
                    holder.setShortBody(cursor.getString(cursor.getColumnIndex(COL_SHORTBODY)));
                    holder.setNewsBody(cursor.getString(cursor.getColumnIndex(COL_NEWSBODY)));
                    holder.setThumbNailPath(cursor.getString(cursor.getColumnIndex(COL_THUMBNAILPATH)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setTotalComments(cursor.getString(cursor.getColumnIndex(COL_TOTALCOMMENTS)));
                    holder.setTotalLikes(cursor.getString(cursor.getColumnIndex(COL_TOTALLIKES)));
                    holder.setFilePath(cursor.getString(cursor.getColumnIndex(COL_FILEPATH)));
                    holder.setDocumentId(cursor.getInt(cursor.getColumnIndex(COL_DOCUMENTID)));
                    holder.setDocumentMasterId(cursor.getInt(cursor.getColumnIndex(COL_DOCUMENTMASTERID)));
                    holder.setReferenceTitle(cursor.getString(cursor.getColumnIndex(COL_REFERENCETITLE)));
                    holder.setFileType(cursor.getString(cursor.getColumnIndex(COL_FILETYPE)));
                    holder.setLikedByMe(cursor.getInt(cursor.getColumnIndex(COL_LIKEDBYME)));
                    mNewsList.add(holder);
                    AppLog.log("getData", "COL_REFERENCEID ++ " + holder.getReferenceId());
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog("getData", e.getMessage());
        }
        return mNewsList;
    }

    public  synchronized TableNewsMasterDataModel getNews(int pStudent, int pReferenceId) {
        TableNewsMasterDataModel holder = new TableNewsMasterDataModel();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                    + COL_STUDENTID + " = '" + pStudent
                    + "' and " + COL_REFERENCEID + " = '" + pReferenceId
                    + "' and " + COL_EXPIRYDATE + " >= '" + Utils.getCurrTimeYYYYMMDDOOOOOO() + "'";
            AppLog.log(TAG,"getNews selectQuery: "+selectQuery);
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getString(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getString(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setNewsTitle(cursor.getString(cursor.getColumnIndex(COL_NEWSTITLE)));
                    holder.setShortBody(cursor.getString(cursor.getColumnIndex(COL_SHORTBODY)));
                    holder.setNewsBody(cursor.getString(cursor.getColumnIndex(COL_NEWSBODY)));
                    holder.setThumbNailPath(cursor.getString(cursor.getColumnIndex(COL_THUMBNAILPATH)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setTotalComments(cursor.getString(cursor.getColumnIndex(COL_TOTALCOMMENTS)));
                    holder.setTotalLikes(cursor.getString(cursor.getColumnIndex(COL_TOTALLIKES)));
                    holder.setFilePath(cursor.getString(cursor.getColumnIndex(COL_FILEPATH)));
                    holder.setDocumentId(cursor.getInt(cursor.getColumnIndex(COL_DOCUMENTID)));
                    holder.setDocumentMasterId(cursor.getInt(cursor.getColumnIndex(COL_DOCUMENTMASTERID)));
                    holder.setReferenceTitle(cursor.getString(cursor.getColumnIndex(COL_REFERENCETITLE)));
                    holder.setFileType(cursor.getString(cursor.getColumnIndex(COL_FILETYPE)));
                    holder.setLikedByMe(cursor.getInt(cursor.getColumnIndex(COL_LIKEDBYME)));
                    AppLog.log("getNews", "COL_REFERENCEID ++ " + holder.getReferenceId());
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog("getNews", e.getMessage());
        }
        return holder;
    }

    public  synchronized void updateLikedByMe(int pLikeValue, int studentId, int mReferenceId) {
        try {
            if (mDB != null) {
                ContentValues value = new ContentValues();
                value.put(COL_LIKEDBYME, pLikeValue);
                long row = mDB.update(TABLE_NAME, value, COL_REFERENCEID + "=? and "+COL_STUDENTID+ "=?", new String[]{""+mReferenceId,""+studentId});
                AppLog.log(TAG, "insertMessageBody row " + row);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "insertMessageBody from TableNewsMaster" + e.getMessage());
        }
    }
}


