package com.hublessgenericiot.smartdevicecontroller;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
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
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hublessgenericiot.smartdevicecontroller.hublesssdk.HublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.IHublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.apiresponses.DeviceListResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.models.ShadowedDevice;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    private String id;
    private ShadowedDevice device;

    ArrayAdapter roomsAdapter;

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
        final Activity activity = getActivity();

        final View view = inflater.inflate(R.layout.fragment_edit_device, container, false);

        IHublessSdkService instance = HublessSdkService.getInstance(getActivity());
        instance.getAllDevices().enqueue(new Callback<DeviceListResponse>() {
            @Override
            public void onResponse(Call<DeviceListResponse> call, retrofit2.Response<DeviceListResponse> response) {
                Log.d("DeviceFragment", "URL: " + call.request().url());
                LinkedList<String> rooms = new LinkedList<>();
                for(ShadowedDevice d : response.body().getPayload()) {  //TODO save the device list in a global somewhere?
                    /*if(d.room != null && !rooms.contains(d.room)) {   //TODO add room attribute
                        rooms.add(d.room);
                    }*/
                    if(d.getDevice().getDefaultClientId().equals(id)){
                        device = d;
                        break;
                    }
                }
                Collections.sort(rooms);
                rooms.add(getString(R.string.room_none));
                rooms.add(getString(R.string.room_new));

                String[] a = rooms.toArray(new String[rooms.size()]);

                roomsAdapter = new ArrayAdapter<>(activity,
                        R.layout.simple_spinner_item,
                        a);

                ButterKnife.bind(this, view);
                //device = DummyContent.ITEM_MAP.get(id);
                name.setText(device.getDevice().getThingName());
                room.setAdapter(roomsAdapter);
                /*if(device.room != null) {     //TODO add room attribute
                    room.setSelection(roomsAdapter.getPosition(device.room));
                }*/
                network.setAdapter(roomsAdapter);
                notify.setChecked(true); //TODO add device.state);
            }

            @Override
            public void onFailure(Call<DeviceListResponse> call, Throwable t) {
                Log.e("APITEST", "Error! " + t.getLocalizedMessage());
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
                    ((EditDeviceActivity) getActivity()).finishWithResult(true);
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
}
