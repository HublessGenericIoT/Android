package com.hublessgenericiot.smartdevicecontroller.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Joe Koncel on 2/24/2016.
 */
public class Device {

    @SerializedName("thingName")
    @Expose
    private String thingName;

    public Device(String name)
    {
        thingName = name;
    }

    public String getThingName()
    {
        return thingName;
    }
}
