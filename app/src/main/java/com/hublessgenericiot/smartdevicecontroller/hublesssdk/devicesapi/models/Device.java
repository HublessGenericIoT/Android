package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

/**
 * Class for the device returned from the API.
 * It does not contain its current status or the shadow,
 * but it contains its name.
 */
public class Device {
    private String thingName;
    private String defaultClientId;
    private IotAttributesMap attributes;

    public String getThingName() {
        return thingName;
    }

    public String getDefaultClientId() {
        return defaultClientId;
    }

    public IotAttributesMap getAttributes() {
        return attributes;
    }
}
