package com.hublessgenericiot.smartdevicecontroller.hublesssdk;

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

import com.hublessgenericiot.smartdevicecontroller.R;
import com.hublessgenericiot.smartdevicecontroller.activities.RoomsActivity;

import java.io.UnsupportedEncodingException;

public class HublessMQTTService {

    MqttAndroidClient mqttClient;

    public void connect(final RoomsActivity activity)
    {
        String clientId = MqttClient.generateClientId();
        mqttClient = new MqttAndroidClient(activity.getApplicationContext(),
                        activity.getApplicationContext().getString(R.string.newserver),
                        clientId);

        mqttClient.setCallback(new SubscribeCallback(activity));

        //MqttConnectOptions options = new MqttConnectOptions();
        //options.setUserName(activity.getApplicationContext().getString(R.string.proxyuser));
        //options.setPassword(activity.getApplicationContext().getString(R.string.proxypass).toCharArray());

        try {
            IMqttToken token = mqttClient.connect(); //options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(activity.getApplicationContext(), "CONNECTED", Toast.LENGTH_LONG).show();
                    Log.d("Connect", "onSuccess");
                    subscribe("$aws/things/+/shadow/update/accepted", activity);
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

    public void subscribe(String topic, final Activity activity)
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

    public void publish(String topic, String payload, final Activity activity)
    {
        Log.i("MQTT", "topic: " + topic + " payload: " + payload);
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            mqttClient.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return mqttClient.isConnected();
    }

}