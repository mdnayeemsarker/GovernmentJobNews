package com.jobapps.governmentjobnews.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jobapps.governmentjobnews.Fragment.AllBanglaFragment;
import com.jobapps.governmentjobnews.Fragment.DailyBanglaFragment;
import com.jobapps.governmentjobnews.Fragment.OnlineBanglaFragment;
import com.jobapps.governmentjobnews.Fragment.TVChannelsFragment;

public class NewsPageAdapter extends FragmentPagerAdapter {
    public NewsPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new AllBanglaFragment();
        }else if (position == 1) {
            fragment = new DailyBanglaFragment();
        }else if (position == 2) {
            fragment = new OnlineBanglaFragment();
        }else if (position == 3) {
            fragment = new TVChannelsFragment();
        }
        assert fragment != null;
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "All";
        } else if (position == 1) {
            title = "Daily Bangla";
        }else if (position == 2) {
            title = "Online Bangla";
        }else if (position == 3) {
            title = "TV Channels";
        }
        return title;
    }
}