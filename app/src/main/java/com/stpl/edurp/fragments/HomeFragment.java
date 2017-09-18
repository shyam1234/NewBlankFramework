package com.stpl.edurp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stpl.edurp.R;
import com.stpl.edurp.activities.DashboardActivity;
import com.stpl.edurp.adapters.HomeAdapter;
import com.stpl.edurp.constant.Constant;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableParentStudentMenuDetails;
import com.stpl.edurp.models.DashboardCellDataModel;
import com.stpl.edurp.notification.EduRPNotificationReceivedHandler;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.ExpandableHeightGridView;
import com.stpl.edurp.utils.GetUILImage;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 24-12-2016.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "HomeFramgent";
    public static HashMap<String, Integer> mMenuColor = new HashMap<>();
    public static HashMap<String, Integer> mMenuImage = new HashMap<>();

    static {
//        mMenuColor.put(Constant.TAG_NOTICEBOARD, R.drawable.menu_light_green);
//        mMenuColor.put(Constant.TAG_ATTENDANCE, R.drawable.menu_green);
//        mMenuColor.put(Constant.TAG_HOMEWORK, R.drawable.menu_light_parrot);
//        mMenuColor.put(Constant.TAG_DIARY, R.drawable.menu_dark_yellow);
//        mMenuColor.put(Constant.TAG_MESSAGE, R.drawable.menu_dark_vallet);
//        mMenuColor.put(Constant.TAG_EVENTS, R.drawable.menu_light_vallet);
//        mMenuColor.put(Constant.TAG_GALLERY, R.drawable.menu_dark_blue);
//        mMenuColor.put(Constant.TAG_FEEDBACK, R.drawable.menu_sky_blue);
//        mMenuColor.put(Constant.TAG_FEE, R.drawable.menu_dark_pink);
//        mMenuColor.put(Constant.TAG_NEWS, R.drawable.menu_light_oran);
//        mMenuColor.put(Constant.TAG_TIMETABLE, R.drawable.menu_dark_pink);
//        mMenuColor.put(Constant.TAG_RESULT, R.drawable.menu_light_blue);
//        mMenuColor.put(Constant.TAG_TAX_REPORTS, R.drawable.menu_dark_vallet);
//        mMenuColor.put(Constant.TAG_PAYSLIP, R.drawable.menu_sky_blue);
//        mMenuColor.put(Constant.TAG_TRANSPORT, R.drawable.menu_light_mayrun);

        mMenuColor.put(Constant.TAG_NOTICEBOARD, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_ATTENDANCE, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_HOMEWORK, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_DIARY, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_MESSAGE, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_EVENTS, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_GALLERY, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_FEEDBACK, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_FEE, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_NEWS, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_TIMETABLE, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_RESULT, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_TAX_REPORTS, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_PAYSLIP, R.drawable.menu_transparent);
        mMenuColor.put(Constant.TAG_TRANSPORT, R.drawable.menu_transparent);

        ///////////////////////////////////////////////////////////////////
        mMenuImage.put(Constant.TAG_NOTICEBOARD, R.drawable.noticeboard);
        mMenuImage.put(Constant.TAG_ATTENDANCE, R.drawable.attendance);
        mMenuImage.put(Constant.TAG_HOMEWORK, R.drawable.homework);
        mMenuImage.put(Constant.TAG_DIARY, R.drawable.diary);
        mMenuImage.put(Constant.TAG_MESSAGE, R.drawable.messages);
        mMenuImage.put(Constant.TAG_EVENTS, R.drawable.events);
        mMenuImage.put(Constant.TAG_GALLERY, R.drawable.gallery);
        mMenuImage.put(Constant.TAG_FEEDBACK, R.drawable.feedback);
        mMenuImage.put(Constant.TAG_FEE, R.drawable.fee);
        mMenuImage.put(Constant.TAG_NEWS, R.drawable.dashboard_news);
        mMenuImage.put(Constant.TAG_TIMETABLE, R.drawable.dashboard_time_table);
        mMenuImage.put(Constant.TAG_RESULT, R.drawable.dashboard_results);
        mMenuImage.put(Constant.TAG_TAX_REPORTS, R.drawable.tax_reports);
        mMenuImage.put(Constant.TAG_PAYSLIP, R.drawable.payslip);
        mMenuImage.put(Constant.TAG_TRANSPORT, R.drawable.transport_manag);
    }

//    public static int[] mMenuImage = new int[]{R.drawable.noticeboard, R.drawable.attendance, R.drawable.homework,
//            R.drawable.diary, R.drawable.messages, R.drawable.events,
//            R.drawable.gallery, R.drawable.feedback, R.drawable.fee,
//            R.drawable.dashboard_time_table, R.drawable.dashboard_news, R.drawable.dashboard_results,
//            R.drawable.payslip, R.drawable.tax_reports, R.drawable.transparent};


