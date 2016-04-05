package com.hublessgenericiot.smartdevicecontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.hublessgenericiot.smartdevicecontroller.dummy.SavedDeviceList;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceCreator;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceType;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by bwencke on 2/24/16.
 */
public class WifiBroadcastReceiver extends BroadcastReceiver {

    RoomsActivity activity;
    WifiManager wifi;

    LinkedList<String> foundMACS = new LinkedList<>();

    public WifiBroadcastReceiver(RoomsActivity activity, WifiManager wifi) {
        this.activity = activity;
        this.wifi = wifi;
        wifi.startScan();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        for(ScanResult s : wifi.getScanResults()) {
            if(s.SSID.startsWith("ESP_")) {
                boolean addDevice = true;
                for(String str : foundMACS){
                    if(str.equals(s.BSSID)){
                        addDevice = false;
                    }
                }
                if(!addDevice) {
                    continue;
                }
                Log.d("WifiBroadcastReceiver", "Found new device: " + s.SSID);
                foundMACS.add(s.BSSID);
                DeviceCreator newDevice = new DeviceCreator("ESP 8266", null, DeviceType.LIGHT);
                SavedDeviceList.ITEMS.add(0, newDevice);
                SavedDeviceList.ITEM_MAP.put(newDevice.getId(), newDevice);
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
