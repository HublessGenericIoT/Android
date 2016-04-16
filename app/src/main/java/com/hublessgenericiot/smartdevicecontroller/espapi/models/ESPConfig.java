package com.hublessgenericiot.smartdevicecontroller.espapi.models;

/**
 * Created by bwencke on 4/6/16.
 */
public class ESPConfig {

    private ESPDeviceInfo DeviceInfo; // TODO: the current ESPDeviceInfo class should become this class and then be removed, but first the ESP needs to support that

    public ESPConfig(ESPDeviceInfo deviceInfo) {
        this.DeviceInfo = deviceInfo;
    }

}
