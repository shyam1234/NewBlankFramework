package com.stpl.edurp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.activities.DashboardActivity;
import com.stpl.edurp.adapters.TimeTableAdapter;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableTimeTableDetails;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.TableTimeTableDetailsDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.GetPicassoImage;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 24-12-2016.
 */

public class TimeTableFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "TimeTableFragment";
    private RecyclerView mRecycleViewTimeTable;
    private TimeTableAdapter mTimeTableAdapter;
    private ArrayList<TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel> mTimetableList;
    private ImageButton mleftArrow;
    private ImageButton mRightArrow;
    private TextView mTextViewDate;

    public TimeTableFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mTimetableList = new ArrayList<TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_timetable, null);
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
                switch ((Integer) msg.what) {
                    case 1:
                        Toast.makeText(getContext(), "student id : " + UserInfo.studentId, Toast.LENGTH_SHORT).show();
                        DashboardActivity.mHandler.removeMessages(1);
                        initView();
                        fetchDataFromServer();
                        return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        //------------------------------------
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_time_table);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        mleftArrow = (ImageButton) getView().findViewById(R.id.imagebtn_left_arrow);
        mRightArrow = (ImageButton) getView().findViewById(R.id.imagebtn_right_arrow);
        mTextViewDate = (TextView) getView().findViewById(R.id.textview_timetable_date);
        mleftArrow.setOnClickListener(this);
        mRightArrow.setOnClickListener(this);
        mTextViewDate.setOnClickListener(this);

        //set today date and show to textview
        //initTimeTable();
        mTextViewDate.setText(Utils.getTimeInDayDateMonthYear(Utils.getCurrTimeYYYYMMDD()));
        initRecyclerView();
        setListener();
    }


    private void initRecyclerView() {
        mRecycleViewTimeTable = (RecyclerView) getView().findViewById(R.id.recyclerview_timetable);
        mRecycleViewTimeTable.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewTimeTable.setLayoutManager(manager);
        mTimeTableAdapter = new TimeTableAdapter(getContext(), mTimetableList);
        mRecycleViewTimeTable.setAdapter(mTimeTableAdapter);
    }

    private void setListener() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
                break;
            case R.id.imagebtn_left_arrow:
                mTextViewDate.setText(Utils.getTimeInDayDateMonthYear(mTextViewDate.getText().toString(), -1));
                fetchDataFromServer();
                break;
            case R.id.imagebtn_right_arrow:
                mTextViewDate.setText(Utils.getTimeInDayDateMonthYear(mTextViewDate.getText().toString(), 1));
                fetchDataFromServer();
                break;
            case R.id.textview_timetable_date:
                showPopupCalender(getContext());
                fetchDataFromServer();
                break;
        }


    }

    public void showPopupCalender(Context pContext) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(pContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String str = Utils.getTimeInDayDateMonthYear(new StringBuilder().append(year)
                        .append("").append(((month + 1) / 10 != 0 ? (month + 1) : "0" + (month + 1))).append("").append(dayOfMonth)
                        .append(" ").toString());
                AppLog.log("Time::: " + str);
                mTextViewDate.setText(str);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void fetchDataFromServer() {
        TableTimeTableDetails table = new TableTimeTableDetails();
        table.openDB(getContext());
        mTimetableList = table.getData(UserInfo.studentId, Utils.getCurrTimeYYYYMMDD(mTextViewDate.getText().toString()));
        //Collections.sort(mTimetableList,Collections.<TableTimeTableDetailsDataModel>reverseOrder());
        table.closeDB();
        //----------------------------------------------------------
        if (Utils.isInternetConnected(getContext())) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_DATELASTRETRIEVED, SharedPreferencesApp.getInstance().getLastRetrieveTime(WSContant.TAG_WS_TIMETABLE));
            header.put(WSContant.TAG_NEW, Utils.getCurrTimeYYYYMMDD());
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_STUDENTID, "" + UserInfo.studentId);
            body.put(WSContant.TAG_TIMETABLEDATE, Utils.getCurrTimeYYYYMMDD(mTextViewDate.getText().toString()));
            Utils.showProgressBar(getContext());
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_TIMETABLE, header, body, WSContant.TAG_WS_TIMETABLE, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    mTimetableList.clear();
                    ParseResponse obj = new ParseResponse(response, TableTimeTableDetailsDataModel.class, ModelFactory.MODEL_TIMETABLEDETAILS);
                    TableTimeTableDetailsDataModel holder = ((TableTimeTableDetailsDataModel) obj.getModel());
                    if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
                        mTimetableList = holder.getMessageBody();
                        Collections.sort(mTimetableList/*, Collections.<TableTimeTableDetailsDataModel.InnerTimeTableDetailDataModel>reverseOrder()*/);
                        saveDataIntoTable(holder);
                        //SharedPreferencesApp.getInstance().saveLastLoginTime(Utils.getCurrTime());
                        initRecyclerView();
                    } else {
                        Toast.makeText(getContext(), R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
                    }
                    Utils.dismissProgressBar();
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    Utils.dismissProgressBar();
                }
            });

        } else {
            initRecyclerView();
        }
    }


    private void saveDataIntoTable(TableTimeTableDetailsDataModel holder) {
        try {
            TableTimeTableDetails table = new TableTimeTableDetails();
            table.openDB(getContext());
            table.insert(holder.getMessageBody());
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from saveDataIntoTable");
        }
    }


    private void navigateToNextPage(Class mClass) {
        Intent i = new Intent(getActivity(), mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }


}
