package com.hublessgenericiot.smartdevicecontroller.hublesssdk.exceptions;

/**
 * AWS enforces that attribute lists can only be 3 items long. This is thrown if another attribute is created.
 */
public class TooManyAttributesException extends RuntimeException {
    public TooManyAttributesException(String s) {
        super(s);
    }
}
