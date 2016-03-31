package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

import com.google.gson.annotations.SerializedName;

/**
 * A ShadowedDevice is a device and it's shadow together.
 * This way, all of the device's data is accessible.
 */
public class ShadowedDevice extends Device {
    private Shadow shadow;

    public Shadow getShadow() {
        return shadow;
    }
}
