package com.hublessgenericiot.smartdevicecontroller.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe Koncel on 2/24/2016.
 */
public class DeviceList {
    //stupid

    @SerializedName("things")
    @Expose
    private List<Device> things;

    public DeviceList()
    {
        things = new ArrayList<Device>();
    }

    public Device getDeviceAtIndex(int index)
    {
        return things.get(index);
    }
}
