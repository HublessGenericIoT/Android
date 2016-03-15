package com.hublessgenericiot.smartdevicecontroller.hublesssdk.exceptions;

/**
 * An exception passed when the API returned a non-"Success" status.
 */
public class APIFailedException extends Throwable {
    public APIFailedException(String s) {
        super(s);
    }
}
