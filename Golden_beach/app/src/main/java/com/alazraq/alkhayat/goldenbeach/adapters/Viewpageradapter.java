package com.alazraq.alkhayat.goldenbeach.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Viewpageradapter extends FragmentPagerAdapter {

    private List<Fragment> fragments=new ArrayList<>();
    private List<String> fragment_titles=new ArrayList<>();

    public Viewpageradapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }



    public void addFragment(Fragment fragment,String fragment_title){

        fragments.add(fragment);
        fragment_titles.add(fragment_title);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position );
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragment_titles.get(position);
    }
}

