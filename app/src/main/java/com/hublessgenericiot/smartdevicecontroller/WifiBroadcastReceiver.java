package com.hublessgenericiot.smartdevicecontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.hublessgenericiot.smartdevicecontroller.activities.RoomsActivity;
import com.hublessgenericiot.smartdevicecontroller.data.SavedDeviceList;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceType;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.NewDevice;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by bwencke on 2/24/16.
 */
public class WifiBroadcastReceiver extends BroadcastReceiver {

    RoomsActivity activity;
    WifiManager wifi;

    LinkedList<NewDevice> newDevices = new LinkedList<>();

    public WifiBroadcastReceiver(RoomsActivity activity, WifiManager wifi) {
        this.activity = activity;
        this.wifi = wifi;
        wifi.startScan();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch(intent.getAction()) {
            case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                WifiController.notifyWifiStateChanged();
                break;
            case WifiManager.SCAN_RESULTS_AVAILABLE_ACTION:
                processScanResults();
                break;
        }
    }

    public void processScanResults() {
        for(ScanResult s : wifi.getScanResults()) {
            if(s.SSID.startsWith("Hubless_ESP")) {
                boolean addDevice = true;
                for (NewDevice newDevice : newDevices) {
                    if (newDevice.getBssid().equals(s.BSSID)) {
                        Log.d("WifiBroadcastReceiver", "new device already listed");
                        addDevice = false;
                    }
                }
                Log.d("WifiBroadcastReceiver", "deciding whether or not to add device");
                if (!addDevice) {
                    Log.d("WifiBroadcastReceiver", "not adding it");
                    continue;
                }
                Log.d("WifiBroadcastReceiver", "Found new device: " + s.SSID);
                NewDevice newDevice = new NewDevice(s.SSID, null, s.SSID, s.BSSID, DeviceType.LIGHT);
                newDevices.add(newDevice);
                SavedDeviceList.ITEMS.add(0, newDevice);
                SavedDeviceList.ITEM_MAP.put(newDevice.getId(), newDevice);
                activity.updateViewPager(false);

            }
        }

        for(NewDevice newDevice : newDevices) {
            boolean remove = true;
            for(ScanResult s : wifi.getScanResults()) {
                if(newDevice.getBssid().equals(s.BSSID)) {
                    remove = false;
                }
            }
            if(remove) {
                newDevices.remove(newDevice);
                SavedDeviceList.ITEMS.remove(newDevice);
                SavedDeviceList.ITEM_MAP.remove(newDevice.getId());
                activity.updateViewPager(false);
            }
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                wifi.startScan();
            }
        }, 10000);
    }
}
