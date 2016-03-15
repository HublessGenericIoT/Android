package com.hublessgenericiot.smartdevicecontroller.hublesssdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * Possible types for the device. Currently only 'light' is supported.
 */
public enum DeviceType {
    @SerializedName("light")
    LIGHT
}
