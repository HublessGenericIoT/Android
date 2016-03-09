package com.hublessgenericiot.smartdevicecontroller.hublesssdk.apiresponses;

/**
 * Root response for the API.
 *
 * All API endpoints will have a status with either an error or success string and a payload.
 */
public abstract class HublessApiResponse<T> {
    private String status;
    private T payload;

    public String getStatus() {
        return status;
    }

    public T getPayload() {
        return payload;
    }
}
