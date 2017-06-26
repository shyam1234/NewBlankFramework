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
import android.widget.Toast;

import com.stpl.edurp.interfaces.ICallBack;

import java.io.File;

/**
 * Created by Admin on 25-06-2017.
 */

public class FileManager {


    /**
     * getExternalStorageDirectory creates folder in This "Internal storage\Edurp_results"
     *
     * @param pContext
     * @param pFolderName
     * @param pFileName
     */
    public static void showDownloadFile(Activity pContext, String pFolderName, String pFileName) {
        verifyStoragePermissions(pContext);
        File f = new File(Environment.getExternalStorageDirectory(),pFolderName);
        if(!f.exists()){
            f.mkdirs();
        }
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + pFolderName + "/" + pFileName);  // -> filename = maven.pdf
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
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + pFolderName + "/" + pFileName);  // -> filename = maven.pdf
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
            File file[] = Environment.getExternalStorageDirectory().listFiles();
            if (file != null) {
                for (int i = 0; i < file.length; i++) {
                    File fileList = file[i].getAbsoluteFile();
                    AppLog.log("getAbsolutePath ", fileList.toString());
                    if (fileList.toString().contains(pFolderName)) {
                        File[] files = fileList.listFiles();
                        for (File f : files) {
                            AppLog.log("getAbsolutePath file : " + f.getName());
                            if (f.getName().equals(pFileName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
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


}
