package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

/**
 * Response payload after creating a device.
 */
public class CreatedDeviceData {
    private String id;
    private MQTTConnectionData mqttData;

    public String getId() {
        return id;
    }

    public MQTTConnectionData getMqttData() {
        return mqttData;
    }
}
