package com.hublessgenericiot.smartdevicecontroller;

import com.hublessgenericiot.smartdevicecontroller.models.DeviceList;
import com.hublessgenericiot.smartdevicecontroller.models.NameTest;
import com.hublessgenericiot.smartdevicecontroller.models.ResponseObject;
import com.hublessgenericiot.smartdevicecontroller.models.Thing;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Joe Koncel on 2/17/2016.
 */
public interface LambdaIoTAPI {
    @GET("/test/devices")
    Call<DeviceList> getDevices();

    @GET("/test/devices")
    Call<DeviceList> getDevicesByRoom(@Query("room") String room);

    @POST("/test/devices")
    Call<Thing> createDevice(@Body Thing newThing);

    @GET("/test/devices/{id}")
    Call<Thing> getDevice(@Path("id") int deviceID);

    @PUT("/test/devices/{id}")
    Call<Thing> updateDevice(@Path("id") int deviceID, @Body Thing thing);

    @DELETE("/test/devices/{id}")
    Call<Thing> deleteDevice(@Path("id") int deviceID);

    //@POST("/test/signup")
    //Call<UsernameAndPasswordObject?> signup(@Body UnP unp);

    //@POST("/test/login")
    //Call<UsernameAndPasswordObject?> login(@Body UnP unp);

    @POST("/test/mydemoresource")
    Call<NameTest> testHWPost(@Body NameTest name);
}