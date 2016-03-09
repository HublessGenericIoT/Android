package com.hublessgenericiot.smartdevicecontroller.hublesssdk.models;

/**
 * Device Shadow model.
 */
public class Shadow {
    private ShadowState state;
    private ShadowMetadata metadata;
    private int version;
    private long timestamp;
    private String clientToken;

    public ShadowState getState() {
        return state;
    }

    public ShadowMetadata getMetadata() {
        return metadata;
    }

    public int getVersion() {
        return version;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getClientToken() {
        return clientToken;
    }
}
