package com.hublessgenericiot.smartdevicecontroller.hublesssdk.models;

import org.json.JSONObject;

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
    Map<String, String> delta;
}
