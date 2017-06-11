package com.stpl.edurp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.stpl.edurp.application.MyApplication;
import com.stpl.edurp.models.TableDocumentMasterDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableDocumentMaster {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableDocumentMaster";
    private static final String TABLE_NAME = "table_document_master";
    private static final String COL_REFERENCEID = "referenceid";
    private static final String COL_MENUCODE = "menucode";
    private static final String COL_DOCUMENT_NAME = "documentname";
    private static final String COL_DOCUMENT_PATH = "documentpath";
    private static final String COL_DOOCUMENT_EXTN = "documentextn";
    private static final String COL_IS_ATTACHMENT = "isattachment";
    private static final String COL_MEDIATYPE = "mediatype";
    private static final String COL_SORTORDER = "sortorder";
    private static final String COL_DOCUMENT_ID = "documentid";
    private static final String COL_DOCUMENTMASTERID= "documentmasterid";
    private static final String COL_FILEURL= "fileurl";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;

    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_REFERENCEID + " int, "
            + COL_MENUCODE + " char(3), "
            + COL_DOCUMENT_NAME + " varchar(100), "
            + COL_DOCUMENT_PATH + " varchar(100), "
            + COL_DOOCUMENT_EXTN + " varchar(10) , "
            + COL_IS_ATTACHMENT + " bit , "
            + COL_MEDIATYPE + " char(1) , "
            + COL_SORTORDER + " int , "
            + COL_DOCUMENT_ID + " varchar(100) , "
            + COL_DOCUMENTMASTERID + " int , "
            + COL_FILEURL + " varchar(255) "
            + " )";

    //For Foreign key
    //  + " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";


    public  synchronized void openDB(Context pContext) {
        DatabaseHelper helper = DatabaseHelper.getInstance(pContext);
        mDB = helper.getWritableDatabase();
    }

    public  synchronized void closeDB() {
        if (mDB != null) {
            mDB = null;
        }
    }

    //--------------------------------------------------------------------------------------------------------------------

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
                mDB.execSQL(TRUNCATE_TABLE);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }

    //---------------------------------------------------------------------------------------

    public  synchronized void insert(ArrayList<TableDocumentMasterDataModel> list) {
        try {
            if (mDB != null) {
                for (TableDocumentMasterDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_REFERENCEID, holder.getReferenceid());
                    value.put(COL_DOCUMENT_NAME, holder.getDocumentname());
                    value.put(COL_DOOCUMENT_EXTN, holder.getDocumentextn());
                    value.put(COL_DOCUMENT_PATH, holder.getDocumentpath());
                    value.put(COL_IS_ATTACHMENT, holder.getIsattachment());
                    value.put(COL_MEDIATYPE, holder.getMediatype());
                    value.put(COL_MENUCODE, holder.getMenucode());
                    value.put(COL_SORTORDER, holder.getSortorder());
                    value.put(COL_DOCUMENT_ID, holder.getDocumentId());
                    value.put(COL_DOCUMENTMASTERID, holder.getDocumentMasterId());
                    value.put(COL_FILEURL, holder.getFileURL());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TAG, TABLE_NAME + " inserted: "+ holder.getDocumentname() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public  synchronized boolean isExists(TableDocumentMasterDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_DOCUMENT_ID + " = '" + model.getDocumentId() + "'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    AppLog.log(TAG, "isExists "+ true);
                    return true;
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog(TAG, "isExists "+ e.getMessage());
        }
        return false;
    }

    public  synchronized boolean deleteRecord(TableDocumentMasterDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_DOCUMENT_ID + "=?", new String[]{"" + holder.getDocumentId()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableDocumentMasterDataModel" + e.getMessage());
        }
        return false;
    }


    public synchronized  ArrayList<TableDocumentMasterDataModel>   getDocument(int pDocMasterID/*, int referenceId*/) {
            ArrayList<TableDocumentMasterDataModel> list = new ArrayList<>();
            try {
                String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                        + COL_DOCUMENTMASTERID + " = '" + pDocMasterID /*+ "' and "
                        + COL_REFERENCEID + " = '" + referenceId */+ "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        TableDocumentMasterDataModel holder = new TableDocumentMasterDataModel();
                        holder.setReferenceid(cursor.getString(cursor.getColumnIndex(COL_REFERENCEID)));
                        holder.setDocumentname(cursor.getString(cursor.getColumnIndex(COL_DOCUMENT_NAME)));
                        holder.setDocumentextn(cursor.getString(cursor.getColumnIndex(COL_DOOCUMENT_EXTN)));
                        holder.setDocumentpath(cursor.getString(cursor.getColumnIndex(COL_DOCUMENT_PATH)));
                        holder.setIsattachment(cursor.getString(cursor.getColumnIndex(COL_IS_ATTACHMENT)));
                        holder.setMediatype(cursor.getString(cursor.getColumnIndex(COL_MEDIATYPE)));
                        holder.setMenucode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                        holder.setSortorder(cursor.getString(cursor.getColumnIndex(COL_SORTORDER)));
                        holder.setDocumentId(cursor.getString(cursor.getColumnIndex(COL_DOCUMENT_ID)));
                        holder.setFileURL(cursor.getString(cursor.getColumnIndex(COL_FILEURL)));
                        holder.setDocumentMasterId(cursor.getInt(cursor.getColumnIndex(COL_DOCUMENTMASTERID)));
                        list.add(holder);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } catch (Exception e) {
                AppLog.errLog(TAG, "getData from TableDocumentMasterDataModel " + e.getMessage());
            } finally {

                return list;
            }
        }
}
