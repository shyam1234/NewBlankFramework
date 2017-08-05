package com.stpl.edurp.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.interfaces.ICallBack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Admin on 18-03-2017.
 */

public class DownloadFileAsync extends AsyncTask<String, String, String> {
    public static final String TAG = "DownloadFileAsync";
    private final Context mContext;
    private final String mFolderName;
    private final ICallBack mCallback;

    public DownloadFileAsync(Activity pContext, String pFolderName, ICallBack pCallback) {
        mContext = pContext;
        mFolderName = pFolderName;
        mCallback = pCallback;
        FileManager.verifyStoragePermissions(pContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Utils.showProgressBar(mContext);
    }

    @Override
    protected void onPostExecute(String pFileName) {
        Utils.dismissProgressBar();
        mCallback.callBack();
        //Utils.showDownloadFile(WSContant.DOWNLOAD_FOLDER,pFileName);
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    @Override
    protected String doInBackground(String... strings) {
        String mFileName = "";
        String fileUrl = null;
        if (strings[1].equalsIgnoreCase("")) {
            fileUrl = strings[0];
        } else {
            fileUrl = strings[0] + strings[1];   // -> http://maven.apache.org/maven-1.x/maven.pdf
        }

        AppLog.networkLog(TAG, "fileUrl: " + fileUrl);
        if (strings.length < 3) {
            mFileName = strings[1] + ".pdf";  // -> maven.pdf
        } else if (strings[2] != null) {
            mFileName = strings[2] + ".pdf";  // -> maven.pdf
        }
        try {

            File pdfFile = FileManager.getPDFFile(mContext, mFolderName, mFileName);
//            File mFolder = new File(Environment.getExternalStorageDirectory().toString(), mFolderName);
//            mFolder.mkdir();
//            File pdfFile = new File(mFolder, mFileName);
            try {
                if (!pdfFile.exists())
                    pdfFile.createNewFile();
            } catch (IOException e) {
                AppLog.errLog(TAG, "createNewFile" + e.getMessage());
            }
            if (!FileDownloader.downloadFile(fileUrl, pdfFile)) {
                mFileName = "";
            }
            Utils.dismissProgressBar();
        } catch (Exception e) {

            AppLog.errLog(TAG, "doInBackground" + e.getMessage());
        }
        return mFileName;
    }

    private static class FileDownloader {
        private static final int MEGABYTE = 1024 * 1024;

        public static boolean downloadFile(String fileUrl, File directory) {
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty(WSContant.TAG_TOKEN, UserInfo.authToken);
                urlConnection.setRequestProperty(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
                //-----------------------------------
                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(directory);
                int totalSize = urlConnection.getContentLength();
                AppLog.networkLog(TAG, "totalSize: " + totalSize);
                //-----------------------------------
                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
                return true;
            } catch (FileNotFoundException e) {
                AppLog.errLog(TAG, "downloadFile11 FileNotFoundException " + e.getMessage());
                directory.delete();
            } catch (MalformedURLException e) {
                AppLog.errLog(TAG, "downloadFile22 " + e.getMessage());
                directory.delete();
            } catch (IOException e) {
                AppLog.errLog(TAG, "downloadFile333 " + e.getMessage());
                directory.delete();
            } catch (Exception e) {
                AppLog.errLog(TAG, "downloadFile4444" + e.getMessage());
                directory.delete();
            }
            return false;
        }
    }
}



