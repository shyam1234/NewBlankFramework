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
import com.stpl.edurp.adapters.PayslipFacultyAdapter;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.interfaces.ICallBack;
import com.stpl.edurp.models.LoginDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.PayslipDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.DownloadFileAsync;
import com.stpl.edurp.utils.FileManager;
import com.stpl.edurp.utils.GetUILImage;
import com.stpl.edurp.utils.InternetManager;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 24-12-2016.
 */

public class PayslipFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "PayslipFragment";
    private RecyclerView mRecyclerView;
    private PayslipFacultyAdapter mPayslipAdapter;
    private ArrayList<PayslipDataModel.PayslipArray> mPayslipDataModel;
    private int mFinancialYearMonthId;

    public PayslipFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mPayslipDataModel = new ArrayList<>();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_payslip, null);
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
                        //             fetchDataFromServer();
                        return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        //--header----------------------------------
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.title_payslip);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.INVISIBLE);
        GetUILImage.getInstance().setCircleImage(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        //------------------------------------
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview_payslip);
        RecyclerView.LayoutManager lLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(lLayoutManager);
        mPayslipAdapter = new PayslipFacultyAdapter(getContext(), mPayslipDataModel, this);
        mRecyclerView.setAdapter(mPayslipAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgview_payslip_download:
                //download payslip
                int position = (int) view.getTag();
                downloadPDF(position);
                break;
            case R.id.rel_payslip_holder:
                //if download payslip then show
                int positn = (int) view.getTag();
                showDownloadedPDF(positn);
                break;
            case R.id.imgview_payslip_downloaded:
                int position1 = (int) view.getTag();
                deletePDF(position1);
                break;
        }
    }

    private void deletePDF(int position1) {
        mFinancialYearMonthId = mPayslipDataModel.get(position1).getFinancialYearMonthId();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.showProgressBar(getContext());
                FileManager.deleteDownloadFile(getActivity(), WSContant.DOWNLOAD_FOLDER, "" + mFinancialYearMonthId + ".pdf", new ICallBack() {
                    @Override
                    public void callBack() {
                        Utils.dismissProgressBar();
                        mPayslipAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }

    private void showDownloadedPDF(int positn) {
        mFinancialYearMonthId = mPayslipDataModel.get(positn).getFinancialYearMonthId();
        if (FileManager.isFileDownloaded(getActivity(), WSContant.DOWNLOAD_FOLDER, mFinancialYearMonthId + ".pdf")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FileManager.showDownloadFile(getActivity(), WSContant.DOWNLOAD_FOLDER, "" + mFinancialYearMonthId + ".pdf");
                    Utils.dismissProgressBar();
                }
            });
        }
    }

    private void downloadPDF(int position) {
        mFinancialYearMonthId = mPayslipDataModel.get(position).getFinancialYearMonthId();
        if (!FileManager.isFileDownloaded(getActivity(), WSContant.DOWNLOAD_FOLDER, mFinancialYearMonthId + ".pdf")) {
            if (InternetManager.isInternetConnected(getActivity())) {
                new DownloadFileAsync(getActivity(), WSContant.DOWNLOAD_FOLDER, new ICallBack() {
                    @Override
                    public void callBack() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPayslipAdapter.notifyDataSetChanged();
                                FileManager.showDownloadFile(getActivity(), WSContant.DOWNLOAD_FOLDER, mFinancialYearMonthId + ".pdf");
                            }
                        });
                    }
                    //?EmployeeId=319&financialyearmonthId=457&Language="B"
                }).execute(WSContant.URL_PRINTEMPPAYSLIP, WSContant.TAG_eMPLOYEEID + "=" + UserInfo.studentId/*UserInfo.userId */ + "&" + WSContant.TAG_FINANCIALYEARMONTHID + "=" + mFinancialYearMonthId + "&" + WSContant.TAG_LANGUAGE + "=" + UserInfo.lang_pref, "" + mFinancialYearMonthId);
            }
        } else {
            showDownloadedPDF(position);
        }
    }


    private void fetchDataFromServer() {
        if (InternetManager.isInternetConnected(getContext())) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_EMPLOYEEID, "" + UserInfo.studentId);
            Utils.showProgressBar(getContext());

            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETPAYSLIPMONTHS, header, body, WSContant.TAG_FACULY_PAYSLIP, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    mPayslipDataModel.clear();
                    ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_PAYSLIPFACULTYDM);
                    PayslipDataModel holder = ((PayslipDataModel) obj.getModel());
                    if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
                        mPayslipDataModel = holder.getMessageBody();
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
            //initRecyclerView();
        }
    }

    private void navigateToNextPage(Class mClass) {
        Intent i = new Intent(getActivity(), mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }


}
