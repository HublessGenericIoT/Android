package com.hublessgenericiot;

import android.app.Activity;

import com.amazonaws.services.iot.AWSIotClient;
import com.amazonaws.services.iot.model.AttachThingPrincipalRequest;
import com.amazonaws.services.iot.model.AttachThingPrincipalResult;
import com.amazonaws.services.iot.model.CreateKeysAndCertificateRequest;
import com.amazonaws.services.iot.model.CreateKeysAndCertificateResult;
import com.amazonaws.services.iot.model.CreatePolicyRequest;
import com.amazonaws.services.iot.model.CreatePolicyResult;
import com.amazonaws.services.iot.model.CreateThingRequest;
import com.amazonaws.services.iot.model.CreateThingResult;
import com.amazonaws.services.iot.model.DescribeEndpointRequest;

import org.eclipse.paho.android.service.MqttAndroidClient;

public class AWSIOT {

    AWSIotClient awsClient;
    //MqttAndroidClient mqttClient;

    public AWSIOT (Activity currentActivity, String clientID)
    {
        awsClient = new AWSIotClient(); //except presumeably we need to give this more info
        //Server URI is supposed to specify port, too, so potential issue here
        //Also constructors with acknowledge type, persistence
        /*mqttClient = new MqttAndroidClient(currentActivity.getApplicationContext(),
                awsClient.describeEndpoint(new DescribeEndpointRequest()).getEndpointAddress(),
                clientID);*/

    }

    public void createNewDevice(String thingName)
    {
        CreateThingRequest thingRequest;
        CreateThingResult thingResult;

        thingRequest = new CreateThingRequest();
        thingRequest.setThingName(thingName);
        thingResult = awsClient.createThing(thingRequest);
    }

    public void provisionNewDevice(String thingName)
    {
        //CreateKeysAndCertificateRequest keysAndCertRequest;
        CreateKeysAndCertificateResult keysAndCertResult;
        CreatePolicyRequest policyRequest;
        CreatePolicyResult policyResult;
        AttachThingPrincipalRequest principalRequest;
        AttachThingPrincipalResult principalResult;

        //Assuming we don't have to modify this request?
        keysAndCertResult = awsClient.createKeysAndCertificate(new CreateKeysAndCertificateRequest());

        policyRequest = new CreatePolicyRequest();
        policyRequest.setPolicyDocument("{\n" +
                "    \"Version\": \"2012-10-17\", \n" +
                "    \"Statement\": [{\n" +
                "        \"Effect\": \"Allow\",\n" +
                "        \"Action\":[\"iot:*\"],\n" +
                "        \"Resource\": [\"*\"]\n" +
                "    }]\n");
        policyResult = awsClient.createPolicy(policyRequest);

        principalRequest = new AttachThingPrincipalRequest();
        principalRequest.setPrincipal(keysAndCertResult.getCertificateArn());
        principalRequest.setThingName(thingName);
        principalResult = awsClient.attachThingPrincipal(principalRequest);
    }


}
