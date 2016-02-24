package com.hublessgenericiot.smartdevicecontroller;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent;

import java.util.LinkedList;

/**
 * Created by bwencke on 2/24/16.
 */
public class RoomsPagerAdapter extends FragmentPagerAdapter {

    TabLayout tabLayout;

    public RoomsPagerAdapter(FragmentManager fm, TabLayout tabLayout) {
        super(fm);
        this.tabLayout = tabLayout;
    }

    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    private LinkedList<String> rooms = new LinkedList<>();

    @Override
    public Fragment getItem(int position) {
        return DeviceFragment.newInstance(rooms.get(position));
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        for(DummyContent.DummyItem d : DummyContent.ITEMS) {
            if(!rooms.contains(d.room)) {
                rooms.add(d.room);
            }
        }

        // if more than 3 tabs, make them scrollable
        if(rooms.size() > 3) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        return rooms.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return rooms.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

}
