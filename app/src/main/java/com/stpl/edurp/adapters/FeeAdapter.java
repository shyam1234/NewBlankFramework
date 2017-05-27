package com.stpl.edurp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.models.TableFeeMasterDataModel;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TableFeeMasterDataModel> mList;
    private View.OnClickListener mListener;

    public FeeAdapter(Context context, ArrayList<TableFeeMasterDataModel> pList, View.OnClickListener pListener) {
        mContext = context;
        mList = pList;
        mListener = pListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.noticeboard_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mList.get(position).getStatus().equalsIgnoreCase(WSContant.TAG_VALUE_NOT_PAID)) {
            holder.noticeboard_row_fee_holder.setVisibility(View.VISIBLE);
            holder.noticeboard_row_activity_fee_row_holder.setVisibility(View.GONE);
            holder.mTextViewRPValue.setText(mList.get(position).getTotalDue());
            holder.mTextViewDueDate.setText(Utils.getTimeInYYYYMMDD(mList.get(position).getDueDate()));
            holder.mButtonPayNow.setOnClickListener(mListener);
            holder.mButtonPayNow.setTag(position);
            holder.noticeboard_row_fee_holder.setOnClickListener(mListener);
            holder.noticeboard_row_fee_holder.setTag(position);
            holder.btnViewDetails.setOnClickListener(mListener);
            holder.btnViewDetails.setTag(position);
        } else {
            holder.noticeboard_row_activity_fee_row_holder.setVisibility(View.VISIBLE);
            holder.noticeboard_row_fee_holder.setVisibility(View.GONE);
            holder.textViewDate.setText(Utils.getTimeInYYYYMMDD(mList.get(position).getDueDate()));
            holder.textViewPaymentValue.setText(mList.get(position).getTotalDue());
            holder.btnViewDetails.setOnClickListener(mListener);
            holder.btnViewDetails.setTag(position);
            holder.noticeboard_row_activity_fee_row_holder.setOnClickListener(mListener);
            holder.noticeboard_row_activity_fee_row_holder.setTag(position);
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDate;
        public TextView textViewPaymentValue;
        public Button btnViewDetails;

        private TextView mTextViewRPValue;
        private TextView mTextViewDueDate;
        //private Button mButtonViewDetails;
        private TextView mButtonPayNow;
        private LinearLayout noticeboard_row_fee_holder;
        private LinearLayout noticeboard_row_activity_fee_row_holder;


        public MyViewHolder(View itemView) {
            super(itemView);
            textViewDate = (TextView) itemView.findViewById(R.id.textview_fee_row_date_vaule);
            textViewPaymentValue = (TextView) itemView.findViewById(R.id.textview_fee_row_part_payment_value);
            btnViewDetails = (Button) itemView.findViewById(R.id.btn_download_details);
            noticeboard_row_activity_fee_row_holder = (LinearLayout) itemView.findViewById(R.id.lin_noticeboard_row_activity_fee_row_holder);
            //------------------------------------
            mTextViewRPValue = (TextView) itemView.findViewById(R.id.textview_fee_rp_value);
            mTextViewDueDate = (TextView) itemView.findViewById(R.id.textview_duedate_value);
            mButtonPayNow = (TextView) itemView.findViewById(R.id.btn_view_pay_now);
            noticeboard_row_fee_holder = (LinearLayout) itemView.findViewById(R.id.lin_noticeboard_row_fee_holder);

        }
    }
}
