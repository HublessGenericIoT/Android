package com.hublessgenericiot.smartdevicecontroller.models;

/**
 * Created by Joe Koncel on 2/17/2016.
 */

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class ResponseObject {

    @SerializedName("key3")
    @Expose
    private String key3;
    @SerializedName("key2")
    @Expose
    private String key2;
    @SerializedName("key1")
    @Expose
    private String key1;

    public ResponseObject(String k1, String k2, String k3)
    {
        key1 = k1;
        key2 = k2;
        key3 = k3;
    }

    /**
     *
     * @return
     * The key3
     */
    public String getKey3() {
        return key3;
    }

    /**
     *
     * @param key3
     * The key3
     */
    public void setKey3(String key3) {
        this.key3 = key3;
    }

    /**
     *
     * @return
     * The key2
     */
    public String getKey2() {
        return key2;
    }

    /**
     *
     * @param key2
     * The key2
     */
    public void setKey2(String key2) {
        this.key2 = key2;
    }

    /**
     *
     * @return
     * The key1
     */
    public String getKey1() {
        return key1;
    }

    /**
     *
     * @param key1
     * The key1
     */
    public void setKey1(String key1) {
        this.key1 = key1;
    }

}