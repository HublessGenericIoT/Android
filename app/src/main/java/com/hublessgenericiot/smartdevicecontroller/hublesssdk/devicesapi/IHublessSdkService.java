package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi;

import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceCreatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceDeletedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceListResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceUpdatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceCreator;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Retrofit interface for the API. Check the hublessapi repo in Github for a more
 * detailed explanation of the API functions.
 */
public interface IHublessSdkService {
    /**
     * Used to get all devices in the system with their shadows.
     *
     * Future plans:
     *  - It will have a query parameter to search by room.
     *  - It will use the user's auth token to only list that user's devices.
     *
     * @return A list of devices in the form of a HublessApiResponse.
     */
    @GET("devices")
    Call<DeviceListResponse> getAllDevices();
    @GET("devices")
    Call<DeviceListResponse> getAllDevices(@Query("nextToken") String nextToken);

    /**
     * Used to get a specific device and its shadow by its name.
     *
     * @param id The id of the device.
     * @return The ShadowedDevice in the form of a HublessApiResponse.
     */
    @GET("devices/{id}")
    Call<DeviceResponse> getDevice(@Path("id") String id);

    /**
     * A way to update a device's data.
     *
     * @param id The id of the device in the API.
     * @param device The new device object with attributes to be written.
     * @return The status of the update.
     */
    @PUT("devices/{id}")
    Call<DeviceUpdatedResponse> updateDevice(@Path("id") String id, @Body DeviceCreator device);


    /**
     * Delete a device on AWS.
     *
     * @param id the ID of the device to be deleted.
     * @return An acknowledgement of the device being deleted.
     */
    @DELETE("devices/{id}")
    Call<DeviceDeletedResponse> deleteDevice(@Path("id") String id);

    /**
     * An endpoint to create the device on AWS. Give a populated DeviceCreator
     * object to give the device a name and assign a room.
     * @param deviceCreator A populated deviceCreator object.
     * @return A DeviceCreatedResponse with a "CreatedDeviceData" payload.
     */
    @POST("devices")
    Call<DeviceCreatedResponse> createDevice(@Body DeviceCreator deviceCreator);
}
