package com.stpl.edurp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.activities.DashboardActivity;
import com.stpl.edurp.adapters.EventsAdapter;
import com.stpl.edurp.models.TableNewsMasterDataModel;
import com.stpl.edurp.utils.GetUILImage;
import com.stpl.edurp.utils.InternetManager;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class EventsFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "EventsFragment";
    private RecyclerView mRecycleViewNews;
    private ArrayList<TableNewsMasterDataModel> mNewsList;
    private EventsAdapter mNewsAdapter;
    private TextView mTextViewUpcomingTab;
    private TextView mTextViewPastTab;
    private Object comments;

    public EventsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mNewsList = new ArrayList<TableNewsMasterDataModel>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_events, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        fetchDataFromServer();
        DashboardActivity.mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        //Toast.makeText(getContext(), "student id : " + UserInfo.studentId, Toast.LENGTH_SHORT).show();
                        DashboardActivity.mHandler.removeMessages(1);
                        initView();
                        fetchDataFromServer();
                        return true;
                }
                return false;
            }
        });
    }

    private void fetchDataFromServer() {

    }

    private void initView() {
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_events);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetUILImage.getInstance().setCircleImage(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        setListener();
        initRecyclerView();
        //default selection of tab
        getUpcoming();
    }


    private void initRecyclerView() {
        mRecycleViewNews = (RecyclerView) getView().findViewById(R.id.recyclerview_news);
        mRecycleViewNews.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewNews.setLayoutManager(manager);
        //----------------------------------------------
        mTextViewUpcomingTab = (TextView) getView().findViewById(R.id.textview_fragment_events_upcoming);
        mTextViewPastTab = (TextView) getView().findViewById(R.id.textview_fragment_events_past);
        mTextViewUpcomingTab.setOnClickListener(this);
        mTextViewPastTab.setOnClickListener(this);
        //-----------------------------------
    }

    private void setListener() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
                break;
            case R.id.textview_fragment_events_upcoming:
                if (InternetManager.isInternetConnected(getContext())) {
                    getUpcoming();
                }
                break;
            case R.id.textview_fragment_events_past:
                if (InternetManager.isInternetConnected(getContext())) {
                    getPast();
                }
                break;
        }
    }


    private void navigateToNextPage(Class mClass) {
        Intent i = new Intent(getActivity(), mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }


    private void getPast() {
        mTextViewPastTab.setBackgroundResource(R.drawable.filled_rect_white);
        mTextViewUpcomingTab.setBackgroundColor(Color.TRANSPARENT);
        mTextViewPastTab.setTextColor(getResources().getColor(R.color.colorGreen));
        mTextViewUpcomingTab.setTextColor(getResources().getColor(R.color.colorWhite));
        mNewsAdapter = new EventsAdapter(getContext(), mNewsList, this);
        mRecycleViewNews.setAdapter(mNewsAdapter);

    }

    private void getUpcoming() {
        // mTextViewUpcomingTab.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mTextViewUpcomingTab.setBackgroundResource(R.drawable.filled_rect_white);
        mTextViewPastTab.setBackgroundColor(Color.TRANSPARENT);
        mTextViewUpcomingTab.setTextColor(getResources().getColor(R.color.colorGreen));
        mTextViewPastTab.setTextColor(getResources().getColor(R.color.colorWhite));
        mNewsAdapter = new EventsAdapter(getContext(), mNewsList, this);
        mRecycleViewNews.setAdapter(mNewsAdapter);
    }
}
