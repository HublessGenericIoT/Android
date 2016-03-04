package com.hublessgenericiot.smartdevicecontroller;

import com.hublessgenericiot.smartdevicecontroller.models.NameTest;
import com.hublessgenericiot.smartdevicecontroller.models.ResponseObject;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Joe Koncel on 2/17/2016.
 */
public interface LambdaIoTAPI {
    @GET("/test/mydemoresource")
    Call<ResponseObject> getDeviceInfo(@Body ResponseObject responseObject);

    @POST("/test/mydemoresource")
    Call<NameTest> testHWPost(@Body NameTest name);
}