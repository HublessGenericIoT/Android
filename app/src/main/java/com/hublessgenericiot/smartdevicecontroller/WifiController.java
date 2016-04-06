package com.hublessgenericiot.smartdevicecontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.CreatedDeviceData;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.NewDevice;

/**
 * Created by bwencke on 4/5/16.
 */
public class WifiController {

    private static WifiManager wifiManager;
    private static WifiBroadcastReceiver wifiBroadcastReceiver;
    private static RoomsActivity activity;
    private static IntentFilter filter;
    private static boolean registered;

    public static void init(RoomsActivity activity) {
        registered = false;
        wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        wifiBroadcastReceiver = new WifiBroadcastReceiver(activity, wifiManager);

        filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
    }

    public static void registerWifiReceiver(RoomsActivity newActivity) {
        activity = newActivity;
        if(wifiBroadcastReceiver != null && filter != null) {
            activity.registerReceiver(wifiBroadcastReceiver, filter);
            registered = true;
        }
    }

    public static void unregisterWifiReceiver() {
        if(wifiBroadcastReceiver != null && filter != null) {
            activity.unregisterReceiver(wifiBroadcastReceiver);
            registered = false;
        }
    }

    public static void configureDevice(NewDevice device, CreatedDeviceData createdDeviceData) {
        if(!registered) {
            return;
        }

        Log.d("CONFIG", createdDeviceData.getId());
        Log.d("CONFIG", createdDeviceData.getMqttData().toString());

        /* Connect to the device's wifi */
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", device.getSsid());
        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        //remember id
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }

    public static void notifyWifiStateChanged() {
        Log.d("Network", "Network state changed.");
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        //if(connectionInfo.getSSID().startsWith("ESP_")) {
            Log.d("Network", "Connected to " + connectionInfo.getSSID());
       // }
    }

}
