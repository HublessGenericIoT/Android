package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

import java.util.UUID;

/**
 * Created by bwencke on 4/5/16.
 */
public class NewDevice extends DeviceCreator {

    private String ssid;
    private String bssid;

    public NewDevice(String name, String room, String ssid, String bssid, DeviceType type) {
        super(name, room, type);
        this.ssid = ssid;
        this.bssid = bssid;
        this.id = UUID.randomUUID().toString();
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }
}
