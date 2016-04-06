package com.hublessgenericiot.smartdevicecontroller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hublessgenericiot.smartdevicecontroller.dummy.SavedDeviceList;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.Device;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DeviceFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ROOM = "room";
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private View emptyView;

    private String mRoom;

    List<Device> devices;
    MyDeviceRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DeviceFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DeviceFragment newInstance(String room) {
        DeviceFragment fragment = new DeviceFragment();
        Bundle args = new Bundle();
        args.putString(ROOM, room);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mRoom = getArguments().getString(ROOM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        emptyView = (View) view.findViewById(R.id.empty);

        // Set the adapter
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        devices = new ArrayList<Device>();
        if(SavedDeviceList.ITEMS.size() > 0) {
            // not empty
            for (Device d : SavedDeviceList.ITEMS) {
                if (d.getName() == null) {
                    continue;
                }
                if (mRoom.equals("All Devices")) {
                    devices.add(d);
                } else if ((d.getRoom().equals(mRoom))) {
                    devices.add(d);
                }
            }

            // TODO: Sort alphabetically

            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            // empty
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        adapter = new MyDeviceRecyclerViewAdapter(devices, mListener);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void updateAdapter() {
        devices.clear();
        for(Device d : SavedDeviceList.ITEMS) {
            if(mRoom.equals("All Devices") || (d.getRoom() != null && d.getRoom().equals(mRoom))) { //|| d.newDevice) {
                devices.add(d);
            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDeviceClick(Device item);
        void onDeviceLongClick(Device item);
    }

    public void reRender() {
        updateAdapter();
    }
}
