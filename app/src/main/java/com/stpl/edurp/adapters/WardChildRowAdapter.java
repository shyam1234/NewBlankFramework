package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.models.TableStudentDetailsDataModel;
import com.stpl.edurp.utils.GetPicassoImage;

import java.util.ArrayList;

/**
 * Created by Admin on 07-01-2017.
 */

public class WardChildRowAdapter extends RecyclerView.Adapter<WardChildRowAdapter.MyViewHolder> {

    private final Context mContext;
    private final View.OnClickListener mListener;
    private ArrayList<TableStudentDetailsDataModel> mListHolder;


    public WardChildRowAdapter(Context context, ArrayList<TableStudentDetailsDataModel> pListChildInfoHolder, View.OnClickListener pListener) {
        mContext = context;
        mListHolder = pListChildInfoHolder;
        mListener = pListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.ward_row, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextViewName.setText(mListHolder.get(position).getFullName());
        holder.mTextViewAddress.setText(mListHolder.get(position).getUnversity_name());
        GetPicassoImage.setCircleImageByPicasso(mContext,mListHolder.get(position).getImageurl(), holder.mImageViewChildIcon);
        holder.mRelHolder.setOnClickListener(mListener);
        holder.mRelHolder.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mListHolder.size();
    }


    //create own view holder to club all the UI requirement
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout mRelHolder;
        private final ImageView mImageViewChildIcon;
        private final TextView mTextViewName;
        private final TextView mTextViewAddress;

        public MyViewHolder(View v) {
            super(v);
            mImageViewChildIcon = (ImageView) v.findViewById(R.id.imageview_ward_row_icon);
            mTextViewName = (TextView) v.findViewById(R.id.textview_ward_row_name);
            mTextViewAddress = (TextView) v.findViewById(R.id.textview_ward_row_address);
            mRelHolder = (RelativeLayout) v.findViewById(R.id.rel_ward_row_holder);

        }
    }

}

