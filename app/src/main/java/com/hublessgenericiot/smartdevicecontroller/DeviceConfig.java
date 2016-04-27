package com.hublessgenericiot.smartdevicecontroller;

import android.util.Log;

import com.hublessgenericiot.smartdevicecontroller.activities.RoomsActivity;
import com.hublessgenericiot.smartdevicecontroller.espapi.ESPApiService;
import com.hublessgenericiot.smartdevicecontroller.espapi.IESPApiService;
import com.hublessgenericiot.smartdevicecontroller.espapi.models.ESPConfig;
import com.hublessgenericiot.smartdevicecontroller.espapi.models.ESPMQTTConfig;
import com.hublessgenericiot.smartdevicecontroller.espapi.models.ESPWifiConfig;
import com.hublessgenericiot.smartdevicecontroller.espapi.responses.ESPSetupResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.CreatedDeviceData;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.NewDevice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bwencke on 4/5/16.
 */
public class DeviceConfig {

    public NewDevice device;
    public CreatedDeviceData createdDeviceData;

    public DeviceConfig(NewDevice device, CreatedDeviceData createdDeviceData) {
        this.device = device;
        this.createdDeviceData = createdDeviceData;
    }

    public boolean isValid() {
        return device != null && createdDeviceData != null;
    }

    public void sendConfiguration(RoomsActivity activity) {

        ESPConfig espConfig = new ESPConfig(
                device.getName(),
                new ESPWifiConfig(
                    activity.getString(R.string.network_ssid),
                    activity.getString(R.string.network_pw)
                ),
                new ESPMQTTConfig(
                    createdDeviceData.getMqttData().getUsername(),
                    createdDeviceData.getMqttData().getPassword(),
                    createdDeviceData.getMqttData().getUrl()
                )
            );

        IESPApiService instance = ESPApiService.getInstance(activity);
        instance.setup(espConfig).enqueue(new Callback<ESPSetupResponse>() {
            @Override
            public void onResponse(Call<ESPSetupResponse> call, Response<ESPSetupResponse> response) {
                if(response.isSuccess()) {
                    Log.i("ESP Config", response.body().toString());
                } else {
                    Log.e("ESP Config", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ESPSetupResponse> call, Throwable t) {
                Log.e("ESP Config", t.toString());
            }
        });
    }

}
