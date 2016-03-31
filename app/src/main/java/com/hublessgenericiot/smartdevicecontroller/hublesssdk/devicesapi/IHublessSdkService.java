package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi;

import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceCreatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceListResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceUpdatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.Device;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceCreator;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * Created by david on 3/4/16.
 */
public interface IHublessSdkService {
    /**
     * Used to get all devices in the system with their shadows.
     *
     * Future plans:
     *  - It will have a query parameter to search by room.
     *  - It will use the user's auth token to only list that user's devices.
     *  - It will allow devices to be paged.
     *
     * @return A list of devices in the form of a HublessApiResponse.
     */
    @GET("devices")
    Call<DeviceListResponse> getAllDevices();

    /**
     * Used to get a specific device and its shadow by its name.
     *
     * @param id The id of the device.
     * @return The ShadowedDevice in the form of a HublessApiResponse.
     */
    @GET("devices/{id}")
    Call<DeviceResponse> getDevice(@Path("id") String id);

    /**
     * A way to update a device's attributes. Note: DEVICE NAMES CANNOT CHANGE
     * @param id The id of the device in the API.
     * @param device The new device object with attributes to be written.
     * @return The status of the update.
     */
    @PUT("devices/{id}")
    Call<DeviceUpdatedResponse> updateDevice(@Path("id") String id, @Body DeviceCreator device);

    /**
     * An endpoint to create the device on AWS. Give a populated DeviceCreator
     * object to give the device a name and assign a room.
     * @param deviceCreator A populated deviceCreator object.
     * @return A DeviceCreatedResponse with a "CreatedDeviceData" payload.
     */
    @POST("devices")
    Call<DeviceCreatedResponse> createDevice(@Body DeviceCreator deviceCreator);
}
