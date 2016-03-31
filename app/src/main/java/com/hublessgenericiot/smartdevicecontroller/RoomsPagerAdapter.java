package com.hublessgenericiot.smartdevicecontroller;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.hublessgenericiot.smartdevicecontroller.dummy.SavedDeviceList;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.HublessCallback;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.HublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.IHublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceListResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.Device;

import java.util.Collections;
import java.util.LinkedList;

import retrofit2.Call;

/**
 * Created by bwencke on 2/24/16.
 */
public class RoomsPagerAdapter extends FragmentPagerAdapter {

    TabLayout tabLayout;
    RoomsActivity roomsActivity;

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
        LinkedList<String> tempRooms = new LinkedList<>();

        for(Device d : SavedDeviceList.ITEMS) {
            if(d.getRoom() != null && !tempRooms.contains(d.getRoom())) {
                tempRooms.add(d.getRoom());
            }
        }
        Collections.sort(tempRooms);
        rooms.clear();
        rooms.add("All Devices"); // TODO: Find a way to include the string resource R.string.room_all
        rooms.addAll(tempRooms);

        // if more than 3 tabs, make them scrollable
        if(rooms.size() > 3) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else if(rooms.size() > 1){
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setVisibility(View.GONE);
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
