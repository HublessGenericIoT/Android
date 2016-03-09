package com.hublessgenericiot.smartdevicecontroller.hublesssdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * A ShadowedDevice is a device and it's shadow together.
 * This way, all of the device's data is accessible.
 */
public class ShadowedDevice {
    @SerializedName("thing")
    private Device device;
    private Shadow shadow;

    public Device getDevice() {
        return device;
    }

    public Shadow getShadow() {
        return shadow;
    }
}
