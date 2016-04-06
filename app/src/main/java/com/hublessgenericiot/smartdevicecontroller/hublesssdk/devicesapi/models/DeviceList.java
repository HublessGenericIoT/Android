package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

import java.util.List;

/**
 * Created by david on 4/4/16.
 */
public class DeviceList {
    private List<ShadowedDevice> devices;
    private String nextToken;

    public List<ShadowedDevice> getDevices() {
        return devices;
    }

    public String getNextToken() {
        return nextToken;
    }
}
