package com.hallo.helloworld;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs=numOfTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new FragmentRight();
            case 1:
                return new FragmentLeft();
            default:
                return null;
        }
    }
    public int getCount(){
        return numOfTabs;
    }
}
