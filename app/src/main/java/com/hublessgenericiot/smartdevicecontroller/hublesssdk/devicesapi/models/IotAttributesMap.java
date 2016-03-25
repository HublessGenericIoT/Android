package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.exceptions.TooManyAttributesException;

import java.util.HashMap;

/**
 * Custom map implementation to verify AWS constraints on attributes locally.
 */
public class IotAttributesMap extends HashMap<String, String> {
    public static int MAX_SIZE = 3;
    @Override
    public String put(String key, String value) {
        if(size() >= MAX_SIZE) throw new TooManyAttributesException("A device can only have three attributes.");
        if(value.contains(" ")) throw new IllegalArgumentException("An attribute value cannot contain spaces.");
        return super.put(key, value);
    }
}
