package com.hublessgenericiot.smartdevicecontroller;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class DevicesListActivityFragment extends Fragment {



    public DevicesListActivityFragment() {
    }

    /**
     * Returns a new instance of this fragment
     */
    public static DevicesListActivityFragment newInstance() {
        DevicesListActivityFragment fragment = new DevicesListActivityFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devices_list, container, false);
    }
}
