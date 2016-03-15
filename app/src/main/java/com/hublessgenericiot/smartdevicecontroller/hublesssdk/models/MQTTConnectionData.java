package com.hublessgenericiot.smartdevicecontroller.hublesssdk.models;

/**
 * Authentication data for a MQTT Endpoint.
 *
 * Eventually this should be more generic for different connection data (like SSL).
 */
public class MQTTConnectionData {
    private String url;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