//    public static int[] mMenuColor = new int[]{R.color.colorGreen, R.color.colorDarkYellow, R.color.colorLightVallet,
//            R.color.colorSkyBlue, R.color.colorDarkPink, R.color.colorDarkblue,
//            R.color.colorLightParrot, R.color.colorDarkVal, R.color.colorLightOran,
//            R.color.colorLeaf, R.color.colorBlue, R.color.colorLighterMayrun,
//            R.color.colorDarkPink, R.color.colorLightParrot, R.color.colorDarkVal};

    private ExpandableHeightGridView mGridViewCell;
    private HomeAdapter mAdapter;
    private ArrayList<DashboardCellDataModel> mCellList;
    // private ArrayList<TableStudentDetailsDataModel> mUniver;
    private ImageView mImageViewUnivercityLogo;
    private TextView mTextViewUnivercityText;
    private LinearLayout mLinearHolder;
    private TextView mTextViewTitle;
    private ImageView mImgProfile;


    public HomeFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        AppLog.log("HomeFragment", "onCreate");

    }

    private void init() {
        mCellList = new ArrayList<>();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppLog.log("HomeFragment ", "bindDataWithParentStudentMenuDetailsDataModel: : onActivityCreated");
        initView();
        fetchDataFromTable();
        setLangSelection();
        AppLog.log(TAG, "UserInfo.normal -++ :" + UserInfo.studentId);
        DashboardActivity.mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        initView();
                        fetchDataFromTable();
                        setLangSelection();
                        DashboardActivity.mHandler.removeMessages(1);
                        AppLog.log(TAG, "UserInfo.handlerrrr  :" + UserInfo.studentId);
                        return true;
                }
                return false;
            }
        });
        if (EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED);
                UserInfo.menuCode = jsonObject.optString(WSContant.TAG_MENUCODE);
            } catch (JSONException e) {
                AppLog.networkLog(TAG, "onActivityCreated " + e.getMessage());
            }
            navigateToRespModule(UserInfo.menuCode);
            EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED = null;
        }
    }

    private void fetchDataFromTable() {
        final TableParentStudentMenuDetails table = new TableParentStudentMenuDetails();
        table.openDB(getContext());
        mCellList = table.getHomeFragmentData(UserInfo.parentId, UserInfo.studentId);
        table.closeDB();
        AppLog.log("HomeFragment ", "mCellList mCellList " + mCellList.size());
        mLinearHolder.setVisibility(View.GONE);
        if (mCellList.size() > 0) {
            mTextViewUnivercityText.setText(mCellList.get(0).getUniversity_name());
        }
        AppLog.log(TAG, "UserInfo.studentId :" + UserInfo.studentId);
    }


    private void initView() {
        if (getView() == null) {
            return;
        }

        mLinearHolder = (LinearLayout) getView().findViewById(R.id.linearlayout);
        mLinearHolder.setVisibility(View.GONE);
        mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_home);
        mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        Utils.setBounceAni(getContext(), mImgProfile);
        //----------------------------------------------------------------------
        final TableParentStudentMenuDetails table = new TableParentStudentMenuDetails();
        table.openDB(getContext());
        mCellList = table.getHomeFragmentData(UserInfo.parentId, UserInfo.studentId);
        table.closeDB();
        AppLog.log("HomeFragment ", "mCellList mCellList " + mCellList.size());
        //----------------------------------------------------------------------
        mLinearHolder.setVisibility(View.GONE);
        mGridViewCell = (ExpandableHeightGridView) getView().findViewById(R.id.gridview_dashboard);
        mAdapter = new HomeAdapter(getContext(), mCellList, this);
        mGridViewCell.setAdapter(mAdapter);
        mGridViewCell.setExpanded(true);
        mImageViewUnivercityLogo = (ImageView) getView().findViewById(R.id.imgview_uni_logo);
        if (mCellList.size() > 0) {
            UserInfo.selectedStudentImageURL = mCellList.get(0).getStudentProfileImage();
            GetUILImage.getInstance().setCircleImage(getContext(), mCellList.get(0).getUniversity_url(), mImageViewUnivercityLogo, R.drawable.university_placeholder);
            GetUILImage.getInstance().setCircleImage(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        }
        //GetUILImage.getInstance(getContext()).setImageByURL(UserInfo.university_logo_url, mImageViewUnivercityLogo, R.drawable.logo, R.drawable.loader);
        mTextViewUnivercityText = (TextView) getView().findViewById(R.id.textview_uni_header_name);
    }


    public void setLangSelection() {
        if (getContext() != null) {
            mTextViewTitle.setText(Utils.getLangConversion(WSContant.TAG_LANG_HOME, getString(R.string.tab_home), UserInfo.lang_pref));
            ((TextView) getView().findViewById(R.id.textview_welcome_to)).setText(Utils.getLangConversion(WSContant.TAG_LANG_WELCOME, getString(R.string.welcome_to), UserInfo.lang_pref));

//            Utils.langConversion(getContext(), mTextViewTitle, WSContant.TAG_LANG_HOME, getString(R.string.tab_home), UserInfo.lang_pref);
//            Utils.langConversion(getContext(), ((TextView) getView().findViewById(R.id.textview_welcome_to)), WSContant.TAG_LANG_WELCOME, getString(R.string.welcome_to), UserInfo.lang_pref);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_dashboard_cell_bg_holder:
                String menu_code = (String) v.getTag();
                AppLog.log("HomeFragment : onItemClick menu name: ", menu_code);
                UserInfo.menuCode = menu_code.trim();
                navigateToRespModule(UserInfo.menuCode);
                break;
        }
    }

    private void navigateToRespModule(String menuCode) {
        Bundle bundle = null;
        switch (menuCode) {
            case Constant.TAG_NOTICEBOARD:
                NoticeboardFragment fragment = new NoticeboardFragment();
                bundle = new Bundle();
                bundle.putString(WSContant.TAG_NOTI_RESP,EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED);
                if(EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED!=null)
                fragment.setArguments(bundle);
                Utils.navigateFragmentMenu(getFragmentManager(), fragment, NoticeboardFragment.TAG);
                break;
            case Constant.TAG_ATTENDANCE:
                AttendanceFragment fragment1 = new AttendanceFragment();
                bundle = new Bundle();
                bundle.putString(WSContant.TAG_NOTI_RESP,EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED);
                if(EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED!=null)
                    fragment1.setArguments(bundle);
                Utils.navigateFragmentMenu(getFragmentManager(), fragment1, AttendanceFragment.TAG);
                break;
            case Constant.TAG_NEWS:
                NewsFragment fragment2 = new NewsFragment();
                bundle = new Bundle();
                bundle.putString(WSContant.TAG_NOTI_RESP,EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED);
                if(EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED!=null)
                    fragment2.setArguments(bundle);
                Utils.navigateFragmentMenu(getFragmentManager(), fragment2, NewsFragment.TAG);
                break;
            case Constant.TAG_TIMETABLE:
                TimeTableFragment fragment3 = new TimeTableFragment();
                bundle = new Bundle();
                bundle.putString(WSContant.TAG_NOTI_RESP,EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED);
                if(EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED!=null)
                    fragment3.setArguments(bundle);
                Utils.navigateFragmentMenu(getFragmentManager(),fragment3, TimeTableFragment.TAG);
                break;
            case Constant.TAG_RESULT:
                ResultFragment fragment4 = new ResultFragment();
                bundle = new Bundle();
                bundle.putString(WSContant.TAG_NOTI_RESP,EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED);
                if(EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED!=null)
                    fragment4.setArguments(bundle);
                Utils.navigateFragmentMenu(getFragmentManager(), fragment4, ResultFragment.TAG);
                break;
            case Constant.TAG_FEE:
                FeeFragment fragment5 = new FeeFragment();
                bundle = new Bundle();
                bundle.putString(WSContant.TAG_NOTI_RESP,EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED);
                if(EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED!=null)
                    fragment5.setArguments(bundle);
                Utils.navigateFragmentMenu(getFragmentManager(), fragment5, FeeFragment.TAG);
                break;
            case Constant.TAG_GALLERY:
                //Utils.navigateFragmentMenu(getFragmentManager(), new GalleryFragment(), GalleryFragment.TAG);
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case Constant.TAG_FEEDBACK:
                //Utils.navigateFragmentMenu(getFragmentManager(), new FeedbackFragment(), FeedbackFragment.TAG);
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case Constant.TAG_MESSAGE:
                //Utils.navigateFragmentMenu(getFragmentManager(), new MessageFragment(), MessageFragment.TAG);
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case Constant.TAG_PAYSLIP:
                PayslipFragment fragment6 = new PayslipFragment();
                bundle = new Bundle();
                bundle.putString(WSContant.TAG_NOTI_RESP,EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED);
                if(EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED!=null)
                    fragment6.setArguments(bundle);
                Utils.navigateFragmentMenu(getFragmentManager(), fragment6, PayslipFragment.TAG);
                //Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case Constant.TAG_TAX_REPORTS:
                TaxReportsFragment fragment7 = new TaxReportsFragment();
                bundle = new Bundle();
                bundle.putString(WSContant.TAG_NOTI_RESP,EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED);
                if(EduRPNotificationReceivedHandler.NOTIFICATION_RECEIVED!=null)
                    fragment7.setArguments(bundle);
                Utils.navigateFragmentMenu(getFragmentManager(),fragment7, FeedbackFragment.TAG);
                //Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case Constant.TAG_TRANSPORT:
                //Utils.navigateFragmentMenu(getFragmentManager(), new FeedbackFragment(), FeedbackFragment.TAG);
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case Constant.TAG_HOMEWORK:
                //did not get design
                //Utils.navigateFragmentMenu(getFragmentManager(), new HomeworkFragment(), HomeworkFragment.TAG);
                //Toast.makeText(getContext(), "Working on it...", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case Constant.TAG_DIARY:
               // Utils.navigateFragmentMenu(getFragmentManager(), new DiaryFragment(), DiaryFragment.TAG);
               // Toast.makeText(getContext(), "Working on it...", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            case Constant.TAG_EVENTS:
               //  Utils.navigateFragmentMenu(getFragmentManager(), new EventsFragment(), EventsFragment.TAG);
               // Toast.makeText(getContext(), "Working on it...", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
