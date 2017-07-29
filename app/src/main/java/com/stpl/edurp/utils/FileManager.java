package com.stpl.edurp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.stpl.edurp.interfaces.ICallBack;

import java.io.File;

/**
 * Created by Admin on 25-06-2017.
 */

public class FileManager {
//   private static File sd = Environment.getExternalStorageDirectory();
//    private static File data = sd;// Environment.getExternalFilesDir();


    /**
     * getExternalStorageDirectory creates folder in This "Internal storage\Edurp_results"
     *
     * @param pContext
     * @param pFolderName
     * @param pFileName
     */
    public static void showDownloadFile(Activity pContext, String pFolderName, String pFileName) {
        verifyStoragePermissions(pContext);
        File pdfFile = getPDFFile(pContext,pFolderName,pFileName);
        Log.d("getAbsoluteFile ","pdfFile pdfFile  ++ "+pdfFile.getAbsolutePath().toString());
        //File pdfFile = new File(data + "/" + pFolderName + "/" + pFileName);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            pContext.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(pContext, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    public static void deleteDownloadFile(Activity pContext, String pFolderName, String pFileName, ICallBack pCallBack) {
        verifyStoragePermissions(pContext);
        File pdfFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            pdfFile= new File(pContext.getExternalFilesDir(null), "/" + pFolderName + "/" + pFileName);
        } else {
            pdfFile = new File(pContext.getFilesDir(), "/" + pFolderName + "/" + pFileName);
        }

        //File pdfFile = new File(data + "/" + pFolderName + "/" + pFileName);  // -> filename = maven.pdf
        if (pdfFile != null) {
            pdfFile.delete();
            pCallBack.callBack();
        }
    }

    public static boolean isFileDownloaded(Context pContext, String pFolderName, String pFileName) {
        // File pdfFile = new File(Environment.getExternalStorageDirectory()+ "/" + pFolderName + "/" + pFileName);
        // boolean isSDPresent = Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED);
        //Utils.showProgressBar(pContext);

        if (true/*isSDPresent*/) {
            File pdfFile = null;
            pdfFile = FileManager.getAbsoluteFile(pFolderName,pContext);
            AppLog.log("isFileDownloaded)))  +++ getAbsolutePath ", ""+pdfFile.getAbsolutePath());
            File file[] = pdfFile.listFiles();
            //AppLog.log("isFileDownloaded +++ length ", ""+file.length);
            if (file!=null) {
              //  for (int i = 0; i < file.length; i++) {
                    File fileList = pdfFile.getAbsoluteFile();
                    AppLog.log("isFileDownloaded +++ getAbsolutePath ", fileList.toString());
                    if (fileList.toString().contains(pFolderName)) {
                        File[] files = fileList.listFiles();
                        for (File f : files) {
                            AppLog.log("isFileDownloaded ++++ getAbsolutePath file : " + f.getName());
                            if (f.getName().equals(pFileName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        //}
        return false;
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    /**
     * permission is need from Android 6.0
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    public static  File getAbsoluteFile(String relativePath, Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return new File(context.getExternalFilesDir(null), "/"+relativePath);
        } else {
            return new File(context.getFilesDir(), "/"+relativePath);
        }
    }

    public static File getPDFFile(Context mContext, String mFolderName, String mFileName) {
        File f = getAbsoluteFile(mFolderName,mContext);
        Log.d("getAbsoluteFile ","getAbsoluteFile ++ "+f.getAbsolutePath().toString());
        if (!f.exists()) {
            f.mkdirs();
        }
        File pdfFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            pdfFile= new File(mContext.getExternalFilesDir(null), "/" + mFolderName + "/" + mFileName);
        } else {
            pdfFile = new File(mContext.getFilesDir(), "/" + mFolderName + "/" + mFileName);
        }
        return pdfFile;

    }
}
