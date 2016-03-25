package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

/**
 * Response payload after creating a device.
 */
public class CreatedDeviceData {
    private String thingName;
    private MQTTConnectionData mqttData;

    public String getThingName() {
        return thingName;
    }

    public MQTTConnectionData getMqttData() {
        return mqttData;
    }
}
