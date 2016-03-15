package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.exceptions;

/**
 * An exception passed when the API returned a non-"Success" status.
 */
public class APIFailedException extends Throwable {
    public APIFailedException(String s) {
        super(s);
    }
}
