package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.models.TableTimeTableDetailsDataModel;
import com.stpl.edurp.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */
public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel> mList;

    public TimeTableAdapter(Context context, ArrayList<TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel> pList) {
        mContext = context;
        mList = pList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.fragment_timetable_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewTime.setText(mList.get(position).getTTime());
        holder.textViewSubject.setText(mList.get(position).getSubjectName());
        holder.textViewFaculty.setText(mList.get(position).getFaculty());
        holder.textViewRoom.setText(mList.get(position).getRoomName());
        AppLog.log("onBindViewHolder", " mList.get(position).getIsPresent() +++ " + mList.get(position).getIsPresent());
        if (mList.get(position).getIsPresent() != null && mList.get(position).getIsPresent().equalsIgnoreCase(WSContant.TAG_PRESENT)) {
            holder.imageviewTimetableIcon.setImageResource(R.drawable.attended);
            holder.textViewRoomNR.setVisibility(View.GONE);
            holder.imageviewTimetableIcon.setVisibility(View.VISIBLE);
        } else if (mList.get(position).getIsPresent().equalsIgnoreCase(WSContant.TAG_ABSENT)) {
            holder.imageviewTimetableIcon.setImageResource(R.drawable.absent);
            holder.textViewRoomNR.setVisibility(View.GONE);
            holder.imageviewTimetableIcon.setVisibility(View.VISIBLE);
        } else {
            holder.textViewRoomNR.setText(mList.get(position).getIsPresent());
            holder.textViewRoomNR.setVisibility(View.VISIBLE);
            holder.imageviewTimetableIcon.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewRoomNR;
        public TextView textViewTime;
        public TextView textViewSubject;
        public TextView textViewFaculty;
        public TextView textViewRoom;
        public ImageView imageviewTimetableIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewTime = (TextView) itemView.findViewById(R.id.textview_timetable_time_value);
            textViewSubject = (TextView) itemView.findViewById(R.id.textview_timetable_subject);
            textViewFaculty = (TextView) itemView.findViewById(R.id.textview_timetable_faculty);
            imageviewTimetableIcon = (ImageView) itemView.findViewById(R.id.imageview_timetable);
            textViewRoom = (TextView) itemView.findViewById(R.id.textview_timetable_room_value);
            textViewRoomNR = (TextView) itemView.findViewById(R.id.textview_timetable_NR);

        }
    }
}
