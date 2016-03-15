package com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi;

import android.util.Log;

import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.HublessApiResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.exceptions.APIFailedException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implements default logging for the response and errors if they occur.
 *
 * Ideally this should be used instead of inline implementations of the
 * interface.
 *
 * To use this, extend it with a new callback for whichever response.
 * It will handle logging the success and failures. Also, it allows you
 * to not have to implement failure everywhere.
 *
 *
 * NOTE: Handling of non-Success messages may change in the future.
 */
public abstract class HublessCallback<T extends HublessApiResponse> implements Callback<T> {

    /**
     * Standard API Response handler. It ensures that all responses from the API will be logged
     * with their method and path. It also forwards all non-Success status messages to onFailure.
     *
     * To add your own handling, implement the abstract doOnResponse() method below.
     *
     * @param call The call from retrofit.
     * @param response The response from the API.
     */
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.d("API", "onResponse(" + call.request().method() + ": " + call.request().url().encodedPath());

        if(!response.body().getStatus().equals("Success")) {
            Log.e("API", "Returned a non-Success status! - Status: " + response.body().getStatus());
            onFailure(call, new APIFailedException("The api returned a non-success status: " + response.body().getStatus()));
        } else {
            doOnResponse(call, response);
        }
    }

    /**
     * The abstract implementation of the onResponse() method provided by retrofit. It functions the same as the
     * standard onResponse in retrofit with one caveat: It will not be called for a non-Success status
     * message from the API.
     *
     * @param call The call from retrofit.
     * @param response The response from the API.
     */
    abstract public void doOnResponse(Call<T> call, Response<T> response);


    /**
     * The default onFailure() method from Retrofit, it logs the method and path of the request
     * and any errors returned by retrofit.
     *
     * AND
     *
     * In addition to being called on retrofit errors, it also will be called for a non-Success
     * status from the API.
     *
     * To extend this handling, override it in a subclass and call super.onFailure().
     *
     * @param call The call from retrofit.
     * @param t The throwable from retrofit, or built from the API.
     */
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d("API", "onFailure(" + call.request().method() + ": " + call.request().url().encodedPath());

        Log.e("API", "ERROR: ", t);
    }
}
