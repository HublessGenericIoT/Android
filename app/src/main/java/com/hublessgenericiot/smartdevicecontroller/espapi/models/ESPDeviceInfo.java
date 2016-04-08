package com.hublessgenericiot.smartdevicecontroller.espapi.models;

/**
 * Created by bwencke on 4/6/16.
 */
public class ESPDeviceInfo {

    private String name;
    private ESPWifiConfig wifi; // TODO: Rename to wifiConfig once the ESP supports it
    private ESPMQTTConfig mqtt; // TODO: Rename to mqttConfig once the ESP supports it

    public ESPDeviceInfo(String name, ESPWifiConfig wifiConfig, ESPMQTTConfig mqttConfig) {
        this.name = name;
        this.wifi = wifiConfig;
        this.mqtt = mqttConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ESPWifiConfig getWifiConfig() {
        return wifi;
    }

    public void setWifiConfig(ESPWifiConfig wifiConfig) {
        this.wifi = wifiConfig;
    }

    public ESPMQTTConfig getMqttConfig() {
        return mqtt;
    }

    public void setMqttConfig(ESPMQTTConfig mqttConfig) {
        this.mqtt = mqttConfig;
    }
}
