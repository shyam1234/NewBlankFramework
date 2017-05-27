package com.stpl.edurp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.stpl.edurp.R;
import com.stpl.edurp.adapters.DashboardAdapter;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableParentStudentAssociation;
import com.stpl.edurp.fragments.HomeFragment;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.CustomDialogbox;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


/**
 * Created by Admin on 24-12-2016.
 */

public class DashboardActivity extends BaseActivity implements OnTabSelectListener, ViewPager.OnPageChangeListener {
    private static final java.lang.String TAG = "DashboardActivity";
    //private FrameLayout mContainer;
    public static Handler mHandler;
    public static DashboardActivity mContext;
    private BottomBar mBottomBar;
    private ViewPager mViewPage;
    private DashboardAdapter mAdapterViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dashboard);
        init();
        initView();
        initRegistration();
    }

    private void init() {
        mContext = this;
        mAdapterViewPager = new DashboardAdapter(getSupportFragmentManager());
        TableParentStudentAssociation table = new TableParentStudentAssociation();
        table.openDB(DashboardActivity.this);
        AppLog.log("ITC", "UserInfo.currUserType: " + UserInfo.currUserType);
        switch (UserInfo.currUserType) {
            case WSContant.TAG_USERTYPE_PARENT:
                UserInfo.parentId = UserInfo.userId;
                //UserInfo.studentId = table.getStudentIDWRTParentID(UserInfo.parentId).getStudentid();
                if (SharedPreferencesApp.getInstance().getDefaultChildSelection() != -1) {
                    UserInfo.studentId = SharedPreferencesApp.getInstance().getDefaultChildSelection();
                    AppLog.log(TAG,"default studentId 11 "+UserInfo.studentId);
                }else{
                    UserInfo.studentId = table.getStudentIDWRTParentID(UserInfo.parentId).getStudentid();
                    AppLog.log(TAG,"default studentId 22 "+UserInfo.studentId);
                }
                break;
            case WSContant.TAG_USERTYPE_STUDENT:
                UserInfo.studentId = UserInfo.userId;
                UserInfo.parentId = table.getParentIDWRTStudentId(UserInfo.studentId).getParent_id();
                break;
        }
        table.closeDB();
        AppLog.log("ITC", "Dashboard UserInfo.parentId: " + UserInfo.parentId);
        AppLog.log("ITC", "Dashboard UserInfo.studentId: " + UserInfo.studentId);
    }


    private void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mViewPage = (ViewPager) findViewById(R.id.vpPager);
        mViewPage.addOnPageChangeListener(this);
    }


    private void initRegistration() {
        mBottomBar.setOnTabSelectListener(this);
        mViewPage.setAdapter(mAdapterViewPager);
    }


    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_home:
                //lFragmentTransaction.replace(R.id.contentContainer, new HomeFragment());
                mViewPage.setCurrentItem(0);
                // mTextViewTitle.setText(getResources().getString(R.string.tab_home));
                if (mHandler != null)
                    mHandler.sendMessage(mHandler.obtainMessage(1, 0));
                break;
            case R.id.tab_ward:
                mViewPage.setCurrentItem(1);
                // mTextViewTitle.setText(getResources().getString(R.string.tab_wards));
                if (mHandler != null)
                    mHandler.sendMessage(mHandler.obtainMessage(0, 1));
                break;
            case R.id.tab_setting:
                mViewPage.setCurrentItem(2);
                //  mTextViewTitle.setText(getResources().getString(R.string.tab_options));
                if (mHandler != null)
                    mHandler.sendMessage(mHandler.obtainMessage(0, 2));
                break;

        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Toast.makeText(DashboardActivity.this, "Selected page position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageSelected(int position) {
        mBottomBar.selectTabAtPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Toast.makeText(DashboardActivity.this, "Selected page position: " + state, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                String name = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                switch (mBottomBar.getCurrentTabPosition()) {
                    case 0:
                        switch (name) {
                            case HomeFragment.TAG:
                                popupCloseAppAcknowledge();
                                break;
                            default:
                                getSupportFragmentManager().popBackStack();
                                break;
                        }
                        break;
                    case 1:
                        mBottomBar.selectTabAtPosition(0);
                        break;
                    case 2:
                        mBottomBar.selectTabAtPosition(1);
                        break;
                }

                AppLog.log("onBackPressed", "" + getSupportFragmentManager().getBackStackEntryCount());
                AppLog.log("onBackPressed", "name " + name);
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
//                finish();
//                Utils.animLeftToRight(DashboardActivity.this);
                popupCloseAppAcknowledge();
                AppLog.log("onBackPressed", "finish ");
            }
        } catch (Exception e) {
            AppLog.errLog("onBackPressed", e.getMessage());
        }
    }

    /**
     * acknowledge before closing app
     */
    private void popupCloseAppAcknowledge() {
        // getSupportFragmentManager().popBackStack(HomeFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        final CustomDialogbox dialogbox = new CustomDialogbox(this,CustomDialogbox.TYPE_YES_NO);
        dialogbox.setTitle(getResources().getString(R.string.msg_exit));
        dialogbox.show();
        dialogbox.getBtnYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Utils.animLeftToRight(DashboardActivity.this);
            }
        });
        dialogbox.getBtnNo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogbox.dismiss();
            }
        });
        dialogbox.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });
    }

}


