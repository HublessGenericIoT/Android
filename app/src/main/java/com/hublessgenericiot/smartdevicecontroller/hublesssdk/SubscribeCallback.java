package com.hublessgenericiot.smartdevicecontroller.hublesssdk;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.hublessgenericiot.smartdevicecontroller.RoomsActivity;
import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

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
        Log.d("Message Arrived", message.toString());
        Toast.makeText(activity.getApplicationContext(), "Message: " + message.toString(), Toast.LENGTH_LONG).show();
        for(DummyContent.DummyItem d : DummyContent.ITEMS)
        {
            if(!(message.toString().equals(d.id)))
            {
                d.state = !(d.state);
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