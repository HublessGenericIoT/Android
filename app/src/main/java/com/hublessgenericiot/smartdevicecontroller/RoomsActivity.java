package com.hublessgenericiot.smartdevicecontroller;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class RoomsActivity extends AppCompatActivity implements DeviceFragment.OnListFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    WifiManager wifi;

    String[] perms = {"android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE", "android.permission.ACCESS_COARSE_LOCATION"};
    private static final int MY_PERMISSIONS_REQUEST_WIFI = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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

        scanForDevices();

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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private LinkedList<String> rooms = new LinkedList<>();

        @Override
        public Fragment getItem(int position) {
            return DeviceFragment.newInstance(rooms.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            for(DummyContent.DummyItem d : DummyContent.ITEMS) {
                if(!rooms.contains(d.room)) {
                    rooms.add(d.room);
                }
            }

            // if more than 3 tabs, make them scrollable
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            if(rooms.size() > 3) {
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            } else {
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
            }
            return rooms.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return rooms.get(position);
        }
    }

    @Override
    public void onDeviceClick(DummyContent.DummyItem item) {

    }

    @Override
    public void onDeviceLongClick(DummyContent.DummyItem item) {
        Intent intent = new Intent(this, EditDeviceActivity.class);
        intent.putExtra(EditDeviceActivity.DEVICE_ID, item.id);
        startActivityForResult(intent, EditDeviceActivity.DEVICE_EDITED);
    }

    private void scanForDevices() {

//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_WIFI_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_WIFI_STATE)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//                Log.d("TAG", "SHOW EXPLANATION!");
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_CONTACTS},
//                        MY_PERMISSIONS_REQUEST_ACCESS_WIFI_STATE);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//
//                Log.d("TAG", "Don't show explanation");
//            }
//        } else {
//            Log.d("TAG", "granted");
//            scanWifi();
//        }

        requestPermissions(perms, MY_PERMISSIONS_REQUEST_WIFI);
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
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WIFI: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("TAG", "doing the thing");

                    scanWifi();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d("TAG", "DENIED!");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void scanWifi() {

        Log.d("dfg", "sc");

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (!wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(true);
        }

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent)  {
                for(ScanResult s : wifi.getScanResults()) {
                    Log.d("sc", s.SSID);
                    if(s.SSID.startsWith("ESP_")) {
                        Snackbar.make(findViewById(R.id.container), "New Device Found", Snackbar.LENGTH_LONG).show();
                    }
                }


                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        prepareScheduledScan();
                    }
                }, 3000);
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        wifi.startScan();
    }
}
