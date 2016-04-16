package com.hublessgenericiot.smartdevicecontroller.espapi.responses;

/**
 * Root response for the API.
 *
 * All API endpoints will have a status with either an error or success string and a payload.
 */
public abstract class ESPApiResponse {
    private String status;
    private String errorMessage;

    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
