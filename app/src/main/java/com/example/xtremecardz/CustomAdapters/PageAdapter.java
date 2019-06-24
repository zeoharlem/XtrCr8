package com.example.xtremecardz.CustomAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.xtremecardz.CustomFragments.BgTab;
import com.example.xtremecardz.CustomFragments.ColorFillTab;
import com.example.xtremecardz.CustomFragments.GradientTab;
import com.example.xtremecardz.CustomFragments.ImageTab;

public class PageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                BgTab bgTab = new BgTab();
                return bgTab;
            case 1:
                ImageTab imageTab   = new ImageTab();
                return imageTab;
            case 2:
                GradientTab gradientTab = new GradientTab();
                return gradientTab;
            case 3:
                ColorFillTab colorFillTab   = new ColorFillTab();
                return colorFillTab;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
