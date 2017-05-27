package com.stpl.edurp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.models.DashboardCellDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 11-12-2016.
 */

public class HomeAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<DashboardCellDataModel> mCellList;
    private View.OnClickListener mListner;

    public HomeAdapter(Context pContext, ArrayList<DashboardCellDataModel> pCellList, View.OnClickListener pListner) {
        mContext = pContext;
        mCellList = pCellList;
        mListner = pListner;
    }

    @Override
    public int getCount() {
        return mCellList.size();
    }

    @Override
    public DashboardCellDataModel getItem(int i) {
        return mCellList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (view == null) {
            view = inflater.inflate(R.layout.dashboard_cell, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }
        holder.setImage(mCellList.get(position).getMenuImage());
        holder.setTextView(mCellList.get(position).getText());
        holder.setNotificationCounter(mCellList.get(position).getNotification());
        holder.cellBgLayout.setBackgroundResource(mCellList.get(position).getColor());
        holder.cellBgLayout.setOnClickListener(mListner);
        holder.textView.setTag(mCellList.get(position).getMenu_code());
        holder.cellBgLayout.setTag(mCellList.get(position).getMenu_code());
        return holder.getView();
    }


    private class ViewHolder {
        private View view;
        private ImageView image;
        public TextView textView;
        private TextView notificationCounter;
        private RelativeLayout cellLayout;
        public RelativeLayout cellBgLayout;

        public ViewHolder(View view) {
            this.view = view;
            image = (ImageView) view.findViewById(R.id.imageview_dashboard_holder);
            textView = (TextView) view.findViewById(R.id.textview_dashboard_cell_name);
            notificationCounter = (TextView) view.findViewById(R.id.textview_dashboard_notifcation_count);
            cellLayout = (RelativeLayout) view.findViewById(R.id.rel_cell_holder);
            cellBgLayout = (RelativeLayout) view.findViewById(R.id.rel_dashboard_cell_bg_holder);

        }

        public View getView() {
            return view;
        }

        public ImageView getImage() {
            return image;
        }

        public void setImage(int id) {
            image.setImageResource(id);
        }

        public void setColor(int color) {
            cellLayout.setBackgroundResource(color);
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(String str) {
            this.textView.setText(str);
        }

        public TextView getNotificationCounter() {
            return notificationCounter;
        }

        public void setNotificationCounter(int number) {
            if (number > 0) {
                notificationCounter.setVisibility(View.VISIBLE);
            }
            if (number > 0 && number < 10) {
                this.notificationCounter.setText("0" + number);
            } else {
                this.notificationCounter.setText("" + number);
            }

        }

        public RelativeLayout getCellLayout() {
            return cellLayout;
        }

        public void setCellLayout(int color) {
            this.cellLayout.setBackgroundColor(color);
        }
    }
}
