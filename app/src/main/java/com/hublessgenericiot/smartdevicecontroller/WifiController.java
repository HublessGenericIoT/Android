package com.hublessgenericiot.smartdevicecontroller;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.hublessgenericiot.smartdevicecontroller.activities.RoomsActivity;
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

    private static DeviceConfig currentConfig;

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

    public static void configureDevice(DeviceConfig deviceConfig) {
        if(!registered) {
            return;
        }

        currentConfig = deviceConfig;

        Log.d("CONFIG", currentConfig.createdDeviceData.getId());
        Log.d("CONFIG", currentConfig.createdDeviceData.getMqttData().toString());

        /* Connect to the device's wifi */
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = currentConfig.device.getSsid();
        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        wifiConfig.priority = 99999;

        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
    }

    public static void notifyWifiStateChanged() {
        if(currentConfig == null || !currentConfig.isValid()) {
            return;
        }

        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        if(connectionInfo != null && connectionInfo.getSSID() != null) {
            String ssid = connectionInfo.getSSID().replaceAll("^\"|\"$", ""); // remove quotes
            Log.d("Network", "Connected to " + ssid);
            Log.d("Network", "Looking for " + currentConfig.device.getSsid());
            if(ssid.equals(currentConfig.device.getSsid())) {
                currentConfig.sendConfiguration(activity);
            }
        }
    }

}
