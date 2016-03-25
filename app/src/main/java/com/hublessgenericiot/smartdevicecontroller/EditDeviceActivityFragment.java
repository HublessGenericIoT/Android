package com.hublessgenericiot.smartdevicecontroller;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Edit a device
 */
public class EditDeviceActivityFragment extends Fragment {

    @Bind(R.id.name) TextView name;
    @Bind(R.id.room) Spinner room;
    @Bind(R.id.network) Spinner network;
    @Bind(R.id.notify) Switch notify;

    private String id;
    private DummyContent.DummyItem device;

    ArrayAdapter roomsAdapter;
    private boolean newRoom = false;

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
        for (DummyContent.DummyItem d : DummyContent.ITEMS) {
            if (d.room != null && !rooms.contains(d.room)) {
                rooms.add(d.room);
            }
        }
        Collections.sort(rooms);
        rooms.add(getString(R.string.room_none));
        rooms.add(getString(R.string.room_new));

        String[] a = rooms.toArray(new String[rooms.size()]);

        roomsAdapter = new ArrayAdapter<>(this.getActivity(),
                R.layout.simple_spinner_item,
                a);

        ButterKnife.bind(this, view);
        device = DummyContent.ITEM_MAP.get(id);
        name.setText(device.name);
        room.setAdapter(roomsAdapter);
        if (device.room != null) {
            room.setSelection(roomsAdapter.getPosition(device.room));
        }
        room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (roomsAdapter.getItem(position).toString().equals(getString(R.string.room_new))) {
                    if(getActivity() instanceof EditDeviceActivity) {
                        ((EditDeviceActivity) getActivity()).showNewRoomDialog();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        network.setAdapter(roomsAdapter);
        notify.setChecked(device.state);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.fragment_edit_device, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save:
                updateDevice();
                if(getActivity() instanceof EditDeviceActivity) {
                    ((EditDeviceActivity) getActivity()).finishWithResult(true, newRoom);
                } else {
                    getActivity().finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateDevice() {
        device.name = name.getText().toString();
        String tempRoom = roomsAdapter.getItem(room.getSelectedItemPosition()).toString();
        if(tempRoom.equals(getString(R.string.room_none))) {
            device.room = null;
        } else {
            device.room = roomsAdapter.getItem(room.getSelectedItemPosition()).toString();
        }
    }

    public void returnNewRoom(String name) {
        if(name == null) {

        } else {
            // TODO: This code is copied and pasted from above = BAD!
            Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
            LinkedList<String> rooms = new LinkedList<>();
            for (DummyContent.DummyItem d : DummyContent.ITEMS) {
                if (d.room != null && !rooms.contains(d.room)) {
                    rooms.add(d.room);
                }
            }
            rooms.add(name);
            Collections.sort(rooms);
            rooms.add(getString(R.string.room_none));
            rooms.add(getString(R.string.room_new));

            String[] a = rooms.toArray(new String[rooms.size()]);
            roomsAdapter = new ArrayAdapter<>(this.getActivity(),
                    R.layout.simple_spinner_item,
                    a);
            room.setAdapter(roomsAdapter);
            room.setSelection(roomsAdapter.getPosition(name));
            newRoom = true; // TODO: If the selected room changes, set to false
        }
    }
}
