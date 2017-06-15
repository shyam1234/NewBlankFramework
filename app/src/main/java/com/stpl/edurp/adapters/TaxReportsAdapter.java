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
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.models.PayslipDataModel;
import com.stpl.edurp.models.TaxReportsDataModel;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 27-05-2017.
 */

public class TaxReportsAdapter extends RecyclerView.Adapter<TaxReportsAdapter.MyViewHolder> {

    private final ArrayList<TaxReportsDataModel.TaxReportsArray> mPayslipList;
    private final View.OnClickListener mListener;
    private Context mContext;

    public TaxReportsAdapter(Context context, ArrayList<TaxReportsDataModel.TaxReportsArray> mPayslipDataModel, View.OnClickListener pListener) {
        mContext = context;
        mPayslipList = mPayslipDataModel;
        mListener = pListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.fragment_payslip_row, parent, false);
        return new TaxReportsAdapter.MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder pHolder, int position) {
        pHolder.download_icon.setId(R.id.imgview_payslip_download);
        pHolder.download_icon.setTag(position);
        pHolder.delete_icon.setTag(position);
        pHolder.month.setText(mPayslipList.get(position).getFinancialYearCode());
        pHolder.download_icon.setOnClickListener(mListener);
        pHolder.delete_icon.setOnClickListener(mListener);
        pHolder.rel_row_holder.setId(R.id.rel_payslip_holder);
        pHolder.rel_row_holder.setOnClickListener(mListener);
        pHolder.rel_row_holder.setTag(position);
        if (!Utils.isFileDownloaded(mContext, WSContant.DOWNLOAD_FOLDER, mPayslipList.get(position).getFinancialYearId() + ".pdf")) {
            pHolder.delete_icon.setVisibility(View.GONE);
            pHolder.download_icon.setVisibility(View.VISIBLE);
        }else{
            pHolder.delete_icon.setVisibility(View.VISIBLE);
            pHolder.download_icon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mPayslipList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView month;
        private final ImageView download_icon;
        private final RelativeLayout rel_row_holder;
        private final ImageView delete_icon;

        public MyViewHolder(View view) {
            super(view);
            month = (TextView) view.findViewById(R.id.textview_payslip_month);
            download_icon = (ImageView) view.findViewById(R.id.imgview_payslip_download);
            delete_icon = (ImageView) view.findViewById(R.id.imgview_payslip_downloaded);
            rel_row_holder = (RelativeLayout)view.findViewById(R.id.rel_payslip_holder);
        }
    }
}
