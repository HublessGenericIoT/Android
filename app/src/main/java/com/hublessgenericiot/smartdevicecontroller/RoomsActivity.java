package com.hublessgenericiot.smartdevicecontroller;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.HublessMQTTService;
import com.hublessgenericiot.smartdevicecontroller.hublesssdk.HublessSdkService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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


        HublessSdkService.testApi(HublessSdkService.getInstance(this));

        mqttService = new HublessMQTTService();
        mqttService.connect(this);
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
    public void onDeviceClick(DummyContent.DummyItem item) {
        mqttService.publish("proxy/topic/device", item.id, this);
    }

    @Override
    public void onDeviceLongClick(DummyContent.DummyItem item) {
        Intent intent = new Intent(this, EditDeviceActivity.class);
        intent.putExtra(EditDeviceActivity.DEVICE_ID, item.id);
        startActivityForResult(intent, EditDeviceActivity.DEVICE_EDITED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EditDeviceActivity.DEVICE_EDITED) {
            if (resultCode == RESULT_OK) {
                boolean modified = data.getBooleanExtra("modified", false); // TODO: Don't use a string here
                if (modified) {
                    updateViewPager();
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

        registerReceiver(new WifiBroadcastReceiver(this, wifi), new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void updateViewPager() {
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
