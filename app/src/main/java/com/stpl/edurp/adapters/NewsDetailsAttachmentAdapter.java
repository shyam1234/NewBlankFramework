package com.stpl.edurp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.activities.NewsDetails;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.interfaces.ICallBack;
import com.stpl.edurp.models.TableDocumentMasterDataModel;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.DownloadFileAsync;
import com.stpl.edurp.utils.FileManager;
import com.stpl.edurp.utils.InternetManager;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class NewsDetailsAttachmentAdapter extends RecyclerView.Adapter<NewsDetailsAttachmentAdapter.MyViewHolder> implements View.OnClickListener {
    private static final java.lang.String TAG = NewsDetailsAttachmentAdapter.class.getName();
    private Context mContext;
    private ArrayList<TableDocumentMasterDataModel> mList;

    public NewsDetailsAttachmentAdapter(NewsDetails newsDetails, ArrayList<TableDocumentMasterDataModel> commentMaster) {
        mContext = newsDetails;
        mList = commentMaster;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.attachment_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewDocumentName.setText(mList.get(position).getDocumentname());
        holder.textViewDocumentName.setTag(new ViewHolderWithPosition(holder, position));
        holder.textViewDocumentName.setOnClickListener(this);
        holder.mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_attachment:
                ViewHolderWithPosition holder = (ViewHolderWithPosition) v.getTag();
                downloadAndShowAttachment(holder);
                break;
        }
    }

    private void downloadAndShowAttachment(final ViewHolderWithPosition holder) {
        String fileURL = mList.get(holder.mPosition).getFileURL();
        final String documentId = mList.get(holder.mPosition).getDocumentId();
        if (fileURL != null && documentId != null) {
            AppLog.log(TAG, "Mediatype: " + mList.get(holder.mPosition).getMediatype());
            AppLog.log(TAG, "Document Id: " + documentId);
            AppLog.log(TAG, "FileURL " + fileURL);
            holder.mHolder.mProgressBar.setVisibility(View.VISIBLE);
            if (!FileManager.isFileDownloaded(mContext, WSContant.DOWNLOAD_FOLDER, documentId + ".pdf")) {
                if (InternetManager.isInternetConnected(mContext)) {
                    new DownloadFileAsync((Activity) mContext, WSContant.DOWNLOAD_FOLDER, false, new ICallBack() {
                        @Override
                        public void callBack() {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    FileManager.showDownloadFile(((Activity) mContext), WSContant.DOWNLOAD_FOLDER, documentId + ".pdf");
                                    holder.mHolder.mProgressBar.setVisibility(View.GONE);
                                }
                            });
                        }

                        //added "" to use the existing framework for attachment.
                    }).execute(fileURL, "", documentId);
                }
            } else {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FileManager.showDownloadFile(((Activity) mContext), WSContant.DOWNLOAD_FOLDER, documentId + ".pdf");
                        holder.mHolder.mProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewDocumentName;
        private final ProgressBar mProgressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewDocumentName = (TextView) itemView.findViewById(R.id.textview_attachment);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar_attachment_row);
        }
    }

    private class ViewHolderWithPosition {
        public MyViewHolder mHolder;
        public int mPosition;

        public ViewHolderWithPosition(MyViewHolder holder, int position) {
            mHolder = holder;
            mPosition = position;
        }
    }
}
