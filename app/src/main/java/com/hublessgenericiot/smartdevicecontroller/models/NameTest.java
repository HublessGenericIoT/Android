package com.hublessgenericiot.smartdevicecontroller.models;

/**
 * Created by Joe Koncel on 2/22/2016.
 */

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class NameTest {

    @SerializedName("name")
    @Expose
    private String name;

    public NameTest(String n)
    {
        name = n;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

}