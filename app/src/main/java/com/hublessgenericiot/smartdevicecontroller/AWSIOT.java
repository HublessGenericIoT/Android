package com.hublessgenericiot.smartdevicecontroller;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.hublessgenericiot.smartdevicecontroller.models.DeviceList;
import com.hublessgenericiot.smartdevicecontroller.models.NameTest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class AWSIOT {

    MqttAndroidClient mqttClient;
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

    public void connectTest(final Activity activity)
    {
        String clientId = MqttClient.generateClientId();
        mqttClient = new MqttAndroidClient(activity.getApplicationContext(), "tcp://ubuntu-david.cloudapp.net:1883",
                        clientId);

        mqttClient.setCallback(new SubscribeCallback(activity));

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName("client");
        options.setPassword("BW8iO21i3Z89".toCharArray());

        try {
            IMqttToken token = mqttClient.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(activity.getApplicationContext(), "CONNECTED", Toast.LENGTH_LONG).show();
                    Log.d("Connect", "onSuccess");
                    subscribeTest("proxy/#", activity);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(activity.getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
                    Log.d("Connect", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribeTest(String topic, final Activity activity)
    {
        int qos = 1;
        try {
            IMqttToken subToken = mqttClient.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                    Toast.makeText(activity.getApplicationContext(), "SUBSCRIBED", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards
                    Toast.makeText(activity.getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publishTest(String topic, String payload, final Activity activity)
    {
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            mqttClient.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
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