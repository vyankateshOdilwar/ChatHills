package com.example.chathills.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.chathills.Fragments.CallsFragment;
import com.example.chathills.Fragments.ChatsFragment;
import com.example.chathills.Fragments.StatusFragment;
import com.example.chathills.R;

public class FragmentsAdapter extends FragmentPagerAdapter {

    private static final int[] TAB_TITLES = new int[]{R.string.chat, R.string.staus, R.string.call};
    private final Context mContext;

    public FragmentsAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position==0){
            return new ChatsFragment();
        } else if (position==1) {
            return new StatusFragment();
        }else {
            return new CallsFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }


}
