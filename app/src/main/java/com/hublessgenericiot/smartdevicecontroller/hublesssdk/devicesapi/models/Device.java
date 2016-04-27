package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

/**
 * Class for the device returned from the API.
 * It does not contain its current status or the shadow,
 * but it contains its name.
 */
public class Device {
    protected String id;
    protected String name;
    protected String room;
    protected String user;
    protected DeviceType type;

    public String getName() {
        return name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public String getUser() {
        return user;
    }

    public DeviceType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
