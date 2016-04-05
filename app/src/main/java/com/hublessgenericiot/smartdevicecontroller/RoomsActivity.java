package com.hublessgenericiot.smartdevicecontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import com.hublessgenericiot.smartdevicecontroller.dummy.SavedDeviceList;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.HublessMQTTService;

import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.HublessCallback;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.HublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.IHublessSdkService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.apiresponses.DeviceListResponse;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.devicesapi.models.Device;

import retrofit2.Call;


public class RoomsActivity extends AppCompatActivity implements DeviceFragment.OnListFragmentInteractionListener {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private RoomsPagerAdapter mRoomsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    WifiManager wifi;
    HublessMQTTService mqttService;

    String[] perms = {"android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE", "android.permission.ACCESS_COARSE_LOCATION"};
    private static final int MY_PERMISSIONS_REQUEST_WIFI = 200;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private WifiBroadcastReceiver wifiReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mRoomsPagerAdapter = new RoomsPagerAdapter(getSupportFragmentManager(), (TabLayout) findViewById(R.id.tabs));

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mRoomsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifi.startScan();
                Snackbar.make(view, "Performing scan...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initWifiScan();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //Toast.makeText(getApplicationContext(), x, Toast.LENGTH_SHORT).show();

        //only for debugging, testing, and example purposes. No need to actually use this.
        //HublessSdkService.testApi(HublessSdkService.getInstance(this));

        if(!SavedDeviceList.newRoom) { //TODO this is so it only connects once
            mqttService = new HublessMQTTService();
            mqttService.connect(this);
        }
        else {
            IHublessSdkService instance = HublessSdkService.getInstance(this);
            instance.getAllDevices().enqueue(new HublessCallback<DeviceListResponse>() {
                @Override
                public void doOnResponse(Call<DeviceListResponse> call, retrofit2.Response<DeviceListResponse> response) {
                    Log.d("RoomsActivity", "URL: " + call.request().url());
                    Log.d("RoomsActivity", response.body().toString());

                    for (Device d : response.body().getPayload()) {
                        SavedDeviceList.ITEMS.add(d);
                        SavedDeviceList.ITEM_MAP.put(d.getId(), d);
                    }
                    SavedDeviceList.newRoom = false;
                    updateViewPager(true);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rooms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeviceClick(Device item) {
        mqttService.publish("proxy/esp_8266/inTopic", item.getName(), this);
    }

    @Override
    public void onDeviceLongClick(Device item) {
        Intent intent = new Intent(this, EditDeviceActivity.class);
        intent.putExtra(EditDeviceActivity.DEVICE_ID, item.getId());
        startActivityForResult(intent, EditDeviceActivity.DEVICE_EDITED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EditDeviceActivity.DEVICE_EDITED) {
            if (resultCode == RESULT_OK) {
                boolean modified = data.getBooleanExtra("modified", false); // TODO: Don't use a string here
                boolean newRoom = data.getBooleanExtra("newRoom", false); // TODO: Don't use a string here
                if (modified) {
                    updateViewPager(newRoom);
                }
            }
        }
    }

    private void initWifiScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, MY_PERMISSIONS_REQUEST_WIFI);
        }
    }

    public void prepareScheduledScan() {
        this.runOnUiThread(scheduledScan);
    }

    public Runnable scheduledScan = new Runnable() {
        @Override
        public void run() {
            scanWifi();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WIFI: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    scanWifi();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("TAG", "DENIED!");
                }
            }
        }
    }

    private void scanWifi() {

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (!wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(true);
        }

        wifiReceiver = new WifiBroadcastReceiver(this, wifi);
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void updateViewPager(boolean newRoom) {
        if(newRoom) {
            unregisterReceiver(wifiReceiver);
            finish();
            startActivity(getIntent());
        }
        // TODO: Handle a room being removed!
        // TODO: Maybe do the "newRoom" check more intelligently somehow
        for (int i = 0; i < mRoomsPagerAdapter.registeredFragments.size(); i++) {
            int key = mRoomsPagerAdapter.registeredFragments.keyAt(i);
            Fragment fragment = mRoomsPagerAdapter.registeredFragments.get(key);
            if (fragment instanceof DeviceFragment) {
                ((DeviceFragment) fragment).reRender();
            }
        }
        mRoomsPagerAdapter.notifyDataSetChanged();
    }

}
