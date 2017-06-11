package com.stpl.edurp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stpl.edurp.R;
import com.stpl.edurp.utils.AppLog;
import com.stpl.edurp.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class MainHomeFragment extends Fragment implements View.OnClickListener {
    public final static String TAG="MainHomeFragment";
    public MainHomeFragment() {
        AppLog.log("MainHomeFragment", "MainHomeFragment ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.log("MainHomeFragment", "onCreate ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_mainhome, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        Utils.navigateFragmentMenu(getFragmentManager() , new HomeFragment(), HomeFragment.TAG);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public void notifyFragment() {
        initView();
    }
}
