package com.hublessgenericiot.smartdevicecontroller;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent;
import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent.DummyItem;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

    private String mRoom;

    LinkedList<DummyItem> items;
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

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            items = new LinkedList<>();
            for(DummyItem d : DummyContent.ITEMS) {
                if(mRoom.equals("All Devices") ||  d.newDevice) {
                    items.add(d);
                } else if(d.room != null && d.room.equals(mRoom)) {
                    items.add(d);
                }
            }
            adapter = new MyDeviceRecyclerViewAdapter(items, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void updateAdapter() {
        items.clear();
        for(DummyItem d : DummyContent.ITEMS) {
            if(mRoom.equals("All Devices") || (d.room != null && d.room.equals(mRoom)) || d.newDevice) {
                items.add(d);
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
        void onDeviceClick(DummyItem item);
        void onDeviceLongClick(DummyItem item);
    }

    public void reRender() {
        updateAdapter();
    }
}
