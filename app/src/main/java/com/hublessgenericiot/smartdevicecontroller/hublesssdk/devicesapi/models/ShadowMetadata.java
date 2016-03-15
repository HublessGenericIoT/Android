package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

import java.util.Map;

/**
 * Metadata on the shadow object, check AWS for usage.
 */
public class ShadowMetadata {
    private Map<String, MetadataMemberObject> desired;
    private Map<String, MetadataMemberObject> reported;

    public Map<String, MetadataMemberObject> getDesired() {
        return desired;
    }

    public Map<String, MetadataMemberObject> getReported() {
        return reported;
    }
}
