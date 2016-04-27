package com.hublessgenericiot.smartdevicecontroller.espapi.models;

/**
 * Created by bwencke on 4/6/16.
 */
public class ESPWifiConfig {

    private String ssid;
    private String password;
    private String automode;

    public ESPWifiConfig(String ssid, String password) {
        this.ssid = ssid;
        this.password = password;
        this.automode = "0";
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPw() {
        return password;
    }

    public void setPw(String password) {
        this.password = password;
    }

    public String getAutomode() {
        return automode;
    }

    public void setAutomode(String automode) {
        this.automode = automode;
    }
}
