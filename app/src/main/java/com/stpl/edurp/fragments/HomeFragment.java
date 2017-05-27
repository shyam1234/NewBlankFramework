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
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.ExpandableHeightGridView;
import com.stpl.edurp.utils.GetPicassoImage;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "HomeFramgent";
    public static int[] mMenuColor = new int[]{R.color.colorGreen, R.color.colorDarkYellow, R.color.colorLightVallet,
            R.color.colorSkyBlue, R.color.colorDarkPink, R.color.colorDarkblue,
            R.color.colorLightParrot, R.color.colorDarkVal, R.color.colorLightOran,
            R.color.colorLeaf, R.color.colorBlue, R.color.colorLighterMayrun};
    public static int[] mMenuImage = new int[]{R.drawable.noticeboard, R.drawable.attendance, R.drawable.homework,
            R.drawable.diary, R.drawable.messages, R.drawable.events,
            R.drawable.gallery, R.drawable.feedback, R.drawable.fee,
            R.drawable.dashboard_time_table, R.drawable.dashboard_news, R.drawable.dashboard_results};

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
        AppLog.log(TAG, "UserInfo.normal -++ :" + UserInfo.studentId);;
        DashboardActivity.mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch ((Integer) msg.what) {
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
        Utils.setBounceAni(getContext(),mImgProfile);
        //----------------------------------------------------------------------
        final TableParentStudentMenuDetails table = new TableParentStudentMenuDetails();
        table.openDB(getContext());
        mCellList = table.getHomeFragmentData(UserInfo.parentId, UserInfo.studentId);
        table.closeDB();
        AppLog.log("HomeFragment ", "mCellList mCellList " + mCellList.size());
        //----------------------------------------------------------------------
        mLinearHolder.setVisibility(View.GONE);
        mAdapter = new HomeAdapter(getContext(), mCellList, this);
        mGridViewCell = (ExpandableHeightGridView) getView().findViewById(R.id.gridview_dashboard);
        mGridViewCell.setAdapter(mAdapter);
        mGridViewCell.setExpanded(true);
        mImageViewUnivercityLogo = (ImageView) getView().findViewById(R.id.imgview_uni_logo);
        if (mCellList.size() > 0) {
            UserInfo.selectedStudentImageURL = mCellList.get(0).getStudentProfileImage();
            GetPicassoImage.setCircleImageByPicasso(getContext(), mCellList.get(0).getUniversity_url(), mImageViewUnivercityLogo);
            GetPicassoImage.setCircleImageByPicasso(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        }
        //RenderImageByUIL.getInstance(getContext()).setImageByURL(UserInfo.university_logo_url, mImageViewUnivercityLogo, R.drawable.logo, R.drawable.loader);
        mTextViewUnivercityText = (TextView) getView().findViewById(R.id.textview_uni_header_name);
    }


    public void setLangSelection() {
        if(getContext()!=null){
            Utils.langConversion(getContext(), mTextViewTitle, WSContant.TAG_LANG_HOME, getString(R.string.tab_home), UserInfo.lang_pref);
            Utils.langConversion(getContext(), ((TextView) getView().findViewById(R.id.textview_welcome_to)), WSContant.TAG_LANG_WELCOME, getString(R.string.welcome_to), UserInfo.lang_pref);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_dashboard_cell_bg_holder:
                String menu_code = (String) v.getTag();
                AppLog.log("HomeFragment : onItemClick menu name: ", menu_code);
                UserInfo.menuCode = menu_code.trim();
                switch ( UserInfo.menuCode) {
                    case Constant.TAG_NOTICEBOARD:
                        Utils.navigateFragmentMenu(getFragmentManager(), new NoticeboardFragment(), NoticeboardFragment.TAG);
                        break;
                    case Constant.TAG_ATTENDANCE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new AttendanceFragment(), AttendanceFragment.TAG);
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
                    case Constant.TAG_DIARY:
                        //Utils.navigateFragmentMenu(getFragmentManager(), new DiaryFragment(), DiaryFragment.TAG);
                        Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                    case Constant.TAG_EVENTS:
                       // Utils.navigateFragmentMenu(getFragmentManager(), new EventsFragment(), EventsFragment.TAG);
                        Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                    case Constant.TAG_FEE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new FeeFragment(), FeeFragment.TAG);
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
                    case Constant.TAG_HOMEWORK:
                        //.navigateFragmentMenu(getFragmentManager(), new HomeworkFragment(), HomeworkFragment.TAG);
                        Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }
}
