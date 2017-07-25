package com.stpl.edurp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stpl.edurp.fragments.MainHomeFragment;
import com.stpl.edurp.fragments.OptionsFragment;
import com.stpl.edurp.fragments.WardFragment;

/**
 * Created by Admin on 24-12-2016.
 */

public class DashboardAdapter extends FragmentStatePagerAdapter {

    private final int mTotalPage;

    public DashboardAdapter(FragmentManager supportFragmentManager, int pTotalPage) {
        super(supportFragmentManager);
        mTotalPage = pTotalPage;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new MainHomeFragment();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                if (mTotalPage == 3) {
                    return new WardFragment();
                } else {
                    return new OptionsFragment();
                }
            case 2: // Fragment # 1 - This will show SecondFragment
                return new OptionsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTotalPage;
    }


}

