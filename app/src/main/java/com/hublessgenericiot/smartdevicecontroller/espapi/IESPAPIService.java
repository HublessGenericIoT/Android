package com.hublessgenericiot.smartdevicecontroller.espapi;

import com.hublessgenericiot.smartdevicecontroller.espapi.models.ESPConfig;
import com.hublessgenericiot.smartdevicecontroller.espapi.responses.ESPConnectResponse;
import com.hublessgenericiot.smartdevicecontroller.espapi.responses.ESPSetupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Retrofit interface for the API. Check the hublessapi repo in Github for a more
 * detailed explanation of the API functions.
 */
public interface IESPApiService {

    /**
     * Connects to the device, if possible
     * @return
     */
    @GET("/")
    Call<ESPConnectResponse> connect();

    /**
     * An endpoint to create the device on AWS. Give a populated DeviceCreator
     * object to give the device a name and assign a room.
     * @param espConfig A populated espConfig object.
     * @return A DeviceCreatedResponse with a "CreatedDeviceData" payload.
     */
    @POST("setup")
    Call<ESPSetupResponse> setup(@Body ESPConfig espConfig);
}
