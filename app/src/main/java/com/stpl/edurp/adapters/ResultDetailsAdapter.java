package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.models.TableResultDetailsDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class ResultDetailsAdapter extends RecyclerView.Adapter<ResultDetailsAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TableResultDetailsDataModel.InnerResultDetails> mList;
    private View.OnClickListener mClickListener;

    public ResultDetailsAdapter(Context context, ArrayList<TableResultDetailsDataModel.InnerResultDetails>  pList, View.OnClickListener pClickListener) {
        mContext = context;
        mList = pList;
        mClickListener = pClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.result_details_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textview_subject.setText(mList.get(position).getSubjectName());
        //holder.textview_credits.setText(mList.get(position).getCredits());
        holder.textview_grade.setText(mList.get(position).getGrade());
        holder.textview_result.setText(mList.get(position).getResult());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textview_subject;
       // private final TextView textview_credits;
        private final TextView textview_grade;
        private final TextView textview_result;

        public MyViewHolder(View itemView) {
            super(itemView);
            textview_subject = (TextView) itemView.findViewById(R.id.textview_results_sem_value);
           // textview_credits = (TextView) itemView.findViewById(R.id.textview_result_details_credits);
            textview_grade = (TextView) itemView.findViewById(R.id.textview_result_row_grade_value);
            textview_result = (TextView) itemView.findViewById(R.id.textview_result_row_result_value);
        }
    }
}
