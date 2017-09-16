package com.stpl.edurp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stpl.edurp.R;
import com.stpl.edurp.adapters.DiaryFragmentAdapter;
import com.stpl.edurp.constant.Constant;
import com.stpl.edurp.models.DiaryDataModel;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.CalendarView;
import com.stpl.edurp.utils.GetUILImage;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class DiaryFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "DiaryFragment";
    private BottomSheetBehavior<View> mBottomSheetBehavior1;
    private CalendarView mCalendarView;
    private RecyclerView mRecycleView;
    private DiaryFragmentAdapter mRecyclerAdapter;
    private ArrayList<DiaryDataModel> mDiaryArrayList;

    public DiaryFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mDiaryArrayList = new ArrayList<DiaryDataModel>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_diary, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        /*DashboardActivity.mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch ((Integer) msg.what) {
                    case 1:
                        //Toast.makeText(getContext(), "student id : " + UserInfo.studentId, Toast.LENGTH_SHORT).show();
                        DashboardActivity.mHandler.removeMessages(1);
                        initView();
                        return true;
                }
                return false;
            }
        });*/
    }

    private void initView() {
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.diary);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetUILImage.getInstance().setCircleImage(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        mCalendarView = (CalendarView) getView().findViewById(R.id.calendar_diary);
        View bottomSheet = getView().findViewById(R.id.bottom_sheet1);
        mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mCalendarView.post(new Runnable() {
            @Override
            public void run() {
                AppLog.log("mCalendarView.getHeight() " + mCalendarView.getHeight());
                mBottomSheetBehavior1.setPeekHeight(mCalendarView.getHeight() - Constant.DIARY_LIST_MARGIN_HEIGHT);
            }
        });

        initRecyclerView();
        setListener();
    }

    private void initRecyclerView() {
        mRecycleView = (RecyclerView) getView().findViewById(R.id.recyclerview_diary_bottomsheet);
        mRecycleView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleView.setLayoutManager(manager);
        mRecyclerAdapter = new DiaryFragmentAdapter(getContext(), this, mDiaryArrayList);
        mRecycleView.setAdapter(mRecyclerAdapter);

    }

    private void setListener() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
                break;
        }
    }

    private void navigateToNextPage(Class mClass) {
        Intent i = new Intent(getActivity(), mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }
}
