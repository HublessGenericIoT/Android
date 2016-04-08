package com.hublessgenericiot.smartdevicecontroller.espapi;

import com.hublessgenericiot.smartdevicecontroller.espapi.models.ESPConfig;
import com.hublessgenericiot.smartdevicecontroller.espapi.responses.ESPAPIResponse;
import com.hublessgenericiot.smartdevicecontroller.espapi.responses.ESPConnectResponse;
import com.hublessgenericiot.smartdevicecontroller.espapi.responses.ESPSetupResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceCreatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceListResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceUpdatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceCreator;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Retrofit interface for the API. Check the hublessapi repo in Github for a more
 * detailed explanation of the API functions.
 */
public interface IESPAPIService {

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
