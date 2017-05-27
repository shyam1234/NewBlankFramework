package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.models.TableResultMasterDataModel;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TableResultMasterDataModel> mList;
    private View.OnClickListener mListner;

    public ResultAdapter(Context context, ArrayList<TableResultMasterDataModel> pList, View.OnClickListener pListner) {
        mContext = context;
        mList = pList;
        mListner = pListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //View view = inflater.inflate(R.layout.attendance_row, null);
        //if use null then recyclerview not take match_parent as width
        View viewHolder = inflater.inflate(R.layout.result_row, parent, false);
        //viewHolder.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewSemester.setText(mList.get(position).getSemesterName());
        holder.textViewIndex.setText(mList.get(position).getAchievementIndex());
        holder.textViewResult.setText(mList.get(position).getResult());
        holder.mTextViewSubject.setText(mList.get(position).getCourseName());
        holder.mTextViewPublishedByAndTime.setText(mList.get(position).getPublishedBy() + " : " + Utils.getTimeInYYYYMMDD(mList.get(position).getPublishedOn()));
        holder.textViewResult.setText(mContext.getString(R.string.tab_fee));
        holder.linearLayout.setOnClickListener(mListner);
        holder.linearLayout.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewSemester;
        public final TextView textViewIndex;
        public final TextView textViewResult;
        private final LinearLayout linearLayout;
        private final TextView mTextViewPublishedByAndTime;
        private final TextView mTextViewTagValue;
        private final TextView mTextViewSubject;

        public MyViewHolder(View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.lin_result_holder);
            mTextViewSubject = (TextView) itemView.findViewById(R.id.textview_subject);
            mTextViewPublishedByAndTime = (TextView) itemView.findViewById(R.id.textview_result_row_admin_time);
            //---------------------------------------------------------
            textViewSemester = (TextView) itemView.findViewById(R.id.textview_results_sem_value);
            textViewIndex = (TextView) itemView.findViewById(R.id.textview_result_row_grade_value);
            textViewResult = (TextView) itemView.findViewById(R.id.textview_result_row_result_value);
            //------------------------------------
            mTextViewTagValue = (TextView) itemView.findViewById(R.id.textview_result_row_tag_value);

        }
    }
}
