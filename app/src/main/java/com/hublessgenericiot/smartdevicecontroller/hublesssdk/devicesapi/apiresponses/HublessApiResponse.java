package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses;

/**
 * Root response for the API.
 *
 * All API endpoints will have a status with either an error or success string and a payload.
 */
public abstract class HublessApiResponse<T> {
    private String status;
    private T payload;
    private String errorMessage;

    public String getStatus() {
        return status;
    }

    public T getPayload() {
        return payload;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
