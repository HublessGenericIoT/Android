package com.hublessgenericiot.smartdevicecontroller;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent;

import java.lang.reflect.Array;
import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditDeviceActivityFragment extends Fragment {

    @Bind(R.id.name) TextView name;
    @Bind(R.id.room) Spinner room;
    @Bind(R.id.network) Spinner network;
    @Bind(R.id.notify) Switch notify;

    private String id;
    private DummyContent.DummyItem device;

    public EditDeviceActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ItemDataHolder) {
            id = ((ItemDataHolder) context).getDeviceId();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_device, container, false);

        LinkedList<String> rooms = new LinkedList<>();
        for(DummyContent.DummyItem d : DummyContent.ITEMS) {
            if(!rooms.contains(d.room)) {
                rooms.add(d.room);
            }
        }

        String[] a = rooms.toArray(new String[rooms.size()]);

        SpinnerAdapter roomsAdapter = new ArrayAdapter<String>(this.getActivity(),
                R.layout.simple_spinner_item,
                a);

        ButterKnife.bind(this, view);
        device = DummyContent.ITEM_MAP.get(id);
        name.setText(device.name);
        room.setAdapter(roomsAdapter);
        network.setAdapter(roomsAdapter);
        notify.setChecked(device.state);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
