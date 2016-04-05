package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Object to send the data to create a new device on AWS.
 */
public class DeviceCreator extends Device {

    public DeviceCreator(String id, String name, String room, DeviceType type) {
        this(name, room, type);
        this.id = id;
    }

    public DeviceCreator(String name, String room, DeviceType type) {
        this.setName(name);
        this.setRoom(room);
        this.setUser("1"); //defaulted to "user" user. ie, not a test user.
        this.type = type;
    }

    public DeviceCreator(Device from) {
        this.setName(from.getName());
        this.setRoom(from.getRoom());
        this.setUser(from.getUser()); //defaulted to "user" user. ie, not a test user.
        this.type = from.getType();
        this.id = from.getId();
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public DeviceType getType() {
        return type;
    }

    public void setName(String name) {
        confirmCompliance(name);
        this.name = name;
    }

    private void confirmCompliance(String in) {
        if(in == null || in.isEmpty()) {
            return;
        }
        Pattern p = Pattern.compile("[ a-zA-Z0-9_.,@/:#-]+");
        Matcher m = p.matcher(in);
        boolean b = m.matches();
        if(!b) throw new IllegalArgumentException("The given string ("+in+") is not valid. Must match \"[ a-zA-Z0-9_.,@/:#-]+\"");
    }

    public void setRoom(String room) {
        confirmCompliance(room);
        this.room = room;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
