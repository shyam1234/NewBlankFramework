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
import com.stpl.edurp.fragments.TaxReportsFragment;
import com.stpl.edurp.models.TaxReportsDataModel;
import com.stpl.edurp.utils.FileManager;
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
        View viewHolder = inflater.inflate(R.layout.fragment_taxreports_row, parent, false);
        return new TaxReportsAdapter.MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder pHolder, int position) {
        pHolder.financial_year.setText(/*mContext.getText(R.string.financial_year) +*/ mPayslipList.get(position).getFinancialYearCode());

        pHolder.download_form_icon.setOnClickListener(mListener);
        pHolder.download_computation_icon.setOnClickListener(mListener);
        pHolder.download_form_icon.setTag(position);
        pHolder.download_computation_icon.setTag(position);

        pHolder.delete_form_icon.setOnClickListener(mListener);
        pHolder.delete_computation_icon.setOnClickListener(mListener);
        pHolder.delete_computation_icon.setTag(position);
        pHolder.delete_form_icon.setTag(position);

        pHolder.text_form.setOnClickListener(mListener);
        pHolder.text_computation.setOnClickListener(mListener);
        pHolder.text_form.setTag(position);
        pHolder.text_computation.setTag(position);

        if (!FileManager.isFileDownloaded(mContext, WSContant.DOWNLOAD_FOLDER, mPayslipList.get(position).getFinancialYearId() + ".pdf")) {
            pHolder.delete_form_icon.setVisibility(View.GONE);
            pHolder.download_form_icon.setVisibility(View.VISIBLE);
        } else {
            pHolder.delete_form_icon.setVisibility(View.VISIBLE);
            pHolder.download_form_icon.setVisibility(View.GONE);
        }
        if (!FileManager.isFileDownloaded(mContext, WSContant.DOWNLOAD_FOLDER, mPayslipList.get(position).getFinancialYearId() + TaxReportsFragment.TAG_COMPUTATION + ".pdf")) {
            pHolder.delete_computation_icon.setVisibility(View.GONE);
            pHolder.download_computation_icon.setVisibility(View.VISIBLE);
        } else {
            pHolder.delete_computation_icon.setVisibility(View.VISIBLE);
            pHolder.download_computation_icon.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mPayslipList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rel_row_holder;
        private final ImageView delete_form_icon;
        private final ImageView download_form_icon;
        private final ImageView download_computation_icon;
        private final TextView financial_year;
        private final ImageView delete_computation_icon;
        private final TextView text_computation;
        private final TextView text_form;

        public MyViewHolder(View view) {
            super(view);
            text_form = (TextView) view.findViewById(R.id.textview_download_form_16);
            text_computation = (TextView) view.findViewById(R.id.textview_download_computationsheet);
            financial_year = (TextView) view.findViewById(R.id.textview_taxreports_row_header);
            download_form_icon = (ImageView) view.findViewById(R.id.imgview_download_form_16);
            download_computation_icon = (ImageView) view.findViewById(R.id.imgview_download_computationsheet);
            delete_form_icon = (ImageView) view.findViewById(R.id.imgview_taxreports_form_downloaded);
            delete_computation_icon = (ImageView) view.findViewById(R.id.imgview_taxreports_downloaded1);
            rel_row_holder = (RelativeLayout) view.findViewById(R.id.rel_taxreports_holder);
        }
    }
}
