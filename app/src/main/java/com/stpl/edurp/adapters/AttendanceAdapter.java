package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.models.TableCourseMasterDataModel;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TableCourseMasterDataModel> mList;
    private View.OnClickListener mListner;


    public AttendanceAdapter(Context context, ArrayList<TableCourseMasterDataModel> mStudentDetailList,
                             View.OnClickListener pListner) {
        mContext = context;
        mList = mStudentDetailList;
        mListner =  pListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.attendance_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewSubject.setText(mList.get(position).getCourse());
        holder.textViewSemester.setText(mList.get(position).getSemester());
        holder.textViewLastSyncDate.setText(Utils.getTimeInDayDateMonthYear(mList.get(position).getLastRetrieved()));
        holder.linearLayout.setOnClickListener(mListner);
        holder.linearLayout.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView textViewSubject;
        public TextView textViewSemester;
        public TextView textViewLastSyncDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.lin_holder);
            textViewSubject = (TextView) itemView.findViewById(R.id.textview_attendance_row_subject);
            textViewSemester = (TextView) itemView.findViewById(R.id.textview_attendance_row_semester);
            textViewLastSyncDate = (TextView) itemView.findViewById(R.id.textview_attendance_row_lastsync_value);
        }
    }
}
