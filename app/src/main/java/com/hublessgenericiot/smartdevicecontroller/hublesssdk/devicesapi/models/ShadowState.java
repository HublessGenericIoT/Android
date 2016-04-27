package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

import java.util.Map;

/**
 * The object for device.shadow.state
 *
 * The reported, desired, and delta values can have arbitrary values, but are currently limited to strings.
 *
 * TODO: Allow Array values in Maps.
 */
public class ShadowState {

    Map<String, String> reported;
    Map<String, String> desired;
    transient Map<String, String> delta;

    public Map<String, String> getReported() {
        return reported;
    }

    public void setReported(Map<String, String> reported) {
        this.reported = reported;
    }

    public Map<String, String> getDesired() {
        return desired;
    }

    public void setDesired(Map<String, String> desired) {
        this.desired = desired;
    }
}
