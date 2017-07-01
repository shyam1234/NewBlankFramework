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
import com.stpl.edurp.activities.DashboardActivity;
import com.stpl.edurp.adapters.FeeAdapter;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableStudentOverallFeeSummary;
import com.stpl.edurp.interfaces.ICallBack;
import com.stpl.edurp.models.GetMobileMenuDataModel;
import com.stpl.edurp.models.LoginDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.TableFeeMasterDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.DownloadFileAsync;
import com.stpl.edurp.utils.FileManager;
import com.stpl.edurp.utils.GetUILImage;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 24-12-2016.
 */

public class FeeFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "FeeFragment";
    private RecyclerView mRecycleViewFee;
    private FeeAdapter mFeeAdapter;
    private ArrayList<TableFeeMasterDataModel> mFeeList;

    public FeeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mFeeList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_noticeboard, null);
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

    private void initView() {
        //------------------------------------
        View inc = getView().findViewById(R.id.inc_results);
        inc.setVisibility(View.VISIBLE);
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_fee);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetUILImage.getInstance().setCircleImage(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);

        initRecyclerView();

    }


    private void initRecyclerView() {
        mRecycleViewFee = (RecyclerView) getView().findViewById(R.id.recyclerview);
        mRecycleViewFee.setVisibility(View.VISIBLE);
        mRecycleViewFee.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewFee.setLayoutManager(manager);
        mFeeAdapter = new FeeAdapter(getContext(), mFeeList, this);
        mRecycleViewFee.setAdapter(mFeeAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
                break;
            case R.id.lin_noticeboard_row_fee_holder:
                //fee not paid
                break;
            case R.id.lin_noticeboard_row_activity_fee_row_holder:
                //paid
                break;
            case R.id.btn_download_details:
            case R.id.btn_download_details1:
                int posi = (int)view.getTag();
                AppLog.log(TAG, "btn_download_details");
                final String sfAssociationId = ""+mFeeList.get(posi).getReferenceId();//"436";

                if (!FileManager.isFileDownloaded(getActivity(), WSContant.DOWNLOAD_FOLDER,sfAssociationId + ".pdf")) {
                    if(Utils.isInternetConnected(getActivity())) {
                        new DownloadFileAsync(getActivity(), WSContant.DOWNLOAD_FOLDER, new ICallBack() {
                            @Override
                            public void callBack() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FileManager.showDownloadFile(getActivity(), WSContant.DOWNLOAD_FOLDER, sfAssociationId + ".pdf");
                                        mFeeAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }).execute(WSContant.URL_PRINT_RECEIPT, "" + sfAssociationId);
                    }
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FileManager.showDownloadFile(getActivity(), WSContant.DOWNLOAD_FOLDER, "" + sfAssociationId + ".pdf");
                            Utils.dismissProgressBar();
                        }
                    });
                }
                break;
            case R.id.btn_view_pay_now:
                Toast.makeText(getContext(), "Coming", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void fetchDataFromServer() {
        TableStudentOverallFeeSummary table = new TableStudentOverallFeeSummary();
        table.openDB(getContext());
        mFeeList = table.getData(UserInfo.studentId);
        Collections.sort(mFeeList, Collections.<TableFeeMasterDataModel>reverseOrder());
        table.closeDB();
        //----------------------------------------------------------
        if (Utils.isInternetConnected(getContext())) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
            body.put(WSContant.TAG_PARENTID, "" + UserInfo.parentId);
            body.put(WSContant.TAG_USERID, "" + UserInfo.studentId);
            body.put(WSContant.TAG_USERTYPE, "" + UserInfo.currUserType);
            body.put(WSContant.TAG_REFERENCEDATE, "" + UserInfo.timeTableRefDate);
            body.put(WSContant.TAG_LASTRETRIEVED, "" + Utils.getLastRetrivedTimeForNews());
            Utils.showProgressBar(getContext());
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEMENU, header, body, WSContant.TAG_NEWS, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    mFeeList.clear();
                    ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_GETMOBILEMENU);
                    GetMobileMenuDataModel holder = ((GetMobileMenuDataModel) obj.getModel());
                    if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
                        mFeeList = holder.getMessageBody().getStudentOverallFeeSummary();
                        Collections.sort(mFeeList, Collections.<TableFeeMasterDataModel>reverseOrder());
                        saveDataIntoTable(holder);
                        SharedPreferencesApp.getInstance().saveLastLoginTime(Utils.getCurrTime());
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


    private void saveDataIntoTable(GetMobileMenuDataModel holder) {
        try {
            TableStudentOverallFeeSummary table = new TableStudentOverallFeeSummary();
            table.openDB(getContext());
            table.insert(holder.getMessageBody().getStudentOverallFeeSummary());
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
