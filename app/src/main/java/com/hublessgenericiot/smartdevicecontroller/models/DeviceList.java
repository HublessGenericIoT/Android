package com.hublessgenericiot.smartdevicecontroller.models;

import java.util.ArrayList;
import java.util.List;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class DeviceList {

    @SerializedName("things")
    @Expose
    private List<Thing> things = new ArrayList<Thing>();

    /**
     *
     * @return
     * The things
     */
    public List<Thing> getThings() {
        return things;
    }

    /**
     *
     * @param things
     * The things
     */
    public void setThings(List<Thing> things) {
        this.things = things;
    }

}
