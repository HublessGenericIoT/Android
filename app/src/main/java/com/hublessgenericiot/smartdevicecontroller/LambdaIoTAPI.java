package com.hublessgenericiot.smartdevicecontroller;

import com.hublessgenericiot.smartdevicecontroller.models.DeviceList;
import com.hublessgenericiot.smartdevicecontroller.models.NameTest;
import com.hublessgenericiot.smartdevicecontroller.models.ResponseObject;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Joe Koncel on 2/17/2016.
 */
public interface LambdaIoTAPI {
    @GET("/test/devices")
    Call<DeviceList> getDevices();

    @POST("/test/mydemoresource")
    Call<NameTest> testHWPost(@Body NameTest name);
}