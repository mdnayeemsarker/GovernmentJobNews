package com.jobapps.governmentjobnews.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jobapps.governmentjobnews.Fragment.OutOfFiveFragment;
import com.jobapps.governmentjobnews.Fragment.OutOfFourFragment;

public class CgpaPageAdapter extends FragmentPagerAdapter {

    public CgpaPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new OutOfFourFragment();
        }else if (position == 1) {
            fragment = new OutOfFiveFragment();
        }
        assert fragment != null;
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "CGPA Out Of 4";
        } else if (position == 1) {
            title = "CGPA Out Of 5";
        }
        return title;
    }
}