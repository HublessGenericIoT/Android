package com.hublessgenericiot.smartdevicecontroller.models;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class Thing {

    @SerializedName("attributes")
    @Expose
    private Attributes attributes;
    @SerializedName("thingName")
    @Expose
    private String thingName;

    /**
     * @return The attributes
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * @param attributes The attributes
     */
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    /**
     * @return The thingName
     */
    public String getThingName() {
        return thingName;
    }

    /**
     * @param thingName The thingName
     */
    public void setThingName(String thingName) {
        this.thingName = thingName;
    }
}