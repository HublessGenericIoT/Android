package com.hublessgenericiot.smartdevicecontroller.espapi.models;

/**
 * Created by bwencke on 4/6/16.
 */
public class ESPWifiConfig {

    private String ssid;
    private String pw; // password
    //private String Authmode; TODO: add authmode

    public ESPWifiConfig(String ssid, String pw) {
        this.ssid = ssid;
        this.pw = pw;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
