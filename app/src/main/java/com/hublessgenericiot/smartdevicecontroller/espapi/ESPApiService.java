package com.hublessgenericiot.smartdevicecontroller.espapi;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hublessgenericiot.smartdevicecontroller.R;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.HublessCallback;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.IHublessSdkService;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A singleton and factory for the Retrofit instance for the API.
 */
public class ESPApiService {
    private static IESPApiService INSTANCE;

    /**
     * Returns the instance of the ESPAPI that was created by Retrofit.
     *
     * The context is required ONLY the first time this is called.
     * To be safe, always pass a valid context, but it will only fail
     * if there is no instance.
     *
     * @param c The context so that the client can grab the API Url from resources.
     * @return An instance of the ESPAPIService.
     */
    public static IESPApiService getInstance(@Nullable final Context c) {
        if(INSTANCE == null) {
            if(c == null) throw new NullPointerException("The context must not be null the first time this is called");

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);

            OkHttpClient client = httpClient.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(c.getString(R.string.esp_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            INSTANCE = retrofit.create(IESPApiService.class);
        }
        return INSTANCE;
    }
}
