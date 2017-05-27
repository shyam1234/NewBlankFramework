package com.stpl.edurp.fragments;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.stpl.edurp.R;
import com.stpl.edurp.activities.AttendanceDetailActivity;
import com.stpl.edurp.activities.DashboardActivity;
import com.stpl.edurp.adapters.AttendanceAdapter;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableAttendanceDetails;
import com.stpl.edurp.database.TableCourseMaster;
import com.stpl.edurp.models.GetMobileAttendanceDetailDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.TableAttendanceDetailsDataModel;
import com.stpl.edurp.models.TableCourseMasterDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.GetPicassoImage;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 24-12-2016.
 */

public class AttendanceFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "AttendanceFragment";
    private RecyclerView mRecycleViewAttendance;
    private AttendanceAdapter mAttendanceAdapter;
    private ArrayList<TableCourseMasterDataModel> mStudentDetailList;

    public AttendanceFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mStudentDetailList = new ArrayList<TableCourseMasterDataModel>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_attendance, null);
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
        mTextViewTitle.setText(R.string.tab_attendance);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecycleViewAttendance = (RecyclerView) getView().findViewById(R.id.recyclerview_attendance);
        mRecycleViewAttendance.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewAttendance.setLayoutManager(manager);
        mAttendanceAdapter = new AttendanceAdapter(getContext(), mStudentDetailList, this);
        mRecycleViewAttendance.setAdapter(mAttendanceAdapter);
        //--For Course and Term

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
                break;
            case R.id.lin_holder:
                int position = (Integer) view.getTag();
                navigateToNextPage(AttendanceDetailActivity.class, mStudentDetailList.get(position));
                break;
        }
    }


    private void fetchDataFromServer() {
        TableCourseMaster table = new TableCourseMaster();
        table.openDB(getContext());
        mStudentDetailList = table.getValueByStudent(UserInfo.studentId);
        table.closeDB();
        //----------------------------------------------------------
        if (Utils.isInternetConnected(getContext())) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_DATELASTRETRIEVED, SharedPreferencesApp.getInstance().getLastRetrieveTime(WSContant.TAG_MOBILEATTENDANCEDETAIL));
            header.put(WSContant.TAG_NEW, SharedPreferencesApp.getInstance().getLastRetrieveTime(WSContant.TAG_MOBILEATTENDANCEDETAIL));
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_STUDENTID, "" + UserInfo.studentId);
            Utils.showProgressBar(getContext());
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEATTENDANCEDETAIL, header, body, WSContant.TAG_MOBILEATTENDANCEDETAIL, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    //mAttendanceDetailsList.clear();
                    ParseResponse obj = new ParseResponse(response, GetMobileAttendanceDetailDataModel.class, ModelFactory.MODEL_GETMOBILEATTDANCEDETAIL);
                    GetMobileAttendanceDetailDataModel holder = ((GetMobileAttendanceDetailDataModel) obj.getModel());
                    if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
                        mStudentDetailList = holder.getMessageBody().getStudentDetailList();
                        saveDataIntoTableCourseMaster(mStudentDetailList);
                        saveDataIntoTableAttendanceDetails(holder.getMessageBody().getStudentAttendanceDetailList());
                        SharedPreferencesApp.getInstance().setLastRetrieveTime(WSContant.TAG_MOBILEATTENDANCEDETAIL, Utils.getCurrTime());
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


    private void saveDataIntoTableCourseMaster(ArrayList<TableCourseMasterDataModel> holder) {
        try {
            TableCourseMaster table = new TableCourseMaster();
            table.openDB(getContext());
            table.insert(holder);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from saveDataIntoTableCourseMaster " + e.getMessage());
        }
    }


    private void saveDataIntoTableAttendanceDetails(ArrayList<TableAttendanceDetailsDataModel> list) {
        try {
            TableAttendanceDetails table = new TableAttendanceDetails();
            table.openDB(getContext());
            table.insert(list);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog(TAG, "saveDataIntoTableAttendanceDetails " + e.getMessage());
        }
    }


    private void navigateToNextPage(Class mClass, TableCourseMasterDataModel mStudentDetailList) {
        Intent i = new Intent(getActivity(), mClass);
        //i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WSContant.TAG_NEW, mStudentDetailList);
        i.putExtras(bundle);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }
}
