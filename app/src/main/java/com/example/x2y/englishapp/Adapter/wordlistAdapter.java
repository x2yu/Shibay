package com.example.x2y.englishapp.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.x2y.englishapp.Fragment.wordlistFragment;

public class wordlistAdapter extends FragmentPagerAdapter {
    private static int PAGE_COUNT;//表示要展示的页面数量
    private Context mContext;

    public wordlistAdapter(Context context, FragmentManager fm){
        super(fm);
        this.mContext = context;
        PAGE_COUNT=4;
    }
    @Override
    public Fragment getItem(int position) {
        return wordlistFragment.newInstance(position);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return "今日单词";
        case 1:
            return "已学单词";
        case 2:
            return "未学单词";
        case 3:
            return "收藏单词";
        default:break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
