package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

import java.util.UUID;

/**
 * Created by bwencke on 4/5/16.
 */
public class NewDevice extends DeviceCreator {

    private String ssid;

    public NewDevice(String name, String room, String ssid, DeviceType type) {
        super(name, room, type);
        this.ssid = ssid;
        this.id = UUID.randomUUID().toString();
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
