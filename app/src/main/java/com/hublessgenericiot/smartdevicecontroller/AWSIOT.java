package com.hublessgenericiot.smartdevicecontroller;

//import org.eclipse.paho.android.service.MqttAndroidClient;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hublessgenericiot.smartdevicecontroller.models.NameTest;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class AWSIOT {

    //MqttAndroidClient mqttClient;
    LambdaIoTAPI lambdaIoTAPI;
    Retrofit retrofit;

    public AWSIOT () //Activity currentActivity) //, String clientID)
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://457g994lrb.execute-api.us-west-2.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        lambdaIoTAPI = retrofit.create(LambdaIoTAPI.class);
    }

    public void createNewDevice(String thingName, final Activity activity)
    {
        //ResponseObject ro = new ResponseObject("Batten ", "Down ", "the Hatches");
        NameTest name = new NameTest(thingName);
        //final String[] a = new String[1];

        //asynchronous call
        //new Thread(new Runnable() {
        //    String t = thingName;
        //    public void run() {
                Call<NameTest> call = lambdaIoTAPI.testHWPost(name);
                //call.enqueue();

                call.enqueue(new Callback<NameTest>() {
                    @Override
                    public void onResponse(Response<NameTest> response, Retrofit rf) {
                        //response.body().toString();
                        Toast.makeText(activity.getApplicationContext(), response.body().getName(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

                /*for(int i = 0; i < 30; i++) {
                    try {
                        call.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //System.out.println("x\n");
                    }
                }*/
        //    }
        //}).start();

        //synchronous call
        /*try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //return a[0];
    }

    public void provisionNewDevice(String thingName)
    {

    }


}

/*provider = new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                return null;
            }

            @Override
            public void refresh() {

            }
        };

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                MY-ACTIVITY.getApplicationContext(), // Application Context
                "MY-IDENTITY-POOL-ID", // Identity Pool ID
                Regions.SELECT_YOUR_REGION // Region enum
        );

        lambdaClient = new AWSLambdaClient(provider);
        /*mqttClient = new MqttAndroidClient(currentActivity.getApplicationContext(),
                awsClient.describeEndpoint(new DescribeEndpointRequest()).getEndpointAddress(),
                clientID);*/