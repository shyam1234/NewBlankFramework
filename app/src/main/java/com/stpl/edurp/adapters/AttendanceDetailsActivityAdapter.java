package com.stpl.edurp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.activities.AttendanceDetailActivity;
import com.stpl.edurp.models.TableAttendanceDetailsDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class AttendanceDetailsActivityAdapter extends RecyclerView.Adapter<AttendanceDetailsActivityAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TableAttendanceDetailsDataModel> mList;
    private View.OnClickListener mListner;

    public AttendanceDetailsActivityAdapter(AttendanceDetailActivity context, ArrayList<TableAttendanceDetailsDataModel> mAttendanceDetailsList) {
        mContext = context;
        mList = mAttendanceDetailsList;
        mListner = (View.OnClickListener)mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.attendance_details_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.subject.setText(mList.get(position).getSubject());
        holder.subjectPercentage.setText(mList.get(position).getPercentage());
        holder.subjectPercentage.setTextColor(Color.parseColor(mList.get(position).getColor()));
        holder.totalValue.setText(mList.get(position).getTotal());
        holder.absentValue.setText(mList.get(position).getAbsent());
        holder.holder.setOnClickListener(mListner);
        holder.holder.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectPercentage;
        public TextView subject;
        public TextView totalValue;
        public TextView absentValue;
        public LinearLayout holder;

        public MyViewHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.textview_attendance_row_subject_value1);
            totalValue = (TextView) itemView.findViewById(R.id.textview_attendance_row_total_value);
            subjectPercentage = (TextView) itemView.findViewById(R.id.textview_attendance_row_subject_value);
            absentValue = (TextView) itemView.findViewById(R.id.textview_attendance_row_absent_value);
            holder = (LinearLayout)itemView.findViewById(R.id.lin_attendance);
        }
    }
}
