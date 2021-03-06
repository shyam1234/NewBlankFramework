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
import com.stpl.edurp.adapters.TaxReportsAdapter;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.interfaces.ICallBack;
import com.stpl.edurp.models.LoginDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.TaxReportsDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.DownloadFileAsync;
import com.stpl.edurp.utils.FileManager;
import com.stpl.edurp.utils.GetUILImage;
import com.stpl.edurp.utils.InternetManager;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 24-12-2016.
 */

public class TaxReportsFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "TaxReportsFragment";
    public static final String TAG_COMPUTATION = "_computation";
    private RecyclerView mRecyclerView;
    private TaxReportsAdapter mPayslipAdapter;
    private ArrayList<TaxReportsDataModel.TaxReportsArray> mTaxReportDataModel;
    private int mFinancialYearId;

    public TaxReportsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mTaxReportDataModel = new ArrayList<>();
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
        mTextViewTitle.setText(R.string.title_tax);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
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
        mPayslipAdapter = new TaxReportsAdapter(getContext(), mTaxReportDataModel, this);
        mRecyclerView.setAdapter(mPayslipAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgview_download_form_16:
                //download payslip
                int position = (int) view.getTag();
                downloadPDFForForm16(position);
                break;
            case R.id.imgview_taxreports_form_downloaded:
                int position1 = (int) view.getTag();
                deletePDF(position1, "" + mTaxReportDataModel.get(position1).getFinancialYearId());
                break;
            case R.id.textview_download_form_16:
                //if download payslip then show
                int positn = (int) view.getTag();
                showDownloadedForm16PDF(positn);
                break;

            case R.id.imgview_download_computationsheet:
                //download payslip
                int position4 = (int) view.getTag();
                downloadPDFForComputation(position4);
                break;
            case R.id.imgview_taxreports_downloaded1:
                int position5 = (int) view.getTag();
                deletePDF(position5, "" + mTaxReportDataModel.get(position5).getFinancialYearId() + TAG_COMPUTATION);
                break;
            case R.id.textview_download_computationsheet:
                //if download payslip then show
                int positn6 = (int) view.getTag();
                showDownloadedComputation(positn6);
                break;

        }
    }

    private void deletePDF(int positn, final String key) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.showProgressBar(getContext());
                FileManager.deleteDownloadFile(getActivity(), WSContant.DOWNLOAD_FOLDER, key + ".pdf", new ICallBack() {
                    @Override
                    public void callBack() {
                        Utils.dismissProgressBar();
                        mPayslipAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }

    private void showDownloadedForm16PDF(int positn) {
        mFinancialYearId = mTaxReportDataModel.get(positn).getFinancialYearId();
        if (FileManager.isFileDownloaded(getActivity(), WSContant.DOWNLOAD_FOLDER, mFinancialYearId + ".pdf")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FileManager.showDownloadFile(getActivity(), WSContant.DOWNLOAD_FOLDER, "" + mFinancialYearId + ".pdf");
                    Utils.dismissProgressBar();

                }
            });
        }
    }

    private void downloadPDFForForm16(int position) {
        mFinancialYearId = mTaxReportDataModel.get(position).getFinancialYearId();
        if (!FileManager.isFileDownloaded(getActivity(), WSContant.DOWNLOAD_FOLDER, mFinancialYearId + ".pdf")) {
            if(InternetManager.isInternetConnected(getContext())) {
                new DownloadFileAsync(getActivity(), WSContant.DOWNLOAD_FOLDER,true,  new ICallBack() {
                    @Override
                    public void callBack() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPayslipAdapter.notifyDataSetChanged();
                                FileManager.showDownloadFile(getActivity(), WSContant.DOWNLOAD_FOLDER, mFinancialYearId + ".pdf");

                            }
                        });
                    }
                }).execute(WSContant.URL_PRINTTAXREPORT, WSContant.TAG_EMPLOYEEID + "=" + UserInfo.userId + "&" + WSContant.TAG_FINANCIALYEARID + "=" + mFinancialYearId + "&" + WSContant.TAG_LANGUAGE + "=" + UserInfo.lang_pref, "" + mFinancialYearId);
            }
        } else {
            showDownloadedForm16PDF(position);
        }
    }


    private void fetchDataFromServer() {
        if (InternetManager.isInternetConnected(getContext())) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            /*header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);*/
            header.put(WSContant.TAG_NEW, Utils.getCurrTime());
            header.put(WSContant.TAG_DATELASTRETRIEVED, SharedPreferencesApp.getInstance().getLastRetrieveTime(TAG));

            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            // body.put(WSContant.TAG_EMPLOYEEID, "" + UserInfo.studentId);
            body.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            Utils.showProgressBar(getContext());

            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETTAXFINANCIALYEAR, header, body, WSContant.TAG_TAXREPORT, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    mTaxReportDataModel.clear();
                    ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_GETTAXFINANCIALYEAR);
                    TaxReportsDataModel holder = ((TaxReportsDataModel) obj.getModel());
                    if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
                        mTaxReportDataModel = holder.getMessageBody();
                        initRecyclerView();
                        SharedPreferencesApp.getInstance().setLastRetrieveTime(TAG, Utils.getCurrTime());
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

    //for Computation sheet
    private void downloadPDFForComputation(int position) {
        mFinancialYearId = mTaxReportDataModel.get(position).getFinancialYearId();
        if (!FileManager.isFileDownloaded(getActivity(), WSContant.DOWNLOAD_FOLDER, mFinancialYearId + TAG_COMPUTATION + ".pdf")) {
            if (InternetManager.isInternetConnected(getContext())) {
                new DownloadFileAsync(getActivity(), WSContant.DOWNLOAD_FOLDER, true, new ICallBack() {
                    @Override
                    public void callBack() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPayslipAdapter.notifyDataSetChanged();
                                FileManager.showDownloadFile(getActivity(), WSContant.DOWNLOAD_FOLDER, mFinancialYearId + TAG_COMPUTATION + ".pdf");

                            }
                        });
                    }
                }).execute(WSContant.URL_PRINTCOMPUTATIONSHEET, WSContant.TAG_EMPLOYEEID + "=" + UserInfo.studentId + "&" + WSContant.TAG_FINANCIALYEARID + "=" + mFinancialYearId + "&" + WSContant.TAG_LANGUAGE + "=" + UserInfo.lang_pref, "" + mFinancialYearId + TAG_COMPUTATION);
            }
        } else {
            showDownloadedComputation(position);
        }
    }

    private void showDownloadedComputation(int positn) {
        mFinancialYearId = mTaxReportDataModel.get(positn).getFinancialYearId();
        if (FileManager.isFileDownloaded(getActivity(), WSContant.DOWNLOAD_FOLDER, mFinancialYearId + TAG_COMPUTATION + ".pdf")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FileManager.showDownloadFile(getActivity(), WSContant.DOWNLOAD_FOLDER, "" + mFinancialYearId + TAG_COMPUTATION + ".pdf");
                    Utils.dismissProgressBar();

                }
            });
        }
    }
}
