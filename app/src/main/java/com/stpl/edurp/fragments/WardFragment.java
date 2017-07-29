package com.stpl.edurp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stpl.edurp.R;
import com.stpl.edurp.activities.ChildProfileActivity;
import com.stpl.edurp.adapters.WardChildRowAdapter;
import com.stpl.edurp.database.CommonInfo;
import com.stpl.edurp.interfaces.ICallBack;
import com.stpl.edurp.models.TableStudentDetailsDataModel;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.GetUILImage;
import com.stpl.edurp.utils.InternetManager;
import com.stpl.edurp.utils.SharedPreferencesApp;
import com.stpl.edurp.utils.UserInfo;
import com.stpl.edurp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class WardFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "WardFragment11";
    private ImageView mProfileImage;//
    private TextView mTextViewProfileHeaderName; //textview_profile_header_name
    private TextView mProfileHeaderLocation;  //textview_profile_header_location
    private RecyclerView mRecycleViewChildInfo;
    private WardChildRowAdapter mChildAdapter;
    private ArrayList<TableStudentDetailsDataModel> mListChildInfoHolder;
    private ImageView mProfileEye;
    // private FloatingActionButton mFloatingBtn;
    private FrameLayout mFramLayout;
    private TextView mTextViewTitle;
    public ImageView mImageViewStudentTitleImg;
    private int mPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getAllChildWRTParent();
        AppLog.log("WardFragment", "onCreate");
    }

    private void init() {
        mListChildInfoHolder = new ArrayList<TableStudentDetailsDataModel>();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_ward, null);
        AppLog.log("WardFragment", "onCreateView");
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppLog.log("WardFragment", "onActivityCreated");
        initView();
    }


    private void initView() {
        mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_wards);
        mImageViewStudentTitleImg = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImageViewStudentTitleImg.setVisibility(View.INVISIBLE);
        //------------------------------------------------------
        mFramLayout = (FrameLayout) getView().findViewById(R.id.framelayout_holder);
        mProfileImage = (ImageView) getView().findViewById(R.id.imageview_profile_logo);
        mTextViewProfileHeaderName = (TextView) getView().findViewById(R.id.textview_profile_header_name);
        mProfileHeaderLocation = (TextView) getView().findViewById(R.id.textview_profile_header_location);
        mProfileEye = (ImageView) getView().findViewById(R.id.imageview_profile_eye);
        //mFloatingBtn = (FloatingActionButton)getView().findViewById(R.id.fab_edit);
        mProfileEye.setVisibility(View.GONE);
        mRecycleViewChildInfo = (RecyclerView) getView().findViewById(R.id.listview_frag_ward);
        mProfileEye.setOnClickListener(this);
        //mFloatingBtn.setOnClickListener(this);
        initRecycleAdapter();
        setDefaultStudent();
    }


    private void setDefaultStudent() {
        for(int index = 0 ; index < mListChildInfoHolder.size() ;index++){
            if(UserInfo.studentId==mListChildInfoHolder.get(index).getStudent_id()){
                AppLog.log(TAG, "default match studentid "+UserInfo.studentId);
                setDefaultStudentProfileInHeader(false, index);
                UserInfo.selectedStudentGender = mListChildInfoHolder.get(index).getGender();
                break;
            }
        }
    }

    private void setDefaultStudentProfileInHeader(boolean isClicked, final int position) {
        mPosition = position;
        UserInfo.selectedStudentImageURL = mListChildInfoHolder.get(position).getImageurl();
        UserInfo.selectedStudentGender = mListChildInfoHolder.get(position).getGender();
        AppLog.log("Gender",UserInfo.selectedStudentGender);
        GetUILImage.getInstance().setCircleImage(getContext(), mListChildInfoHolder.get(position).getImageurl(), mProfileImage);
        mTextViewProfileHeaderName.setText(mListChildInfoHolder.get(position).getFullName());
        mProfileHeaderLocation.setText(mListChildInfoHolder.get(position).getUnversity_name());
        GetUILImage.getInstance().setCircleImage(getContext(), mListChildInfoHolder.get(position).getImageurl(), mImageViewStudentTitleImg);
        AppLog.log("setDefaultStudentProfileInHeader getFullName ", mListChildInfoHolder.get(position).getFullName());
        //save user default child selection

        if (isClicked) {
            UserInfo.studentId = mListChildInfoHolder.get(position).getStudent_id();
            if(InternetManager.isInternetConnected(getContext())) {
                Utils.showProgressBar(getContext());
                Utils.getHomeFragmentItems(new ICallBack() {
                    @Override
                    public void callBack() {
                        Utils.dismissProgressBar();
                    }
                });
            }
        }else{
            UserInfo.studentId = mListChildInfoHolder.get(position).getStudent_id();
            AppLog.log("setDefaultStudentProfileInHeader   UserInfo.studentId222 ", ""+mListChildInfoHolder.get(position).getStudent_id());
        }

        SharedPreferencesApp.getInstance().savedDefaultChildSelection(UserInfo.studentId);
        SharedPreferencesApp.getInstance().saveLastSavedUniversityID("" +mListChildInfoHolder.get(position).getUniversity_id());
        UserInfo.selectedStudentImageURL = mListChildInfoHolder.get(position).getImageurl();
        //AppLog.log("Student profile pic: "+ UserInfo.selectedStudentImageURL );
    }

    private void initRecycleAdapter() {
        RecyclerView.LayoutManager linear = new LinearLayoutManager(getContext());
        mRecycleViewChildInfo.setLayoutManager(linear);
        mRecycleViewChildInfo.setItemAnimator(new DefaultItemAnimator());
        mChildAdapter = new WardChildRowAdapter(getContext(), mListChildInfoHolder, this);
        mRecycleViewChildInfo.setAdapter(mChildAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_profile_eye:
                //Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_SHORT).show();
                navigateToNextPage(ChildProfileActivity.class);
                break;
            case R.id.fab_edit:
                Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rel_ward_row_holder:
                setDefaultStudentProfileInHeader(true, (Integer) view.getTag());
                break;
        }
    }


    /**
     * get all child with respect to logged parent
     */
    public void getAllChildWRTParent() {
        CommonInfo table = new CommonInfo();
        table.openDB(getContext());
        try {
            // mListChildInfoHolder =  table.getAllChildWRTParent(""+((LoginDataModel)ModelFactory.getInstance().getModel(ModelFactory.MODEL_LOGIN)).data.UserId);
            mListChildInfoHolder = table.getAllChildWRTParent(UserInfo.parentId);
            AppLog.log("getAllChildWRTParent parentId ", "" + UserInfo.parentId);
            AppLog.log("getAllChildWRTParent mListChildInfoHolder ", "" + mListChildInfoHolder.size());
        } catch (Exception e) {
            AppLog.errLog("getAllChildWRTParent", e.getMessage());
        }
        table.closeDB();
    }


    private void navigateToNextPage(Class<?> activity) {
        Intent intent = new Intent(getContext(), activity);
        if(activity == ChildProfileActivity.class ) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("list", mListChildInfoHolder.get(mPosition));
            intent.putExtras(bundle);
        }
        startActivity(intent);
        Utils.animRightToLeft(getActivity());
    }
}

