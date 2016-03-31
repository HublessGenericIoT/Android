package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hublessgenericiot.smartdevicecontroller.R;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceCreatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceListResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceUpdatedResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceCreator;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.DeviceType;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A singleton and factory for the Retrofit instance for the API.
 */
public class HublessSdkService {
    private static IHublessSdkService INSTANCE;

    /**
     * Returns the instance of the HublessSdk that was created by Retrofit.
     *
     * The context is required ONLY the first time this is called.
     * To be safe, always pass a valid context, but it will only fail
     * if there is no instance.
     *
     * @param c The context so that the client can grab the API Url from resources.
     * @return An instance of the HublessSdkService.
     */
    public static IHublessSdkService getInstance(@Nullable final Context c) {
        if(INSTANCE == null) {
            if(c == null) throw new NullPointerException("The context must not be null the first time this is called");

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new AuthenticationInterceptor(c.getApplicationContext().getString(R.string.apikey)));

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);

            OkHttpClient client = httpClient.build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(c.getString(R.string.apiurl))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            INSTANCE = retrofit.create(IHublessSdkService.class);
        }
        return INSTANCE;
    }

    /**
     * Test function. Do not call in production.
     * @param instance an initialized instance of the Service.
     */
    public static void testApi(final IHublessSdkService instance) {

        instance.getAllDevices().enqueue(new HublessCallback<DeviceListResponse>() {
            @Override
            public void doOnResponse(Call<DeviceListResponse> call, retrofit2.Response<DeviceListResponse> response) {
                Log.d("API", "This is an example of how to use the HublessCallback.");
            }
        });


        instance.createDevice(new DeviceCreator("MyDevice", "LivingRoom", DeviceType.LIGHT)).enqueue(new HublessCallback<DeviceCreatedResponse>() {
            @Override
            public void doOnResponse(Call<DeviceCreatedResponse> call, retrofit2.Response<DeviceCreatedResponse> response) {
                Log.d("APITEST", "URL: "+ call.request().url());
                Log.d("APITEST", response.body().getStatus());
                Log.d("APITEST", "Created ID: " + response.body().getPayload().getId());
            }
        });

        instance.getAllDevices().enqueue(new HublessCallback<DeviceListResponse>() {
            @Override
            public void doOnResponse(Call<DeviceListResponse> call, retrofit2.Response<DeviceListResponse> response) {
                Log.d("APITEST", "URL: " + call.request().url());
                //Log.d("APITEST", response.body().getStatus());
                //Log.d("APITEST", "Size: " + response.body().getPayload().size());
                //Log.d("APITEST", response.body().getPayload().get(0).getDevice().getThingName());
            }
        });

        instance.getDevice("2c51f514-0aba-444b-81d9-eec49b8a6370").enqueue(new HublessCallback<DeviceResponse>() {
            @Override
            public void doOnResponse(Call<DeviceResponse> call, retrofit2.Response<DeviceResponse> response) {
                Log.d("APITEST", "URL: " + call.request().url());
                Log.d("APITEST", response.body().getStatus());
                Log.d("APITEST", "Device Name: " + response.body().getPayload().getName());

                DeviceCreator dc = new DeviceCreator(response.body().getPayload());
                dc.setRoom("BensBedroom");

                instance.updateDevice("2c51f514-0aba-444b-81d9-eec49b8a6370", dc).enqueue(new HublessCallback<DeviceUpdatedResponse>() {
                    @Override
                    public void doOnResponse(Call<DeviceUpdatedResponse> call, retrofit2.Response<DeviceUpdatedResponse> response) {
                        Log.d("APITEST", "URL: "+ call.request().url());
                        Log.d("APITEST", "Status: " + response.message());
                        Log.d("APITEST", response.body().getStatus());
                    }
                });
            }
        });
    }

    /**
     * Interceptor for the API that will add the authentication header for the
     * API over all. This DOES NOT add a user token.
     */
    private static class AuthenticationInterceptor implements Interceptor {
        private String apiKey;

        public AuthenticationInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("x-api-key", apiKey)
                    .header("Content-Type", "application/json")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        }
    }
}
