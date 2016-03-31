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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hublessgenericiot.smartdevicecontroller.dummy.SavedDeviceList;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.HublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.IHublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.Device;

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
            //Toast.makeText(getActivity().getApplicationContext(), id.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity activity = getActivity();

        final View view = inflater.inflate(R.layout.fragment_edit_device, container, false);

        IHublessSdkService instance = HublessSdkService.getInstance(getActivity());
        instance.getDevice(id).enqueue(new Callback<DeviceResponse>() {
            @Override
            public void onResponse(Call<DeviceResponse> call, retrofit2.Response<DeviceResponse> response) {
                Log.d("DeviceFragment", "URL: " + call.request().url());
                LinkedList<String> rooms = new LinkedList<>();
                /*for (Device d : response.body().getPayload()) {  //TODO save the device list in a global somewhere?
                    /*if(d.room != null && !rooms.contains(d.room)) {   //TODO add room attribute
                        rooms.add(d.room);
                    }*/
                    /*if (d.getThingName().equals(id)) {
                        device = d;
                        break;
                    }
                }*/

                //device = response.body().getPayload().getDevice();

                Collections.sort(rooms);
                rooms.add(getString(R.string.room_none));
                rooms.add(getString(R.string.room_new));

                String[] a = rooms.toArray(new String[rooms.size()]);

                roomsAdapter = new ArrayAdapter<>(activity,
                        R.layout.simple_spinner_item,
                        a);

                ButterKnife.bind(this, view);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Handle UI here
                        name.setText("Hello");
                        room.setAdapter(roomsAdapter);
                        network.setAdapter(roomsAdapter);
                        notify.setChecked(true);
                    }
                });

                //device = SavedDeviceList.ITEM_MAP.get(id);
                //if(response.body() != null) {
                    //name.setText(response.body().getPayload().getDevice().getAttributes().get("name"));
                //}
                //else {
                    //name.setText("Hello");
                //}
                room.setAdapter(roomsAdapter);
                /*if(device.room != null) {     //TODO add room attribute
                    room.setSelection(roomsAdapter.getPosition(device.room));
                }*/
                network.setAdapter(roomsAdapter);
                notify.setChecked(true); //TODO add device.state);
            }

            @Override
            public void onFailure(Call<DeviceResponse> call, Throwable t) {
                Log.e("APITEST", "Error! " + t.getLocalizedMessage());
            }
        });

        /*
        View view = inflater.inflate(R.layout.fragment_edit_device, container, false);

        LinkedList<String> rooms = new LinkedList<>();
        for (SavedDeviceList.DummyItem d : SavedDeviceList.ITEMS) {
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
        device = SavedDeviceList.ITEM_MAP.get(id);
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
>>>>>>> master

        automationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof EditDeviceActivity) {
                    ((EditDeviceActivity) getActivity()).showAutomationDialog();
                }
            }
        });*/

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
        //TODO actually make this happen
        /*device.name = name.getText().toString();
        String tempRoom = roomsAdapter.getItem(room.getSelectedItemPosition()).toString();
        if(tempRoom.equals(getString(R.string.room_none))) {
            device.room = null;
        } else {
            device.room = roomsAdapter.getItem(room.getSelectedItemPosition()).toString();
        }*/
    }

    public void returnNewRoom(String name) {
        if(name == null) {

        } else {
            // TODO: This code is copied and pasted from above = BAD!
            Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
            LinkedList<String> rooms = new LinkedList<>();
            for (SavedDeviceList.DummyItem d : SavedDeviceList.ITEMS) {
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
