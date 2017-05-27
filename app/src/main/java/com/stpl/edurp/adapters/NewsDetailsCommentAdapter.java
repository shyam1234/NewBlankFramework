package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.activities.NewsDetails;
import com.stpl.edurp.models.NewsDetailsCommentLikeDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class NewsDetailsCommentAdapter extends RecyclerView.Adapter<NewsDetailsCommentAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<NewsDetailsCommentLikeDataModel.CommentMasterDataModel> mList;

    public NewsDetailsCommentAdapter(NewsDetails newsDetails, ArrayList<NewsDetailsCommentLikeDataModel.CommentMasterDataModel> commentMaster) {
        mContext = newsDetails;
        mList = commentMaster;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //View view = inflater.inflate(R.layout.attendance_row, null);
        //if use null then recyclerview not take match_parent as width
        View viewHolder = inflater.inflate(R.layout.comment_row, parent, false);
        //viewHolder.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewDate.setText("" + mList.get(position).getCommentedOn());
        holder.textViewName.setText("" + mList.get(position).getCommentedBy());
        holder.textviewComment.setText("" + mList.get(position).getComment());
        holder.textviewComment.setVisibility(View.VISIBLE);
    }


    @Override
    public int getItemCount() {
        return mList.size();
        // return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageViewProfilePic;
        public final TextView textViewName;
        public final TextView textViewDate;
        public final TextView textviewComment;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textview_name);
            textViewDate = (TextView) itemView.findViewById(R.id.textview_date);
            textviewComment = (TextView) itemView.findViewById(R.id.textview_comment_like_row_description);
            imageViewProfilePic = (ImageView) itemView.findViewById(R.id.imageview_header_info);
        }
    }
}
