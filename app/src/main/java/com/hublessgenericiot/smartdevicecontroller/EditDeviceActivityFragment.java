package com.hublessgenericiot.smartdevicecontroller;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hublessgenericiot.smartdevicecontroller.dummy.SavedDeviceList;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.HublessCallback;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.HublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.IHublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceUpdatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.Device;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceCreator;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceType;

import java.util.Collections;
import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Edit a device
 */
public class EditDeviceActivityFragment extends Fragment {

    @Bind(R.id.name) TextView name;
    @Bind(R.id.room) Spinner room;
    @Bind(R.id.network) Spinner network;
    @Bind(R.id.notify) Switch notify;
    @Bind(R.id.automation_button) Button automationButton;

    private String id;
    private Device device;

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
        for (Device d : SavedDeviceList.ITEMS) {
            if (d.getRoom() != null && !rooms.contains(d.getRoom())) {
                rooms.add(d.getRoom());
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
        device = SavedDeviceList.ITEM_MAP.get(id);
        name.setText(device.getName());
        room.setAdapter(roomsAdapter);
        if (device.getRoom() != null) {
            room.setSelection(roomsAdapter.getPosition(device.getRoom()));
        }
        room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (roomsAdapter.getItem(position).toString().equals(getString(R.string.room_new))) {
                    if (getActivity() instanceof EditDeviceActivity) {
                        ((EditDeviceActivity) getActivity()).showNewRoomDialog();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        network.setAdapter(roomsAdapter);
        notify.setChecked(false); //device.state); TODO states

        automationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof EditDeviceActivity) {
                    ((EditDeviceActivity) getActivity()).showAutomationDialog();
                }
            }
        });

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
        final Activity activity = getActivity();

        final String tempRoom = roomsAdapter.getItem(room.getSelectedItemPosition()).toString();
        final String tempName = name.getText().toString();
        /*if(tempRoom.equals(getString(R.string.room_none))) {
            tempRoom = null;
        }*/

        //TODO add Device type field
        DeviceCreator dc = new DeviceCreator(device.getId(), tempName, tempRoom, DeviceType.LIGHT);

        IHublessSdkService instance = HublessSdkService.getInstance(activity);
        instance.updateDevice(device.getId(), dc).enqueue(new HublessCallback<DeviceUpdatedResponse>() {
            @Override
            public void doOnResponse(Call<DeviceUpdatedResponse> call, retrofit2.Response<DeviceUpdatedResponse> response) {
                Toast.makeText(activity.getApplicationContext(), "Device Updated", Toast.LENGTH_LONG).show();
                //TODO this is not an accepatble long-term solution, must refresh tab
            }
        });

        device.setName(tempName);
        device.setRoom(tempRoom);
    }

    public void returnNewRoom(String name) {
        if(name == null) {

        } else {
            // TODO: This code is copied and pasted from above = BAD!
            Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
            LinkedList<String> rooms = new LinkedList<>();
            for (Device d : SavedDeviceList.ITEMS) {
                if (d.getRoom() != null && !rooms.contains(d.getRoom())) {
                    rooms.add(d.getRoom());
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
