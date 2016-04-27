package com.hublessgenericiot.smartdevicecontroller.espapi.models;

/**
 * Created by bwencke on 4/6/16.
 */
public class ESPConfig {

    private String id;
    private ESPWifiConfig wifiConfig;
    private ESPMQTTConfig mqttConfig;

    public ESPConfig(String id, ESPWifiConfig wifiConfig, ESPMQTTConfig mqttConfig) {
        this.id = id;
        this.wifiConfig = wifiConfig;
        this.mqttConfig = mqttConfig;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ESPWifiConfig getWifiConfig() {
        return wifiConfig;
    }

    public void setWifiConfig(ESPWifiConfig wifiConfig) {
        this.wifiConfig = wifiConfig;
    }

    public ESPMQTTConfig getMqttConfig() {
        return mqttConfig;
    }

    public void setMqttConfig(ESPMQTTConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }
}
