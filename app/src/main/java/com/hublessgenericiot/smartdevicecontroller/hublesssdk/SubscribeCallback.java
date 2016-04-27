package com.hublessgenericiot.smartdevicecontroller.hublesssdk;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hublessgenericiot.smartdevicecontroller.activities.RoomsActivity;
import com.hublessgenericiot.smartdevicecontroller.data.SavedDeviceList;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.Device;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.Shadow;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.ShadowedDevice;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Joe Koncel on 3/4/2016.
 */
public class SubscribeCallback implements MqttCallback
{

    RoomsActivity activity;

    public SubscribeCallback(RoomsActivity a)
    {
        activity = a;
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.d("Connection Lost", cause.toString());
        Toast.makeText(activity.getApplicationContext(), "Connection Lost", Toast.LENGTH_LONG).show();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message)
    {
        Log.d("Topic", topic);
        Log.d("Message Arrived", message.toString());
        Toast.makeText(activity.getApplicationContext(), "Message: " + message.toString(), Toast.LENGTH_LONG).show();
        String[] tokens = topic.split("/");
        for(Device d : SavedDeviceList.ITEMS)
        {
            Log.d("ID ISSUES", tokens[2] + " vs " + d.getId());
            if(tokens[2].equals(d.getId()))
            {
                if (d instanceof ShadowedDevice)
                {
                    Gson gson = new Gson();
                    Shadow shadow = gson.fromJson(message.toString(), Shadow.class);
                    Toast.makeText(activity.getApplicationContext(), shadow.getState().getDesired().toString(), Toast.LENGTH_LONG).show();
                    ((ShadowedDevice) d).getShadow().setState(shadow.getState());
                }
                break;
            }
        }

        activity.updateViewPager(false);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d("Delivery Complete", token.toString());
        Toast.makeText(activity.getApplicationContext(), "Delivery Complete", Toast.LENGTH_LONG).show();
    }

}