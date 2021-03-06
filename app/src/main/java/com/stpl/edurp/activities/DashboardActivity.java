package com.stpl.edurp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.stpl.edurp.R;
import com.stpl.edurp.adapters.DashboardAdapter;
import com.stpl.edurp.constant.WSContant;
import com.stpl.edurp.database.TableParentStudentAssociation;
import com.stpl.edurp.fragments.HomeFragment;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;


/**
 * Created by Admin on 24-12-2016.
 */

public class DashboardActivity extends BaseActivity implements OnTabSelectListener, ViewPager.OnPageChangeListener {
    private static final java.lang.String TAG = "DashboardActivity";
    //private FrameLayout mContainer;
    public static Handler mHandler;
    public static DashboardActivity mContext;
    private BottomBar mBottomBar;
    public static ViewPager mViewPage;
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
        //For bottom tab
        if (UserInfo.currUserType.equalsIgnoreCase(WSContant.TAG_USERTYPE_PARENT)) {
            mAdapterViewPager = new DashboardAdapter(getSupportFragmentManager(), 3);
        } else {
            mAdapterViewPager = new DashboardAdapter(getSupportFragmentManager(), 2);
        }
        //For bottom tab

        TableParentStudentAssociation table = new TableParentStudentAssociation();
        table.openDB(DashboardActivity.this);
        AppLog.log("ITC", "UserInfo.currUserType: " + UserInfo.currUserType);
        if (UserInfo.currUserType != null) {
            switch (UserInfo.currUserType) {
                case WSContant.TAG_USERTYPE_PARENT:
                    UserInfo.parentId = UserInfo.userId;
                    //UserInfo.studentId = table.getStudentIDWRTParentID(UserInfo.parentId).getStudentid();
                    if (SharedPreferencesApp.getInstance().getDefaultChildSelection() != -1) {
                        UserInfo.studentId = SharedPreferencesApp.getInstance().getDefaultChildSelection();
                        AppLog.log(TAG, "default studentId 11 " + UserInfo.studentId);
                    } else {
                        UserInfo.studentId = table.getStudentIDWRTParentID(UserInfo.parentId).getStudentid();
                        AppLog.log(TAG, "default studentId 22 " + UserInfo.studentId);
                    }
                    break;
                case WSContant.TAG_USERTYPE_STUDENT:
                    UserInfo.studentId = UserInfo.userId;
                    UserInfo.parentId = table.getParentIDWRTStudentId(UserInfo.studentId).getParent_id();
                    break;
            }
        } else {
            Toast.makeText(mContext, "UserInfo.currUserType is null", Toast.LENGTH_SHORT).show();
            AppLog.log(TAG, "UserInfo.currUserType is: " + UserInfo.currUserType);
        }
        table.closeDB();
        AppLog.log(TAG, "Dashboard UserInfo.parentId: " + UserInfo.parentId);
        AppLog.log(TAG, "Dashboard UserInfo.studentId: " + UserInfo.studentId);
    }


    private void initView() {
        if (UserInfo.currUserType.equalsIgnoreCase(WSContant.TAG_USERTYPE_PARENT)) {
            mBottomBar = (BottomBar) findViewById(R.id.bottomBarParent);
        } else {
            mBottomBar = (BottomBar) findViewById(R.id.bottomBarFaculty);
        }
        mBottomBar.setVisibility(View.VISIBLE);
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
                mViewPage.setCurrentItem(0);
                // Utils.navigateFragmentMenu(getSupportFragmentManager(), new HomeFragment(), HomeFragment.TAG);
                // mTextViewTitle.setText(getResources().getString(R.string.tab_home));
             /*   if (mHandler != null)
                    mHandler.sendMessage(mHandler.obtainMessage(1, 0));*/
                break;
            case R.id.tab_ward:
                mViewPage.setCurrentItem(1);
                // mTextViewTitle.setText(getResources().getString(R.string.tab_wards));
                /*if (mHandler != null)
                    mHandler.sendMessage(mHandler.obtainMessage(0, 1));*/
                break;
            case R.id.tab_setting:
                mViewPage.setCurrentItem(2);
                //  mTextViewTitle.setText(getResources().getString(R.string.tab_options));
               /* if (mHandler != null)
                    mHandler.sendMessage(mHandler.obtainMessage(0, 2));*/
                break;
        }
    }


    private int temp_posi = 0;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Toast.makeText(DashboardActivity.this, "Selected page position: " + position, Toast.LENGTH_SHORT).show();
        if (temp_posi != position) {
            temp_posi = position;
            if (position == 0)
                Utils.navigateFragmentMenu(getSupportFragmentManager(), new HomeFragment(), HomeFragment.TAG);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mBottomBar.selectTabAtPosition(position);
        if (position == 0) {
            Utils.navigateFragmentMenu(getSupportFragmentManager(), new HomeFragment(), HomeFragment.TAG);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Toast.makeText(DashboardActivity.this, "Selected page position: " + state, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        try {
            String name11 = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            AppLog.log(TAG, "onBackPressed Navigation+++ " + name11 + " mBottomBar.getCurrentTabPosition() " + mBottomBar.getCurrentTabPosition());
            if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
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
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
                .setTitle(R.string.app_name)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.msg_exit)
                .setCancelable(false)
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.dismiss();
                            return true;
                        }
                        return false;
                    }
                })
                .setPositiveButton(getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Utils.animLeftToRight(DashboardActivity.this);
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();


       /* final CustomDialogbox dialogbox = new CustomDialogbox(this, CustomDialogbox.TYPE_YES_NO);
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
            public boolean onKey(DialogInterface mDialog, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mDialog.dismiss();
                }
                return true;
            }
        });*/
    }


}


