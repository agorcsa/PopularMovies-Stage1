package com.example.andreeagorcsa.popularmovies.Adapters;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.andreeagorcsa.popularmovies.Tab1;
import com.example.andreeagorcsa.popularmovies.Tab2;
import com.example.andreeagorcsa.popularmovies.Tab3;

/**
 * Created by andreeagorcsa on 2018. 04. 23..
 */

public class PagerAdapter extends FragmentStatePagerAdapter{

    int numberOfTabs;


    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tab1 tab1 = new Tab1();
                //return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
               // return tab2;
            case 2:
                Tab3 tab3 = new Tab3();
               // return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}