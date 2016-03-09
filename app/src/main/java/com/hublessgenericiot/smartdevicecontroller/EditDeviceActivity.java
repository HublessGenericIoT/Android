package com.hublessgenericiot.smartdevicecontroller;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.hublessgenericiot.smartdevicecontroller.dummy.DummyContent;


public class EditDeviceActivity extends AppCompatActivity implements ItemDataHolder {

    public static final String DEVICE_ID = "device_id";
    public static final int DEVICE_EDITED = 0;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getStringExtra(DEVICE_ID);

        setContentView(R.layout.activity_edit_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_device, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();    //Call the back button's method
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getDeviceId() {
        return id;
    }
}
