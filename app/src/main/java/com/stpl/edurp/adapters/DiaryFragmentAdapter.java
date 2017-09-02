package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.models.DiaryDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class DiaryFragmentAdapter extends RecyclerView.Adapter<DiaryFragmentAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<DiaryDataModel> mList;
    private View.OnClickListener mListner;

    public DiaryFragmentAdapter(Context context, View.OnClickListener pListner, ArrayList<DiaryDataModel> mDiaryArrayList) {
        mContext = context;
        mList = mDiaryArrayList;
        mListner =  pListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.fragment_diary_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.subject.setText(mList.get(position).getSubject());
//        holder.subjectPercentage.setText(mList.get(position).getPercentage());
//        holder.subjectPercentage.setTextColor(Color.parseColor(mList.get(position).getColor()));
//        holder.totalValue.setText(mList.get(position).getTotal());
//        holder.absentValue.setText(mList.get(position).getAbsent());
//        holder.holder.setOnClickListener(mListner);
//        holder.holder.setTag(position);
    }


    @Override
    public int getItemCount() {
        return 10;//mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectPercentage;
        public TextView subject;
        public TextView totalValue;
        public TextView absentValue;
        public LinearLayout holder;

        public MyViewHolder(View itemView) {
            super(itemView);
//            subject = (TextView) itemView.findViewById(R.id.textview_attendance_row_subject_value1);
//            totalValue = (TextView) itemView.findViewById(R.id.textview_attendance_row_total_value);
//            subjectPercentage = (TextView) itemView.findViewById(R.id.textview_attendance_row_subject_value);
//            absentValue = (TextView) itemView.findViewById(R.id.textview_attendance_row_absent_value);
//            holder = (LinearLayout) itemView.findViewById(R.id.lin_attendance);
        }
    }
}
