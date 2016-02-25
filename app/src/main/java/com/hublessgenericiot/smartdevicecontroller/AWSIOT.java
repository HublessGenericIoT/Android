package com.hublessgenericiot.smartdevicecontroller;

//import org.eclipse.paho.android.service.MqttAndroidClient;

import android.app.Activity;
import android.widget.Toast;

import com.hublessgenericiot.smartdevicecontroller.models.DeviceList;
import com.hublessgenericiot.smartdevicecontroller.models.NameTest;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class AWSIOT {

    //MqttAndroidClient mqttClient;
    LambdaIoTAPI lambdaIoTAPI;
    Retrofit retrofit;

    public AWSIOT ()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://457g994lrb.execute-api.us-west-2.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        lambdaIoTAPI = retrofit.create(LambdaIoTAPI.class);
    }

    public void getNameReturnTest(String thingName, final Activity activity)
    {
        NameTest name = new NameTest(thingName);

        Call<NameTest> call = lambdaIoTAPI.testHWPost(name);
        call.enqueue(new Callback<NameTest>() {
            @Override
            public void onResponse(Response<NameTest> response, Retrofit rf) {
                Toast.makeText(activity.getApplicationContext(), response.body().getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getDeviceList(final Activity activity)
    {
        Call<DeviceList> call = lambdaIoTAPI.getDevices();
        call.enqueue(new Callback<DeviceList>() {
            @Override
            public void onResponse(Response<DeviceList> response, Retrofit rf) {
                if (response.body() != null) {
                    Toast.makeText(activity.getApplicationContext(), response.body().getThings().get(1).getThingName(), Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(activity.getApplicationContext(), "null", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}