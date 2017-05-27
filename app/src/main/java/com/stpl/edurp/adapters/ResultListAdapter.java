package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.models.ResultListDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<ResultListDataModel> mList;
    private View.OnClickListener mClickListener;

    public ResultListAdapter(Context context, ArrayList<ResultListDataModel> pList, View.OnClickListener pClickListener) {
        mContext = context;
        mList = pList;
        mClickListener = pClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.resultlist_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageviewSelection.setTag(position);
        holder.imageviewSelection.setOnClickListener(mClickListener);
        holder.textViewSem.setText(mList.get(position).getSemester());
//        if(ResultFragment.selected_sem.trim().length()>0){
//            if(mList.get(position).getSemester().equalsIgnoreCase(ResultFragment.selected_sem))
//             holder.imageviewSelection.setImageResource(R.drawable.selected);
//            else
//                holder.imageviewSelection.setImageResource(R.drawable.select);
//        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
        // return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSem;
        public ImageView imageviewSelection;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewSem = (TextView) itemView.findViewById(R.id.textview_resultlist_row_sem_value);
            imageviewSelection = (ImageView) itemView.findViewById(R.id.imageview_resultlist_row_selection);
        }
    }
}
