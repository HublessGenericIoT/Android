package com.hublessgenericiot.smartdevicecontroller.dummy;

import com.hublessgenericiot.smartdevicecontroller.hublesssdk.HublessMQTTService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Global device list saved here
 */
public class SavedDeviceList {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Device> ITEMS = new ArrayList<Device>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Device> ITEM_MAP = new HashMap<String, Device>();

    /* This is so RoomsActivity reloads only once */
    public static boolean newRoom = true;

    public static HublessMQTTService mqttService;
}
