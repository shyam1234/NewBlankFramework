package com.stpl.edurp.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.stpl.edurp.activities.NewsDetails;
import com.stpl.edurp.activities.ResultDetailActivity;
import com.stpl.edurp.adapters.NoticeboardAdapter;
import com.stpl.edurp.constant.Constant;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableNewsMaster;
import com.stpl.edurp.database.TableNoticeBoard;
import com.stpl.edurp.database.TableStudentOverallFeeSummary;
import com.stpl.edurp.database.TableStudentOverallResultSummary;
import com.stpl.edurp.models.GetMobileMenuDataModel;
import com.stpl.edurp.models.LoginDataModel;
import com.stpl.edurp.models.ModelFactory;
import com.stpl.edurp.models.TableFeeMasterDataModel;
import com.stpl.edurp.models.TableNewsMasterDataModel;
import com.stpl.edurp.models.TableResultMasterDataModel;
import com.stpl.edurp.network.IWSRequest;
import com.stpl.edurp.network.WSRequest;
import com.stpl.edurp.parser.ParseResponse;
import com.stpl.edurp.utils.AppLog;
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
public class NoticeboardFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "NoticeboardFragment";
    private RecyclerView mRecycleViewNews;
    //private ArrayList<TableNoticeBoardDataModel> mNoticeboardList;
    private NoticeboardAdapter mNoticeboardAdapter;
    private ArrayList<Object> mCommonList;
    private TextView mTextViewTitle;
    private String mNotificationResp;

    public NoticeboardFragment() {
        AppLog.log(TAG, "NoticeboardFragment");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        AppLog.log(TAG, "onStart");
    }


    private void init() {
        //mNoticeboardList = new ArrayList<TableNoticeBoardDataModel>();
        mCommonList = new ArrayList<Object>();
        if(getArguments()!=null){
            mNotificationResp = (String)getArguments().getSerializable(WSContant.TAG_NOTI_RESP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_news, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //AppLog.log(TAG, "onActivityCreated");
        initView();
        if(mNotificationResp!=null){
            AppLog.log(TAG,"bindResponse ");
            bindResponse(mNotificationResp);
        }else{
            AppLog.log(TAG,"fetchDataFromServer ");
            fetchDataFromServer();
        }

//        DashboardActivity.mHandler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 1:
//                      //  Toast.makeText(getContext(), "student id : " + UserInfo.studentId, Toast.LENGTH_SHORT).show();
//                        DashboardActivity.mHandler.removeMessages(1);
//                        initView();
//                        fetchDataFromServer();
//                        return true;
//                }
//                return false;
//            }
//        });
    }

    private void initView() {
        mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_noticeboard);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetUILImage.getInstance().setCircleImage(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        mRecycleViewNews = (RecyclerView) getView().findViewById(R.id.recyclerview_news);
        //------------------------------------
        //initRecyclerView();
        setLangSelection();

    }

    private void initRecyclerView() {
        // mRecycleViewNews = (RecyclerView) getView().findViewById(R.id.recyclerview_news);
        mRecycleViewNews.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewNews.setLayoutManager(manager);
        mNoticeboardAdapter = new NoticeboardAdapter(getContext(), mCommonList/*mNoticeboardList*/, this);
        mRecycleViewNews.setAdapter(mNoticeboardAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserInfo.menuCode = Constant.TAG_NOTICEBOARD;
        readFromTable();
        initRecyclerView();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
                break;
            case R.id.lin_noticeboard_row_activity_fee_row_holder:
                UserInfo.menuCode = Constant.TAG_FEE;
                int position = (Integer) view.getTag();
                Utils.navigateFragmentMenu(getFragmentManager(), new FeeFragment(), FeeFragment.TAG);

                break;
            case R.id.lin_noticeboard_row_fee_holder:
                UserInfo.menuCode = Constant.TAG_FEE;
                int position1 = (Integer) view.getTag();
                Utils.navigateFragmentMenu(getFragmentManager(), new FeeFragment(), FeeFragment.TAG);
                break;
            case R.id.lin_noticeboard_news_row_holder:
                UserInfo.menuCode = Constant.TAG_NEWS;
                int position2 = (Integer) view.getTag();
                Intent i = new Intent(getActivity(), NewsDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle bundle = new Bundle();
                if(mCommonList.get(position2) instanceof TableNewsMasterDataModel){
                    bundle.putInt(Constant.TAG_HOLDER, ((TableNewsMasterDataModel)mCommonList.get(position2)).getReferenceId());
                }
                i.putExtras(bundle);
                startActivity(i);
                //Utils.navigateFragmentMenu(getFragmentManager(), new NewsFragment(), NewsFragment.TAG);
                //Utils.animRightToLeft(getActivity());
                break;
            case R.id.btn_view_pay_now:
                int position3 = (Integer) view.getTag();
                Toast.makeText(getContext(), "coming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_download_details:
                int position4 = (Integer) view.getTag();
                Toast.makeText(getContext(), "coming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_noticeboard_row_result_row_holder:
                UserInfo.menuCode = Constant.TAG_RESULT;
                int position5 = (Integer) view.getTag();
                Intent intent = new Intent(getContext(), ResultDetailActivity.class);
                Bundle bundle1 = new Bundle();
                if(mCommonList.get(position5) instanceof TableResultMasterDataModel) {
                    bundle1.putSerializable(Constant.TAG_HOLDER, ((TableResultMasterDataModel) mCommonList.get(position5)));
                }
                intent.putExtras(bundle1);
                startActivity(intent);
                //Utils.navigateFragmentMenu(getFragmentManager(), new ResultFragment(), ResultFragment.TAG);
                break;

               /*
                switch (mCommonList.get(position).getMenuCode()) {
                    case Constant.TAG_FEE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new FeeFragment(), FeeFragment.TAG);
                        break;
                    case Constant.TAG_ATTENDANCE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new AttendanceFragment(), AttendanceFragment.TAG);
                        break;
                    case Constant.TAG_HOMEWORK:
                        Utils.navigateFragmentMenu(getFragmentManager(), new HomeworkFragment(), HomeworkFragment.TAG);
                        break;
                    case Constant.TAG_DIARY:
                        Utils.navigateFragmentMenu(getFragmentManager(), new DiaryFragment(), DiaryFragment.TAG);
                        break;
                    case Constant.TAG_MESSAGE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new MessageFragment(), MessageFragment.TAG);
                        break;
                    case Constant.TAG_EVENTS:
                        Utils.navigateFragmentMenu(getFragmentManager(), new EventsFragment(), EventsFragment.TAG);
                        break;
                    case Constant.TAG_GALLERY:
                        Utils.navigateFragmentMenu(getFragmentManager(), new GalleryFragment(), GalleryFragment.TAG);
                        break;
                    case Constant.TAG_FEEDBACK:
                        Utils.navigateFragmentMenu(getFragmentManager(), new FeedbackFragment(), FeedbackFragment.TAG);
                        break;
                    case Constant.TAG_NEWS:
                        Utils.navigateFragmentMenu(getFragmentManager(), new NewsFragment(), NewsFragment.TAG);
                        break;
                    case Constant.TAG_TIMETABLE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new TimeTableFragment(), TimeTableFragment.TAG);
                        break;
                    case Constant.TAG_RESULT:
                        Utils.navigateFragmentMenu(getFragmentManager(), new ResultFragment(), ResultFragment.TAG);
                        break;
                }*/

        }
    }


    private void fetchDataFromServer() {
        if (InternetManager.isInternetConnected(getContext())) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            // header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTimeForNews());
            //header.put(WSContant.TAG_NEW, Utils.getCurrTime());
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
            body.put(WSContant.TAG_PARENTID, "" + UserInfo.parentId);
            body.put(WSContant.TAG_USERID, "" + UserInfo.studentId);
            body.put(WSContant.TAG_USERTYPE, "" + UserInfo.currUserType);
            body.put(WSContant.TAG_REFERENCEDATE, "" + UserInfo.timeTableRefDate);
            body.put(WSContant.TAG_LASTRETRIEVED, "" + UserInfo.timeTableRefDate);//SharedPreferencesApp.getLastGetMobileMenuTime());
            Utils.showProgressBar(getContext());
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEMENU, header, body, WSContant.TAG_NEWS, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                   bindResponse(response);
                }


                @Override
                public void onErrorResponse(VolleyError response) {
                    Utils.dismissProgressBar();
                }
            });
        } else {
            readFromTable();
            initRecyclerView();
        }

    }

    private void bindResponse(String response) {
        ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_GETMOBILEMENU);
        GetMobileMenuDataModel holder = ((GetMobileMenuDataModel) obj.getModel());
        bindData(holder);
        Utils.dismissProgressBar();
        AppLog.log(TAG, "fetchDataFromServe1r3333 " + mCommonList.size());
    }


    private void bindData(GetMobileMenuDataModel holder) {
        if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
            //mCommonList  = holder.getMessageBody().getNoticeBoardMenuList();
            //Collections.sort(mNoticeboardList,Collections.<TableNoticeBoardDataModel>reverseOrder());
            saveDataIntoTable(holder);
            readFromTable();
            SharedPreferencesApp.getInstance().saveGetMobileMenuTime(Utils.getCurrTime());
            initRecyclerView();
        } else {
            Toast.makeText(getContext(), R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
        }
    }

    private void readFromTable() {
        mCommonList.clear();
        TableNewsMaster table1 = new TableNewsMaster();
        table1.openDB(getContext());
        for (TableNewsMasterDataModel model : table1.getDataByStudent(UserInfo.studentId)) {
            mCommonList.add(model);
        }

        table1.closeDB();

        TableStudentOverallResultSummary table2 = new TableStudentOverallResultSummary();
        table2.openDB(getContext());
        for (TableResultMasterDataModel model123 : table2.getData(UserInfo.parentId, UserInfo.studentId)) {
            mCommonList.add(model123);
            AppLog.log(TAG, "getData+++ " + mCommonList.size() + " model123 " + model123);
        }
        table2.closeDB();

        TableStudentOverallFeeSummary table3 = new TableStudentOverallFeeSummary();
        table3.openDB(getContext());
        for (TableFeeMasterDataModel model3 : table3.getData(UserInfo.parentId, UserInfo.studentId)) {
            mCommonList.add(model3);
        }
        table3.closeDB();
    }

    private void saveDataIntoTable(GetMobileMenuDataModel holder) {
        try {
            //-------------------------------------------------------------
            TableNoticeBoard table = new TableNoticeBoard();
            table.openDB(getContext());
            table.insert(holder.getMessageBody().getNoticeBoardMenuList());
            table.closeDB();
            //-------------------------------------------------------------
            TableNewsMaster table1 = new TableNewsMaster();
            table1.openDB(getContext());
            table1.insert(holder.getMessageBody().getNewsMasterMenuList());
            table1.closeDB();

            TableStudentOverallResultSummary table2 = new TableStudentOverallResultSummary();
            table2.openDB(getContext());
            table2.insert(holder.getMessageBody().getStudentOverallResultSummary());
            table2.closeDB();

            TableStudentOverallFeeSummary table3 = new TableStudentOverallFeeSummary();
            table3.openDB(getContext());
            table3.insert(holder.getMessageBody().getStudentOverallFeeSummary());
            table3.closeDB();
            //-------------------------------------------------------------
        } catch (Exception e) {
            AppLog.errLog(TAG, " saveDataIntoTable " + e.getMessage());
        }
    }


    private void navigateToNextPage(Class<?> page) {
        Intent i = new Intent(getActivity(), page);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        //bundle.putSerializable(Constant.TAG_HOLDER, mNoticeboardList.get(position));
        i.putExtras(bundle);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }


    public void setLangSelection() {
        Utils.langConversion(getContext(), mTextViewTitle, new String[]{WSContant.TAG_LANG_NOTICEBOARD}, getString(R.string.tab_noticeboard), UserInfo.lang_pref);
    }
}
