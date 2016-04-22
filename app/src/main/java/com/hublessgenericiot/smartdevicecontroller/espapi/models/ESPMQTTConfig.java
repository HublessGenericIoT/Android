package com.hublessgenericiot.smartdevicecontroller.espapi.models;

/**
 * Created by bwencke on 4/6/16.
 */
public class ESPMQTTConfig {

    private String user;
    private String pw; // password
    private String endpoint;

    public ESPMQTTConfig(String user, String pw, String endpoint) {
        this.user = user;
        this.pw = pw;
        this.endpoint = endpoint;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
