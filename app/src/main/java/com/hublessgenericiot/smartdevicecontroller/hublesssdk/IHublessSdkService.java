package com.hublessgenericiot.smartdevicecontroller.hublesssdk;

import com.hublessgenericiot.smartdevicecontroller.hublesssdk.apiresponses.DeviceCreatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.apiresponses.DeviceListResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.apiresponses.DeviceResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.apiresponses.DeviceUpdatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.models.Device;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.models.DeviceCreator;

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
     * Future plans:
     *  - Right now, the name of the device is its human readable name. Eventually this will
     *      be changed to an ID with the owner of the device stored in the metadata.
     *
     * @param name The name of the device.
     * @return The ShadowedDevice in the form of a HublessApiResponse.
     */
    @GET("devices/{name}")
    Call<DeviceResponse> getDeviceWithName(@Path("name") String name);

    /**
     * A way to update a device's attributes. Note: DEVICE NAMES CANNOT CHANGE
     * @param name The name of the device in the API.
     * @param device The new device object with attributes to be written.
     * @return The status of the update.
     */
    @PUT("devices/{name}")
    Call<DeviceUpdatedResponse> putDeviceWithAttributes(@Path("name") String name, @Body Device device);

    /**
     * An endpoint to create the device on AWS. Give a populated DeviceCreator
     * object to give the device a name and assign a room.
     * @param deviceCreator A populated deviceCreator object.
     * @return A DeviceCreatedResponse with a "CreatedDeviceData" payload.
     */
    @POST("devices")
    Call<DeviceCreatedResponse> createDevice(@Body DeviceCreator deviceCreator);
}
