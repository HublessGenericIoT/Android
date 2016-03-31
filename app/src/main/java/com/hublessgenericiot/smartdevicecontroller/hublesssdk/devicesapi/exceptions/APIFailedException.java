package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.exceptions;

import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.HublessApiResponse;

/**
 * An exception passed when the API returned a non-"Success" status.
 */
public class APIFailedException extends Throwable {
    private HublessApiResponse response;

    public APIFailedException(String s) {
        super(s);
    }
    public APIFailedException(String s, HublessApiResponse response) {
        super(s);
        this.response = response;
    }

    public HublessApiResponse getResponse() {
        return response;
    }
}
