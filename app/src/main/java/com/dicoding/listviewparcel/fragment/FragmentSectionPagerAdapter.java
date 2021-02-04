package com.dicoding.listviewparcel.fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dicoding.listviewparcel.R;

public class FragmentSectionPagerAdapter extends FragmentPagerAdapter {
    public String username;
    private final Context mContext;

    public FragmentSectionPagerAdapter(Context context, FragmentManager fm){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.username = username;
        mContext = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
        R.string.tab_follower,
        R.string.tab_following
    };


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Fragment followerFragment = FollowerFragment.newInstance(username);
                return followerFragment;
            case 1:
                Fragment followingFragment = FollowingFragment.newInstance(username);
                return followingFragment;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
