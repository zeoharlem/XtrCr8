package com.example.xtremecardz.Activities;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xtremecardz.CustomAdapters.PageAdapter;
import com.example.xtremecardz.CustomFragments.BgTab;
import com.example.xtremecardz.CustomFragments.ColorFillTab;
import com.example.xtremecardz.CustomFragments.GradientTab;
import com.example.xtremecardz.CustomFragments.ImageTab;
import com.example.xtremecardz.R;

public class BackgroundTaskActivity extends AppCompatActivity implements BgTab.OnFragmentInteractionListener, ImageTab.OnFragmentInteractionListener, ColorFillTab.OnFragmentInteractionListener, GradientTab.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_task);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = findViewById(R.id.bgTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Background"));
        tabLayout.addTab(tabLayout.newTab().setText("Shapes"));
        //tabLayout.addTab(tabLayout.newTab().setText("Typeface"));
        tabLayout.addTab(tabLayout.newTab().setText("Camera"));
        tabLayout.addTab(tabLayout.newTab().setText("Gallery"));

        final ViewPager viewPager   = findViewById(R.id.viewPagerRow);
        final PageAdapter pageAdapter   = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setCustomFont(tabLayout);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setCustomFont(TabLayout mCustomFontTab) {
        ViewGroup vg = (ViewGroup) mCustomFontTab.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/GoogleSans-Bold.ttf"));
                }
            }
        }
    }
}
